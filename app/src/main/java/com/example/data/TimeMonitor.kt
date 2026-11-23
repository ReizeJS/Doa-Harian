package com.example.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.example.model.AppTimeZone
import com.example.model.ContextualRecommendation
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimeMonitor(
  private val context: Context,
  private val currentTimeMillis: () -> Long = System::currentTimeMillis,
  private val currentTimeZone: () -> TimeZone = TimeZone::getDefault,
) {

  private val manualRefreshTrigger = MutableStateFlow(0L)
  @Volatile private var activeDeviceTimeZoneOverride: TimeZone? = null

  fun readCurrentTime(preference: AppTimeZone?): Triple<Int, Int, String> {
    val deviceTz = activeDeviceTimeZoneOverride ?: currentTimeZone()
    val (resolvedTz, label) = resolveTimeZone(preference, deviceTz)
    val cal =
      Calendar.getInstance(resolvedTz).apply {
        timeInMillis = currentTimeMillis()
      }
    return Triple(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), label)
  }

  private val systemClockTickFlow: Flow<Long> = callbackFlow {
    trySend(currentTimeMillis())

    val receiver =
      object : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
          if (intent?.action == Intent.ACTION_TIMEZONE_CHANGED) {
            val tzId = intent.getStringExtra("time-zone")
            if (!tzId.isNullOrBlank()) {
              activeDeviceTimeZoneOverride = TimeZone.getTimeZone(tzId)
            }
          }
          trySend(currentTimeMillis())
        }
      }

    val filter =
      IntentFilter().apply {
        addAction(Intent.ACTION_TIME_TICK)
        addAction(Intent.ACTION_TIME_CHANGED)
        addAction(Intent.ACTION_TIMEZONE_CHANGED)
      }

    ContextCompat.registerReceiver(
      context,
      receiver,
      filter,
      ContextCompat.RECEIVER_NOT_EXPORTED,
    )

    val tickerJob = launch {
      while (isActive) {
        val delayMs = millisUntilNextMinute(currentTimeMillis())
        delay(delayMs)
        trySend(currentTimeMillis())
      }
    }

    awaitClose {
      tickerJob.cancel()
      runCatching { context.unregisterReceiver(receiver) }
    }
  }

  fun recommendationFlow(selectedTimeZoneFlow: Flow<AppTimeZone?>): Flow<ContextualRecommendation> =
    combine(systemClockTickFlow, manualRefreshTrigger, selectedTimeZoneFlow) { _, _, tzPref ->
        readCurrentTime(tzPref)
      }
      .distinctUntilChanged()
      .map { (hour, minute, label) ->
        PrayerRepository.getContextualRecommendation(
          hour = hour,
          minute = minute,
          timeZoneLabel = label,
        )
      }

  fun refreshNow() {
    activeDeviceTimeZoneOverride = null
    manualRefreshTrigger.value = System.nanoTime()
  }

  companion object {
    private val INDONESIA_ZONE_IDS =
      setOf(
        "Asia/Jakarta",
        "Asia/Pontianak",
        "Asia/Makassar",
        "Asia/Ujung_Pandang",
        "Asia/Jayapura",
      )

    fun resolveTimeZone(
      preference: AppTimeZone?,
      deviceTimeZone: TimeZone = TimeZone.getDefault(),
    ): Pair<TimeZone, String> {
      val effectivePref =
        preference
          ?: if (deviceTimeZone.id in INDONESIA_ZONE_IDS) AppTimeZone.DEVICE else AppTimeZone.WIB

      return when (effectivePref) {
        AppTimeZone.WIB -> TimeZone.getTimeZone("Asia/Jakarta") to "WIB"
        AppTimeZone.WITA -> TimeZone.getTimeZone("Asia/Makassar") to "WITA"
        AppTimeZone.WIT -> TimeZone.getTimeZone("Asia/Jayapura") to "WIT"
        AppTimeZone.DEVICE -> {
          val label =
            when (deviceTimeZone.id) {
              "Asia/Jakarta", "Asia/Pontianak" -> "WIB"
              "Asia/Makassar", "Asia/Ujung_Pandang" -> "WITA"
              "Asia/Jayapura" -> "WIT"
              else -> deviceTimeZone.getDisplayName(false, TimeZone.SHORT, Locale("id", "ID"))
            }
          deviceTimeZone to label
        }
      }
    }

    fun millisUntilNextMinute(nowMillis: Long): Long {
      val remainder = nowMillis.mod(60_000L)
      return (60_000L - remainder + 50L).coerceIn(50L, 60_050L)
    }
  }
}
