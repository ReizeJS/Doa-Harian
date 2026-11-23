package com.example.model

enum class PrayerCategory(val label: String) {
  ALL("Semua"),
  AKTIVITAS_HARIAN("Aktivitas Harian"),
  RUMAH_PERJALANAN("Rumah & Perjalanan"),
  KULIAH_ILMU("Kuliah & Ilmu"),
  IBADAH_KETENANGAN("Ibadah & Ketenangan"),
}

data class Prayer(
  val id: String,
  val title: String,
  val category: PrayerCategory,
  val source: String,
  val contextHint: String,
  val arabic: String,
  val latin: String,
  val translation: String,
  val notes: String,
  val quickTips: List<String> = emptyList(),
  val keywords: List<String> = emptyList(),
)

enum class AppTimeZone(val label: String, val description: String, val zoneId: String?) {
  WIB("WIB", "WIB (UTC+7)", "Asia/Jakarta"),
  WITA("WITA", "WITA (UTC+8)", "Asia/Makassar"),
  WIT("WIT", "WIT (UTC+9)", "Asia/Jayapura"),
  DEVICE("Perangkat", "Ikuti Perangkat", null),
}

data class ContextualRecommendation(
  val timeFormatted: String,
  val contextLabel: String,
  val timeZoneLabel: String,
  val prayer: Prayer,
)

data class MemorizationTip(
  val number: Int,
  val title: String,
  val description: String,
)

enum class MainTab {
  DOA,
  TERSIMPAN,
}
