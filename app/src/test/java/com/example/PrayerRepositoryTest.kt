package com.example

import com.example.data.PrayerRepository
import com.example.data.TimeMonitor
import com.example.model.PrayerCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PrayerRepositoryTest {

  @Test
  fun prayersCountIs44WithUniqueIdsAndCompleteFields() {
    val prayers = PrayerRepository.prayers
    assertEquals(44, prayers.size)
    assertEquals(44, prayers.map { it.id }.toSet().size)

    prayers.forEach { prayer ->
      assertTrue("Missing title for ${prayer.id}", prayer.title.isNotBlank())
      assertTrue("Missing arabic for ${prayer.id}", prayer.arabic.isNotBlank())
      assertTrue("Missing latin for ${prayer.id}", prayer.latin.isNotBlank())
      assertTrue("Missing translation for ${prayer.id}", prayer.translation.isNotBlank())
      assertTrue("Missing source for ${prayer.id}", prayer.source.isNotBlank())
      assertTrue("Missing contextHint for ${prayer.id}", prayer.contextHint.isNotBlank())
    }
  }

  @Test
  fun contextualRecommendationsMatchAll14WindowsAndExactBoundaries() {
    val boundaryChecks =
      listOf(
        // Window 14 (wrap-around): 22:30 - 02:59
        Triple(22, 30, "Istirahat malam" to "sebelum_tidur"),
        Triple(23, 59, "Istirahat malam" to "sebelum_tidur"),
        Triple(0, 0, "Istirahat malam" to "sebelum_tidur"),
        Triple(2, 59, "Istirahat malam" to "sebelum_tidur"),
        // Window 1: 03:00 - 04:29
        Triple(3, 0, "Sepertiga malam" to "keteguhan_hati"),
        Triple(4, 29, "Sepertiga malam" to "keteguhan_hati"),
        // Window 2: 04:30 - 05:59
        Triple(4, 30, "Waktu Subuh" to "bangun_tidur"),
        Triple(5, 59, "Waktu Subuh" to "bangun_tidur"),
        // Window 3: 06:00 - 06:59
        Triple(6, 0, "Pagi hari" to "ilmu_bermanfaat"),
        Triple(6, 59, "Pagi hari" to "ilmu_bermanfaat"),
        // Window 4: 07:00 - 08:29
        Triple(7, 0, "Berangkat pagi" to "keluar_rumah"),
        Triple(8, 29, "Berangkat pagi" to "keluar_rumah"),
        // Window 5: 08:30 - 09:59
        Triple(8, 30, "Di perjalanan" to "naik_kendaraan"),
        Triple(9, 59, "Di perjalanan" to "naik_kendaraan"),
        // Window 6: 10:00 - 11:44
        Triple(10, 0, "Waktu belajar" to "sebelum_belajar"),
        Triple(11, 44, "Waktu belajar" to "sebelum_belajar"),
        // Window 7: 11:45 - 13:14
        Triple(11, 45, "Jam makan siang" to "sebelum_makan"),
        Triple(13, 14, "Jam makan siang" to "sebelum_makan"),
        // Window 8: 13:15 - 14:59
        Triple(13, 15, "Ibadah siang" to "pertolongan_ibadah"),
        Triple(14, 59, "Ibadah siang" to "pertolongan_ibadah"),
        // Window 9: 15:00 - 16:29
        Triple(15, 0, "Kuliah & diskusi" to "kelancaran_lisan"),
        Triple(16, 29, "Kuliah & diskusi" to "kelancaran_lisan"),
        // Window 10: 16:30 - 17:44
        Triple(16, 30, "Aktivitas sore" to "dimudahkan_urusan"),
        Triple(17, 44, "Aktivitas sore" to "dimudahkan_urusan"),
        // Window 11: 17:45 - 19:14
        Triple(17, 45, "Petang & dzikir" to "sayyidul_istighfar"),
        Triple(19, 14, "Petang & dzikir" to "sayyidul_istighfar"),
        // Window 12: 19:15 - 20:59
        Triple(19, 15, "Evaluasi belajar" to "setelah_belajar"),
        Triple(20, 59, "Evaluasi belajar" to "setelah_belajar"),
        // Window 13: 21:00 - 22:29
        Triple(21, 0, "Ketenangan malam" to "kebaikan_dunia_akhirat"),
        Triple(22, 29, "Ketenangan malam" to "kebaikan_dunia_akhirat"),
      )

    boundaryChecks.forEach { (hour, minute, expected) ->
      val rec = PrayerRepository.getContextualRecommendation(hour, minute)
      assertEquals("Failed label at $hour:$minute", expected.first, rec.contextLabel)
      assertEquals("Failed prayer at $hour:$minute", expected.second, rec.prayer.id)
      assertEquals(String.format("%02d:%02d", hour, minute), rec.timeFormatted)
    }
  }

  @Test
  fun millisUntilNextMinuteSynchronizesToMinuteBoundary() {
    assertEquals(60_050L, TimeMonitor.millisUntilNextMinute(0L))
    assertEquals(45_050L, TimeMonitor.millisUntilNextMinute(15_000L))
    assertEquals(150L, TimeMonitor.millisUntilNextMinute(59_900L))
    assertEquals(51L, TimeMonitor.millisUntilNextMinute(59_999L))
  }

  @Test
  fun resolveTimeZoneDefaultsNonIndonesianZonesToWib() {
    val (laResolved, laLabel) =
      TimeMonitor.resolveTimeZone(null, java.util.TimeZone.getTimeZone("America/Los_Angeles"))
    assertEquals("Asia/Jakarta", laResolved.id)
    assertEquals("WIB", laLabel)

    val (witaResolved, witaLabel) =
      TimeMonitor.resolveTimeZone(null, java.util.TimeZone.getTimeZone("Asia/Makassar"))
    assertEquals("Asia/Makassar", witaResolved.id)
    assertEquals("WITA", witaLabel)

    val (witResolved, witLabel) =
      TimeMonitor.resolveTimeZone(
        com.example.model.AppTimeZone.WIT,
        java.util.TimeZone.getTimeZone("America/Los_Angeles"),
      )
    assertEquals("Asia/Jayapura", witResolved.id)
    assertEquals("WIT", witLabel)
  }

  @Test
  fun filterPrayersSearchesAcrossKeywordsLatinAndCategory() {
    val all = PrayerRepository.prayers

    val byKeyword = PrayerRepository.filterPrayers(all, "skripsi", PrayerCategory.ALL)
    assertTrue(byKeyword.any { it.id == "sebelum_belajar" })

    val byLatin = PrayerRepository.filterPrayers(all, "ghufraanak", PrayerCategory.ALL)
    assertEquals(listOf("keluar_kamar_mandi"), byLatin.map { it.id })

    val byCategory = PrayerRepository.filterPrayers(all, "", PrayerCategory.KULIAH_ILMU)
    assertEquals(10, byCategory.size)
  }

  @Test
  fun memorizationTipsHas5StepsAndTahiyatHasQuickTips() {
    assertEquals(5, PrayerRepository.memorizationTips.size)
    val tahiyatAwal = PrayerRepository.prayers.first { it.id == "tahiyat_awal" }
    val tahiyatAkhir = PrayerRepository.prayers.first { it.id == "tahiyat_akhir" }
    assertTrue(tahiyatAwal.quickTips.isNotEmpty())
    assertTrue(tahiyatAkhir.quickTips.isNotEmpty())
  }
}
