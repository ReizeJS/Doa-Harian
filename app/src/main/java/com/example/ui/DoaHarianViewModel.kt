package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.NetworkMonitor
import com.example.data.PrayerRepository
import com.example.data.PreferencesRepository
import com.example.data.StoredPreferences
import com.example.data.TimeMonitor
import com.example.model.AppTimeZone
import com.example.model.ContextualRecommendation
import com.example.model.MainTab
import com.example.model.Prayer
import com.example.model.PrayerCategory
import java.util.Calendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DoaHarianUiState(
  val activeTab: MainTab = MainTab.DOA,
  val selectedCategory: PrayerCategory = PrayerCategory.ALL,
  val searchQuery: String = "",
  val selectedPrayer: Prayer = PrayerRepository.prayers.first(),
  val isMobileReaderOpen: Boolean = false,
  val savedIds: Set<String> = emptySet(),
  val readTodayIds: Set<String> = emptySet(),
  val showLatin: Boolean = true,
  val isArabicLarge: Boolean = false,
  val darkModePreference: Boolean? = null,
  val selectedTimeZone: AppTimeZone? = null,
  val recommendation: ContextualRecommendation = initialRecommendation(),
  val isOffline: Boolean = false,
  val isTipsModalOpen: Boolean = false,
  val isTipsPopupDismissed: Boolean = false,
  val showSplash: Boolean = true,
) {
  val displayedPrayers: List<Prayer>
    get() {
      val baseList =
        if (activeTab == MainTab.TERSIMPAN) {
          PrayerRepository.prayers.filter { savedIds.contains(it.id) }
        } else {
          PrayerRepository.prayers
        }
      return PrayerRepository.filterPrayers(baseList, searchQuery, selectedCategory)
    }

  val savedCount: Int
    get() = savedIds.size

  val showRecommendationCard: Boolean
    get() = activeTab == MainTab.DOA && searchQuery.isBlank()
}

private fun initialRecommendation(): ContextualRecommendation {
  val (tz, label) = TimeMonitor.resolveTimeZone(null)
  val cal = Calendar.getInstance(tz)
  return PrayerRepository.getContextualRecommendation(
    hour = cal.get(Calendar.HOUR_OF_DAY),
    minute = cal.get(Calendar.MINUTE),
    timeZoneLabel = label,
  )
}

class DoaHarianViewModel(
  private val preferencesRepository: PreferencesRepository,
  networkMonitor: NetworkMonitor,
  private val timeMonitor: TimeMonitor,
) : ViewModel() {

  private data class InternalState(
    val activeTab: MainTab = MainTab.DOA,
    val selectedCategory: PrayerCategory = PrayerCategory.ALL,
    val searchQuery: String = "",
    val selectedPrayer: Prayer = PrayerRepository.prayers.first(),
    val isMobileReaderOpen: Boolean = false,
    val isTipsModalOpen: Boolean = false,
    val isTipsPopupDismissed: Boolean = false,
    val showSplash: Boolean = true,
  )

  private val internalState = MutableStateFlow(InternalState())

  private val selectedTimeZoneFlow =
    preferencesRepository.preferencesFlow.map { it.timeZone }.distinctUntilChanged()

  val uiState: StateFlow<DoaHarianUiState> =
    combine(
        internalState,
        preferencesRepository.preferencesFlow,
        networkMonitor.isOfflineFlow,
        timeMonitor.recommendationFlow(selectedTimeZoneFlow),
      ) { internal, prefs: StoredPreferences, isOffline: Boolean, recommendation ->
        DoaHarianUiState(
          activeTab = internal.activeTab,
          selectedCategory = internal.selectedCategory,
          searchQuery = internal.searchQuery,
          selectedPrayer = internal.selectedPrayer,
          isMobileReaderOpen = internal.isMobileReaderOpen,
          savedIds = prefs.savedIds,
          readTodayIds = prefs.readTodayIds,
          showLatin = prefs.showLatin,
          isArabicLarge = prefs.isArabicLarge,
          darkModePreference = prefs.isDarkMode,
          selectedTimeZone = prefs.timeZone,
          recommendation = recommendation,
          isOffline = isOffline,
          isTipsModalOpen = internal.isTipsModalOpen,
          isTipsPopupDismissed = internal.isTipsPopupDismissed,
          showSplash = internal.showSplash,
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DoaHarianUiState(),
      )

  fun refreshRecommendationNow() {
    timeMonitor.refreshNow()
  }

  fun selectTimeZone(timeZone: AppTimeZone) {
    viewModelScope.launch { preferencesRepository.setTimeZone(timeZone) }
  }

  fun dismissSplash() {
    internalState.update { it.copy(showSplash = false) }
  }

  fun selectTab(tab: MainTab) {
    internalState.update { state ->
      val nextSelected =
        if (tab == MainTab.TERSIMPAN) {
          val saved = PrayerRepository.prayers.filter { uiState.value.savedIds.contains(it.id) }
          saved.firstOrNull() ?: state.selectedPrayer
        } else {
          state.selectedPrayer
        }
      state.copy(
        activeTab = tab,
        selectedCategory = PrayerCategory.ALL,
        searchQuery = "",
        selectedPrayer = nextSelected,
      )
    }
  }

  fun resetToHome() {
    internalState.update {
      it.copy(
        activeTab = MainTab.DOA,
        selectedCategory = PrayerCategory.ALL,
        searchQuery = "",
        isMobileReaderOpen = false,
      )
    }
  }

  fun selectCategory(category: PrayerCategory) {
    internalState.update { state ->
      val filtered =
        PrayerRepository.filterPrayers(
          sourceList =
            if (state.activeTab == MainTab.TERSIMPAN) {
              PrayerRepository.prayers.filter { uiState.value.savedIds.contains(it.id) }
            } else {
              PrayerRepository.prayers
            },
          query = state.searchQuery,
          category = category,
        )
      val nextSelected =
        if (filtered.any { it.id == state.selectedPrayer.id }) state.selectedPrayer
        else filtered.firstOrNull() ?: state.selectedPrayer

      state.copy(selectedCategory = category, selectedPrayer = nextSelected)
    }
  }

  fun updateSearchQuery(query: String) {
    internalState.update { state -> state.copy(searchQuery = query) }
  }

  fun openPrayer(prayer: Prayer, openSheet: Boolean) {
    internalState.update {
      it.copy(selectedPrayer = prayer, isMobileReaderOpen = openSheet)
    }
  }

  fun openRecommendation(openSheet: Boolean) {
    val (hour, minute, label) = timeMonitor.readCurrentTime(uiState.value.selectedTimeZone)
    val latestRecommendation = PrayerRepository.getContextualRecommendation(hour, minute, label)
    val recPrayer = latestRecommendation.prayer
    timeMonitor.refreshNow()
    internalState.update { state ->
      val nextCategory =
        if (state.selectedCategory != PrayerCategory.ALL &&
          state.selectedCategory != recPrayer.category
        ) {
          PrayerCategory.ALL
        } else {
          state.selectedCategory
        }
      state.copy(
        activeTab = MainTab.DOA,
        selectedCategory = nextCategory,
        searchQuery = "",
        selectedPrayer = recPrayer,
        isMobileReaderOpen = openSheet,
      )
    }
  }

  fun closeMobileReader() {
    internalState.update { it.copy(isMobileReaderOpen = false) }
  }

  fun navigateReader(delta: Int) {
    val currentList = uiState.value.displayedPrayers
    if (currentList.isEmpty()) return
    val currentIndex = currentList.indexOfFirst { it.id == uiState.value.selectedPrayer.id }
    val targetIndex =
      if (currentIndex == -1) 0 else (currentIndex + delta).coerceIn(0, currentList.lastIndex)
    internalState.update { it.copy(selectedPrayer = currentList[targetIndex]) }
  }

  fun toggleSave(prayerId: String) {
    viewModelScope.launch { preferencesRepository.toggleSaved(prayerId) }
  }

  fun toggleReadToday(prayerId: String) {
    viewModelScope.launch { preferencesRepository.toggleReadToday(prayerId) }
  }

  fun toggleShowLatin() {
    val next = !uiState.value.showLatin
    viewModelScope.launch { preferencesRepository.setShowLatin(next) }
  }

  fun toggleArabicSize() {
    val next = !uiState.value.isArabicLarge
    viewModelScope.launch { preferencesRepository.setArabicLarge(next) }
  }

  fun toggleDarkMode(currentlyDark: Boolean) {
    viewModelScope.launch { preferencesRepository.setDarkMode(!currentlyDark) }
  }

  fun openTipsModal() {
    internalState.update { it.copy(isTipsModalOpen = true) }
  }

  fun closeTipsModal() {
    internalState.update { it.copy(isTipsModalOpen = false) }
  }

  fun dismissTipsPopup() {
    internalState.update { it.copy(isTipsPopupDismissed = true) }
  }

  class Factory(
    private val preferencesRepository: PreferencesRepository,
    private val networkMonitor: NetworkMonitor,
    private val timeMonitor: TimeMonitor,
  ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return DoaHarianViewModel(preferencesRepository, networkMonitor, timeMonitor) as T
    }
  }
}
