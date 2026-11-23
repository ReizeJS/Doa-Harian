package com.example.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.model.AppTimeZone
import com.example.model.MainTab
import com.example.model.Prayer
import com.example.model.PrayerCategory
import com.example.ui.components.DesktopPrayerReaderPane
import com.example.ui.components.HeaderBar
import com.example.ui.components.MemorizationTipsCard
import com.example.ui.components.MemorizationTipsModal
import com.example.ui.components.MemorizationTipsPopup
import com.example.ui.components.MobileBottomNav
import com.example.ui.components.MobilePrayerBottomSheet
import com.example.ui.components.PrayerListItem
import com.example.ui.components.RecommendationCard
import com.example.ui.components.SplashScreen
import com.example.ui.theme.DoaHarianTheme
import com.example.ui.theme.DoaTheme
import com.example.ui.theme.PlusJakartaSansFontFamily
import kotlinx.coroutines.launch

@Composable
fun DoaHarianApp(viewModel: DoaHarianViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val systemDark = isSystemInDarkTheme()
  val isDarkMode = uiState.darkModePreference ?: systemDark

  val lifecycleOwner = LocalLifecycleOwner.current
  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      if (event == Lifecycle.Event.ON_RESUME) {
        viewModel.refreshRecommendationNow()
      }
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
  }

  DoaHarianTheme(darkTheme = isDarkMode) {
    MainScreen(
      uiState = uiState,
      isDarkMode = isDarkMode,
      onToggleDarkMode = { viewModel.toggleDarkMode(isDarkMode) },
      onLogoClick = { viewModel.resetToHome() },
      onSelectTab = { viewModel.selectTab(it) },
      onSelectCategory = { viewModel.selectCategory(it) },
      onSearchQueryChange = { viewModel.updateSearchQuery(it) },
      onOpenPrayer = { prayer, openSheet -> viewModel.openPrayer(prayer, openSheet) },
      onOpenRecommendation = { openSheet -> viewModel.openRecommendation(openSheet) },
      onSelectTimeZone = { viewModel.selectTimeZone(it) },
      onCloseMobileReader = { viewModel.closeMobileReader() },
      onNavigateReader = { delta -> viewModel.navigateReader(delta) },
      onToggleSave = { viewModel.toggleSave(it) },
      onToggleReadToday = { viewModel.toggleReadToday(it) },
      onToggleShowLatin = { viewModel.toggleShowLatin() },
      onToggleArabicSize = { viewModel.toggleArabicSize() },
      onOpenTipsModal = { viewModel.openTipsModal() },
      onCloseTipsModal = { viewModel.closeTipsModal() },
      onDismissTipsPopup = { viewModel.dismissTipsPopup() },
      onDismissSplash = { viewModel.dismissSplash() },
    )
  }
}

@Composable
private fun MainScreen(
  uiState: DoaHarianUiState,
  isDarkMode: Boolean,
  onToggleDarkMode: () -> Unit,
  onLogoClick: () -> Unit,
  onSelectTab: (MainTab) -> Unit,
  onSelectCategory: (PrayerCategory) -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onOpenPrayer: (Prayer, Boolean) -> Unit,
  onOpenRecommendation: (Boolean) -> Unit,
  onSelectTimeZone: (AppTimeZone) -> Unit,
  onCloseMobileReader: () -> Unit,
  onNavigateReader: (Int) -> Unit,
  onToggleSave: (String) -> Unit,
  onToggleReadToday: (String) -> Unit,
  onToggleShowLatin: () -> Unit,
  onToggleArabicSize: () -> Unit,
  onOpenTipsModal: () -> Unit,
  onCloseTipsModal: () -> Unit,
  onDismissTipsPopup: () -> Unit,
  onDismissSplash: () -> Unit,
) {
  val palette = DoaTheme.colors
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  val copiedText = stringResource(R.string.copied_feedback)
  val listState = rememberLazyListState()

  val hasScrolledDown by remember {
    derivedStateOf {
      listState.firstVisibleItemIndex >= 2 || listState.firstVisibleItemScrollOffset > 220
    }
  }
  val isBottomTipsCardVisible by remember {
    derivedStateOf {
      listState.layoutInfo.visibleItemsInfo.any { it.key == "bottom_tips_card" }
    }
  }

  val displayedPrayers = uiState.displayedPrayers
  val currentReaderIndex =
    displayedPrayers.indexOfFirst { it.id == uiState.selectedPrayer.id }
  val canNavigatePrev = currentReaderIndex > 0
  val canNavigateNext =
    currentReaderIndex in 0 until displayedPrayers.lastIndex

  BoxWithConstraints(
    modifier = Modifier.fillMaxSize().background(palette.pageBackground)
  ) {
    val isWideLayout = maxWidth >= 840.dp

    val showTipsPopup =
      uiState.activeTab == MainTab.DOA &&
        hasScrolledDown &&
        !isBottomTipsCardVisible &&
        !uiState.isTipsPopupDismissed &&
        !uiState.isMobileReaderOpen &&
        !uiState.isTipsModalOpen

    Column(modifier = Modifier.fillMaxSize()) {
      HeaderBar(
        isWideLayout = isWideLayout,
        activeTab = uiState.activeTab,
        savedCount = uiState.savedCount,
        isDarkMode = isDarkMode,
        isOffline = uiState.isOffline,
        onLogoClick = onLogoClick,
        onSelectTab = onSelectTab,
        onToggleDarkMode = onToggleDarkMode,
      )

      // Main Responsive Content Area
      Box(
        modifier = Modifier.weight(1f).fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
      ) {
        if (isWideLayout) {
          Row(
            modifier =
              Modifier.widthIn(max = 1152.dp)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
          ) {
            // Left Column: Master Prayer List
            PrayerListColumn(
              uiState = uiState,
              displayedPrayers = displayedPrayers,
              isWideLayout = true,
              listState = listState,
              onSelectCategory = onSelectCategory,
              onSearchQueryChange = onSearchQueryChange,
              onOpenPrayer = { prayer -> onOpenPrayer(prayer, false) },
              onOpenRecommendation = { onOpenRecommendation(false) },
              onSelectTimeZone = onSelectTimeZone,
              onOpenTipsModal = onOpenTipsModal,
              modifier = Modifier.weight(5f).fillMaxHeight(),
            )

            // Right Column: Sticky Prayer Reader Pane
            Box(modifier = Modifier.weight(7f).fillMaxHeight()) {
              DesktopPrayerReaderPane(
                prayer = uiState.selectedPrayer,
                isSaved = uiState.savedIds.contains(uiState.selectedPrayer.id),
                isReadToday = uiState.readTodayIds.contains(uiState.selectedPrayer.id),
                showLatin = uiState.showLatin,
                isArabicLarge = uiState.isArabicLarge,
                canNavigatePrev = canNavigatePrev,
                canNavigateNext = canNavigateNext,
                onToggleArabicSize = onToggleArabicSize,
                onToggleShowLatin = onToggleShowLatin,
                onToggleSave = { onToggleSave(uiState.selectedPrayer.id) },
                onToggleReadToday = { onToggleReadToday(uiState.selectedPrayer.id) },
                onCopyFeedback = {
                  scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(copiedText)
                  }
                },
                onPrev = { onNavigateReader(-1) },
                onNext = { onNavigateReader(1) },
              )
            }
          }
        } else {
          // Compact Mobile Layout
          PrayerListColumn(
            uiState = uiState,
            displayedPrayers = displayedPrayers,
            isWideLayout = false,
            listState = listState,
            onSelectCategory = onSelectCategory,
            onSearchQueryChange = onSearchQueryChange,
            onOpenPrayer = { prayer -> onOpenPrayer(prayer, true) },
            onOpenRecommendation = { onOpenRecommendation(true) },
            onSelectTimeZone = onSelectTimeZone,
            onOpenTipsModal = onOpenTipsModal,
            modifier = Modifier.fillMaxSize().imePadding(),
          )
        }

        // Scroll-triggered Floating Memorization Tips Popup
        MemorizationTipsPopup(
          visible = showTipsPopup,
          onOpenModal = onOpenTipsModal,
          onDismiss = onDismissTipsPopup,
          modifier =
            Modifier.align(Alignment.BottomCenter)
              .padding(horizontal = 16.dp, vertical = 14.dp),
        )
      }

      if (!isWideLayout) {
        MobileBottomNav(
          activeTab = uiState.activeTab,
          savedCount = uiState.savedCount,
          onSelectTab = onSelectTab,
        )
      }
    }

    // Mobile Bottom Sheet Prayer Reader
    if (!isWideLayout) {
      MobilePrayerBottomSheet(
        visible = uiState.isMobileReaderOpen,
        prayer = uiState.selectedPrayer,
        isSaved = uiState.savedIds.contains(uiState.selectedPrayer.id),
        isReadToday = uiState.readTodayIds.contains(uiState.selectedPrayer.id),
        showLatin = uiState.showLatin,
        isArabicLarge = uiState.isArabicLarge,
        canNavigatePrev = canNavigatePrev,
        canNavigateNext = canNavigateNext,
        onDismiss = onCloseMobileReader,
        onToggleArabicSize = onToggleArabicSize,
        onToggleShowLatin = onToggleShowLatin,
        onToggleSave = { onToggleSave(uiState.selectedPrayer.id) },
        onToggleReadToday = { onToggleReadToday(uiState.selectedPrayer.id) },
        onCopyFeedback = {
          scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(copiedText)
          }
        },
        onPrev = { onNavigateReader(-1) },
        onNext = { onNavigateReader(1) },
      )
    }

    // Memorization Tips Modal
    MemorizationTipsModal(
      visible = uiState.isTipsModalOpen,
      onDismiss = onCloseTipsModal,
    )

    // Snackbar Feedback (e.g., Clipboard Copy)
    SnackbarHost(
      hostState = snackbarHostState,
      modifier =
        Modifier.align(Alignment.BottomCenter)
          .padding(bottom = if (isWideLayout) 20.dp else 72.dp, start = 16.dp, end = 16.dp),
    ) { data ->
      Snackbar(
        containerColor = palette.headerBackground,
        contentColor = androidx.compose.ui.graphics.Color.White,
        shape = RoundedCornerShape(12.dp),
      ) {
        Text(text = data.visuals.message, style = MaterialTheme.typography.bodyMedium)
      }
    }

    // Opening Splash Screen
    SplashScreen(
      visible = uiState.showSplash,
      onFinished = onDismissSplash,
    )
  }
}

@Composable
private fun PrayerListColumn(
  uiState: DoaHarianUiState,
  displayedPrayers: List<Prayer>,
  isWideLayout: Boolean,
  listState: androidx.compose.foundation.lazy.LazyListState,
  onSelectCategory: (PrayerCategory) -> Unit,
  onSearchQueryChange: (String) -> Unit,
  onOpenPrayer: (Prayer) -> Unit,
  onOpenRecommendation: () -> Unit,
  onSelectTimeZone: (AppTimeZone) -> Unit,
  onOpenTipsModal: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val horizontalPadding = if (isWideLayout) 0.dp else 16.dp

  LazyColumn(
    state = listState,
    modifier = modifier,
    contentPadding =
      PaddingValues(
        start = horizontalPadding,
        end = horizontalPadding,
        top = if (isWideLayout) 0.dp else 16.dp,
        bottom = if (isWideLayout) 24.dp else 28.dp,
      ),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    // 1. Page Title & Subtitle
    item(key = "page_header") {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text =
            if (uiState.activeTab == MainTab.DOA) stringResource(R.string.title_daily_prayers)
            else stringResource(R.string.title_saved_prayers),
          color = palette.textHeading,
          style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text =
            if (uiState.activeTab == MainTab.DOA)
              stringResource(R.string.subtitle_daily_prayers)
            else stringResource(R.string.subtitle_saved_prayers),
          color = palette.textSecondary,
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }

    // 2. Real-time Contextual Recommendation Card (shown on Doa Harian when searchQuery is blank, across all category tabs)
    if (uiState.showRecommendationCard) {
      item(key = "recommendation_card") {
        RecommendationCard(
          recommendation = uiState.recommendation,
          selectedTimeZone = uiState.selectedTimeZone,
          onSelectTimeZone = onSelectTimeZone,
          onClick = onOpenRecommendation,
        )
      }
    }

    // 3. Search Bar
    item(key = "search_bar") {
      SearchBarField(
        query = uiState.searchQuery,
        onQueryChange = onSearchQueryChange,
      )
    }

    // 4. Category Filter Pills
    item(key = "category_tabs") {
      CategoryFilterRow(
        selectedCategory = uiState.selectedCategory,
        onSelectCategory = onSelectCategory,
      )
    }

    // 5. Prayer List or Empty State
    if (displayedPrayers.isEmpty()) {
      item(key = "empty_state") {
        EmptyStateCard(
          isSavedTab = uiState.activeTab == MainTab.TERSIMPAN,
          hasSearch = uiState.searchQuery.isNotBlank(),
        )
      }
    } else {
      items(
        items = displayedPrayers,
        key = { it.id },
      ) { prayer ->
        PrayerListItem(
          prayer = prayer,
          isSelected = isWideLayout && uiState.selectedPrayer.id == prayer.id,
          isSaved = uiState.savedIds.contains(prayer.id),
          isReadToday = uiState.readTodayIds.contains(prayer.id),
          onClick = { onOpenPrayer(prayer) },
        )
      }
    }

    // 6. Bottom Card: Tips Cara Mudah Menghafal Doa (only in Doa Harian tab, removed from Tersimpan)
    if (uiState.activeTab == MainTab.DOA) {
      item(key = "bottom_tips_card") {
        Spacer(modifier = Modifier.height(4.dp))
        MemorizationTipsCard(onClick = onOpenTipsModal)
      }
    }
  }
}

@Composable
private fun SearchBarField(
  query: String,
  onQueryChange: (String) -> Unit,
) {
  val palette = DoaTheme.colors
  val focusManager = LocalFocusManager.current

  Row(
    modifier =
      Modifier.fillMaxWidth()
        .height(48.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(palette.cardSurface)
        .border(1.dp, palette.borderPrimary, RoundedCornerShape(14.dp))
        .padding(start = 14.dp, end = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = Icons.Outlined.Search,
      contentDescription = null,
      tint = palette.textPlaceholder,
      modifier = Modifier.size(20.dp),
    )
    Spacer(modifier = Modifier.width(10.dp))

    Box(
      modifier = Modifier.weight(1f),
      contentAlignment = Alignment.CenterStart,
    ) {
      if (query.isEmpty()) {
        Text(
          text = stringResource(R.string.search_placeholder),
          color = palette.textPlaceholder,
          style = MaterialTheme.typography.bodyLarge,
        )
      }
      BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle =
          TextStyle(
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 15.sp,
            color = palette.textPrimary,
          ),
        cursorBrush = SolidColor(palette.accentGreen),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        modifier = Modifier.fillMaxWidth().testTag("search_input"),
      )
    }

    if (query.isNotEmpty()) {
      IconButton(
        onClick = { onQueryChange("") },
        modifier = Modifier.size(48.dp).testTag("clear_search_button"),
      ) {
        Icon(
          imageVector = Icons.Filled.Close,
          contentDescription = stringResource(R.string.clear_search),
          tint = palette.textSecondary,
          modifier = Modifier.size(18.dp),
        )
      }
    }
  }
}

@Composable
private fun CategoryFilterRow(
  selectedCategory: PrayerCategory,
  onSelectCategory: (PrayerCategory) -> Unit,
) {
  val palette = DoaTheme.colors

  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(vertical = 2.dp),
  ) {
    items(
      items = PrayerCategory.entries,
      key = { it.name },
    ) { category ->
      val selected = category == selectedCategory
      val bgColor by
        animateColorAsState(
          targetValue = if (selected) palette.tabActiveBg else palette.tabInactiveBg,
          animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
          label = "catBg",
        )
      val textColor by
        animateColorAsState(
          targetValue = if (selected) palette.tabActiveText else palette.tabInactiveText,
          animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
          label = "catText",
        )

      Box(
        modifier =
          Modifier.clip(RoundedCornerShape(99.dp))
            .background(bgColor)
            .clickable { onSelectCategory(category) }
            .padding(horizontal = 14.dp, vertical = 9.dp)
            .testTag("category_chip_${category.name}"),
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = category.label,
          color = textColor,
          style = MaterialTheme.typography.labelLarge,
          fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
        )
      }
    }
  }
}

@Composable
private fun EmptyStateCard(
  isSavedTab: Boolean,
  hasSearch: Boolean,
) {
  val palette = DoaTheme.colors
  Column(
    modifier =
      Modifier.fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(palette.cardSurface)
        .border(1.dp, palette.borderPrimary, RoundedCornerShape(16.dp))
        .padding(horizontal = 24.dp, vertical = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Icon(
      imageVector = if (isSavedTab && !hasSearch) Icons.Outlined.BookmarkBorder else Icons.Outlined.Search,
      contentDescription = null,
      tint = palette.accentGreen,
      modifier = Modifier.size(34.dp),
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
      text =
        if (isSavedTab && !hasSearch) stringResource(R.string.empty_saved_title)
        else stringResource(R.string.empty_search_title),
      color = palette.textHeading,
      style = MaterialTheme.typography.titleMedium,
      textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text =
        if (isSavedTab && !hasSearch) stringResource(R.string.empty_saved_subtitle)
        else stringResource(R.string.empty_search_subtitle),
      color = palette.textSecondary,
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Center,
    )
  }
}
