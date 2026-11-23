package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.AppTimeZone
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sakudoa_prefs")

data class StoredPreferences(
  val savedIds: Set<String> = emptySet(),
  val readTodayIds: Set<String> = emptySet(),
  val showLatin: Boolean = true,
  val isArabicLarge: Boolean = false,
  val isDarkMode: Boolean? = null,
  val timeZone: AppTimeZone? = null,
)

class PreferencesRepository(private val context: Context) {

  private object Keys {
    val SAVED_IDS = stringSetPreferencesKey("sakudoa_saved_ids")
    val READ_TODAY_DATE = stringPreferencesKey("sakudoa_read_today_date")
    val READ_TODAY_IDS = stringSetPreferencesKey("sakudoa_read_today_ids")
    val SHOW_LATIN = booleanPreferencesKey("sakudoa_show_latin")
    val ARABIC_LARGE = booleanPreferencesKey("sakudoa_arabic_size")
    val DARK_MODE = booleanPreferencesKey("sakudoa_dark_mode")
    val TIME_ZONE = stringPreferencesKey("sakudoa_time_zone")
  }

  val preferencesFlow: Flow<StoredPreferences> =
    context.dataStore.data.map { prefs ->
      val today = todayString()
      val storedDate = prefs[Keys.READ_TODAY_DATE]
      val validReadToday =
        if (storedDate == today) prefs[Keys.READ_TODAY_IDS] ?: emptySet() else emptySet()

      val tzName = prefs[Keys.TIME_ZONE]
      val parsedTz = tzName?.let { name -> AppTimeZone.entries.firstOrNull { it.name == name } }

      StoredPreferences(
        savedIds = prefs[Keys.SAVED_IDS] ?: emptySet(),
        readTodayIds = validReadToday,
        showLatin = prefs[Keys.SHOW_LATIN] ?: true,
        isArabicLarge = prefs[Keys.ARABIC_LARGE] ?: false,
        isDarkMode = prefs[Keys.DARK_MODE],
        timeZone = parsedTz,
      )
    }

  suspend fun toggleSaved(prayerId: String) {
    context.dataStore.edit { prefs ->
      val current = prefs[Keys.SAVED_IDS] ?: emptySet()
      prefs[Keys.SAVED_IDS] =
        if (current.contains(prayerId)) current - prayerId else current + prayerId
    }
  }

  suspend fun toggleReadToday(prayerId: String) {
    val today = todayString()
    context.dataStore.edit { prefs ->
      val storedDate = prefs[Keys.READ_TODAY_DATE]
      val current =
        if (storedDate == today) prefs[Keys.READ_TODAY_IDS] ?: emptySet() else emptySet()
      prefs[Keys.READ_TODAY_DATE] = today
      prefs[Keys.READ_TODAY_IDS] =
        if (current.contains(prayerId)) current - prayerId else current + prayerId
    }
  }

  suspend fun setShowLatin(show: Boolean) {
    context.dataStore.edit { prefs -> prefs[Keys.SHOW_LATIN] = show }
  }

  suspend fun setArabicLarge(large: Boolean) {
    context.dataStore.edit { prefs -> prefs[Keys.ARABIC_LARGE] = large }
  }

  suspend fun setDarkMode(dark: Boolean) {
    context.dataStore.edit { prefs -> prefs[Keys.DARK_MODE] = dark }
  }

  suspend fun setTimeZone(timeZone: AppTimeZone) {
    context.dataStore.edit { prefs -> prefs[Keys.TIME_ZONE] = timeZone.name }
  }

  private fun todayString(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return formatter.format(Date())
  }
}
