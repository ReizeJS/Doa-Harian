package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Prayer
import com.example.ui.theme.DoaTheme
import com.example.ui.theme.arabicTextStyle
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@Composable
fun DesktopPrayerReaderPane(
  prayer: Prayer,
  isSaved: Boolean,
  isReadToday: Boolean,
  showLatin: Boolean,
  isArabicLarge: Boolean,
  canNavigatePrev: Boolean,
  canNavigateNext: Boolean,
  onToggleArabicSize: () -> Unit,
  onToggleShowLatin: () -> Unit,
  onToggleSave: () -> Unit,
  onToggleReadToday: () -> Unit,
  onCopyFeedback: () -> Unit,
  onPrev: () -> Unit,
  onNext: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val scrollState = rememberScrollState()

  LaunchedEffect(prayer.id) { scrollState.scrollTo(0) }

  Column(
    modifier =
      modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(palette.cardSurface)
        .border(1.dp, palette.borderPrimary, RoundedCornerShape(18.dp))
        .testTag("desktop_prayer_reader")
  ) {
    PrayerReaderBody(
      prayer = prayer,
      showLatin = showLatin,
      isArabicLarge = isArabicLarge,
      scrollState = scrollState,
      onToggleArabicSize = onToggleArabicSize,
      onToggleShowLatin = onToggleShowLatin,
      onClose = null,
      modifier = Modifier.weight(1f, fill = false),
    )

    HorizontalDivider(thickness = 1.dp, color = palette.divider)

    ReaderActionBar(
      prayer = prayer,
      isSaved = isSaved,
      isReadToday = isReadToday,
      canNavigatePrev = canNavigatePrev,
      canNavigateNext = canNavigateNext,
      onToggleSave = onToggleSave,
      onToggleReadToday = onToggleReadToday,
      onCopyFeedback = onCopyFeedback,
      onPrev = onPrev,
      onNext = onNext,
    )
  }
}

@Composable
fun MobilePrayerBottomSheet(
  visible: Boolean,
  prayer: Prayer,
  isSaved: Boolean,
  isReadToday: Boolean,
  showLatin: Boolean,
  isArabicLarge: Boolean,
  canNavigatePrev: Boolean,
  canNavigateNext: Boolean,
  onDismiss: () -> Unit,
  onToggleArabicSize: () -> Unit,
  onToggleShowLatin: () -> Unit,
  onToggleSave: () -> Unit,
  onToggleReadToday: () -> Unit,
  onCopyFeedback: () -> Unit,
  onPrev: () -> Unit,
  onNext: () -> Unit,
) {
  if (!visible) return

  BackHandler(enabled = visible, onBack = onDismiss)

  val palette = DoaTheme.colors
  val scope = rememberCoroutineScope()
  val scrollState = rememberScrollState()
  val offsetY = remember { Animatable(600f) }
  var isDragging by remember { mutableFloatStateOf(0f) }
  val density = LocalDensity.current

  LaunchedEffect(visible) {
    offsetY.animateTo(0f, animationSpec = tween(260, easing = FastOutSlowInEasing))
  }

  LaunchedEffect(prayer.id) { scrollState.scrollTo(0) }

  fun animateDismiss() {
    scope.launch {
      offsetY.animateTo(900f, animationSpec = tween(190, easing = FastOutSlowInEasing))
      onDismiss()
    }
  }

  fun settleOrDismiss(currentOffset: Float, velocityY: Float) {
    val thresholdPx = with(density) { 120.dp.toPx() }
    if (currentOffset > thresholdPx || velocityY > 900f) {
      animateDismiss()
    } else {
      scope.launch { offsetY.animateTo(0f, animationSpec = tween(200)) }
    }
  }

  // Nested scroll connection so swiping down when content scroll is at top drags the sheet down
  val nestedScrollConnection = remember {
    object : NestedScrollConnection {
      override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        if (offsetY.value > 0f && available.y != 0f) {
          val next = (offsetY.value + available.y).coerceAtLeast(0f)
          scope.launch { offsetY.snapTo(next) }
          return Offset(0f, available.y)
        }
        return Offset.Zero
      }

      override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
      ): Offset {
        if (scrollState.value <= 1 && available.y > 0f) {
          val next = (offsetY.value + available.y).coerceAtLeast(0f)
          scope.launch { offsetY.snapTo(next) }
          return Offset(0f, available.y)
        }
        return Offset.Zero
      }

      override suspend fun onPreFling(available: Velocity): Velocity {
        if (offsetY.value > 0f) {
          settleOrDismiss(offsetY.value, available.y)
          return available
        }
        return Velocity.Zero
      }
    }
  }

  BoxWithConstraints(
    modifier =
      Modifier.fillMaxSize()
        .background(Color.Black.copy(alpha = 0.45f))
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          onClick = { animateDismiss() },
        )
  ) {
    val maxSheetHeight = maxHeight * 0.88f

    Column(
      modifier =
        Modifier.align(Alignment.BottomCenter)
          .fillMaxWidth()
          .heightIn(max = maxSheetHeight)
          .offset { IntOffset(0, offsetY.value.roundToInt()) }
          .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
          .background(palette.cardSurface)
          .border(
            width = 1.dp,
            color = palette.borderPrimary,
            shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
          )
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {},
          )
          .nestedScroll(nestedScrollConnection)
          .testTag("mobile_prayer_sheet")
    ) {
      // Drag handle area supporting swipe down to close
      Column(
        modifier =
          Modifier.fillMaxWidth()
            .pointerInput(Unit) {
              detectVerticalDragGestures(
                onDragEnd = { settleOrDismiss(offsetY.value, isDragging) },
                onVerticalDrag = { _, dragAmount ->
                  isDragging = dragAmount * 20f
                  scope.launch {
                    offsetY.snapTo((offsetY.value + dragAmount).coerceAtLeast(0f))
                  }
                },
              )
            }
            .padding(top = 10.dp, bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Box(
          modifier =
            Modifier.width(42.dp)
              .height(4.dp)
              .clip(RoundedCornerShape(99.dp))
              .background(palette.secondaryButtonBorder)
        )
      }

      PrayerReaderBody(
        prayer = prayer,
        showLatin = showLatin,
        isArabicLarge = isArabicLarge,
        scrollState = scrollState,
        onToggleArabicSize = onToggleArabicSize,
        onToggleShowLatin = onToggleShowLatin,
        onClose = { animateDismiss() },
        modifier = Modifier.weight(1f, fill = false),
      )

      HorizontalDivider(thickness = 1.dp, color = palette.divider)

      Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
        ReaderActionBar(
          prayer = prayer,
          isSaved = isSaved,
          isReadToday = isReadToday,
          canNavigatePrev = canNavigatePrev,
          canNavigateNext = canNavigateNext,
          onToggleSave = onToggleSave,
          onToggleReadToday = onToggleReadToday,
          onCopyFeedback = onCopyFeedback,
          onPrev = onPrev,
          onNext = onNext,
        )
      }
    }
  }
}

@Composable
private fun PrayerReaderBody(
  prayer: Prayer,
  showLatin: Boolean,
  isArabicLarge: Boolean,
  scrollState: ScrollState,
  onToggleArabicSize: () -> Unit,
  onToggleShowLatin: () -> Unit,
  onClose: (() -> Unit)?,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors

  Column(modifier = modifier.fillMaxWidth()) {
    // Header meta + reader controls
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "${prayer.category.label} · ${prayer.source}",
            color = palette.accentGreen,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }
        if (onClose != null) {
          IconButton(
            onClick = onClose,
            modifier = Modifier.size(48.dp).testTag("close_reader_button"),
          ) {
            Icon(
              imageVector = Icons.Filled.Close,
              contentDescription = stringResource(R.string.close_label),
              tint = palette.textSecondary,
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Control pills: Arabic size & Teks Latin toggle
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        ReaderControlChip(
          icon = Icons.Outlined.FormatSize,
          label =
            if (isArabicLarge) stringResource(R.string.font_large)
            else stringResource(R.string.font_normal),
          active = isArabicLarge,
          onClick = onToggleArabicSize,
          modifier = Modifier.testTag("toggle_arabic_size"),
        )
        ReaderControlChip(
          icon = if (showLatin) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
          label = stringResource(R.string.latin_text_toggle),
          active = showLatin,
          onClick = onToggleShowLatin,
          modifier = Modifier.testTag("toggle_latin_text"),
        )
      }
    }

    HorizontalDivider(thickness = 1.dp, color = palette.divider)

    // Scrollable reading area
    Column(
      modifier =
        Modifier.fillMaxWidth()
          .verticalScroll(scrollState)
          .padding(horizontal = 18.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      Column {
        Text(
          text = prayer.title,
          color = palette.textHeading,
          style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = prayer.contextHint,
          color = palette.textSecondary,
          style = MaterialTheme.typography.bodyMedium,
        )
      }

      // Arabic RTL Block
      CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
          modifier =
            Modifier.fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(palette.arabicBlockBg)
              .border(1.dp, palette.arabicBlockBorder, RoundedCornerShape(14.dp))
              .padding(horizontal = 18.dp, vertical = 20.dp)
        ) {
          Text(
            text = prayer.arabic,
            color = palette.textArabic,
            style = arabicTextStyle(isLarge = isArabicLarge),
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth().testTag("reader_arabic_text"),
          )
        }
      }

      // Latin Transliteration Block (collapsible for memorization practice)
      AnimatedVisibility(
        visible = showLatin,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = stringResource(R.string.section_latin),
            color = palette.textHint,
            style = MaterialTheme.typography.labelMedium,
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = prayer.latin,
            color = palette.textLatin,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.testTag("reader_latin_text"),
          )
        }
      }

      // Meaning Block
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = stringResource(R.string.section_meaning),
          color = palette.textHint,
          style = MaterialTheme.typography.labelMedium,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = prayer.translation,
          color = palette.textPrimary,
          style = MaterialTheme.typography.bodyLarge,
        )
      }

      // Quick Tips Block (for Tahiyat Awal, Tahiyat Akhir, etc.)
      if (prayer.quickTips.isNotEmpty()) {
        Column(
          modifier =
            Modifier.fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(palette.arabicBlockBg)
              .border(1.dp, palette.arabicBlockBorder, RoundedCornerShape(12.dp))
              .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Outlined.Bolt,
              contentDescription = null,
              tint = palette.accentGreen,
              modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = stringResource(R.string.section_quick_tips),
              color = palette.accentGreen,
              style = MaterialTheme.typography.labelLarge,
            )
          }
          prayer.quickTips.forEach { tip ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(
                text = "•",
                color = palette.accentGreen,
                fontWeight = FontWeight.Bold,
              )
              Text(
                text = tip,
                color = palette.textPrimary,
                style = MaterialTheme.typography.bodyMedium,
              )
            }
          }
        }
      }

      // Short Notes Block
      if (prayer.notes.isNotBlank()) {
        Column(
          modifier =
            Modifier.fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(palette.notesBlockBg)
              .padding(14.dp)
        ) {
          Text(
            text = stringResource(R.string.section_notes),
            color = palette.accentGreen,
            style = MaterialTheme.typography.labelMedium,
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = prayer.notes,
            color = palette.textSecondary,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
    }
  }
}

@Composable
private fun ReaderControlChip(
  icon: ImageVector,
  label: String,
  active: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier =
      modifier
        .heightIn(min = 36.dp)
        .clip(RoundedCornerShape(99.dp))
        .background(if (active) palette.itemSelectedBg else palette.tabInactiveBg)
        .border(
          width = 1.dp,
          color = if (active) palette.accentGreen else palette.secondaryButtonBorder,
          shape = RoundedCornerShape(99.dp),
        )
        .clickable(onClick = onClick)
        .padding(horizontal = 12.dp, vertical = 6.dp),
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = if (active) palette.accentGreen else palette.tabInactiveText,
      modifier = Modifier.size(16.dp),
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = label,
      color = if (active) palette.accentGreen else palette.tabInactiveText,
      style = MaterialTheme.typography.labelMedium,
    )
  }
}

@Composable
private fun ReaderActionBar(
  prayer: Prayer,
  isSaved: Boolean,
  isReadToday: Boolean,
  canNavigatePrev: Boolean,
  canNavigateNext: Boolean,
  onToggleSave: () -> Unit,
  onToggleReadToday: () -> Unit,
  onCopyFeedback: () -> Unit,
  onPrev: () -> Unit,
  onNext: () -> Unit,
) {
  val palette = DoaTheme.colors
  val clipboardManager = LocalClipboardManager.current

  val formattedCopy =
    buildString {
      appendLine(prayer.title)
      appendLine()
      appendLine(prayer.arabic)
      appendLine()
      appendLine(prayer.latin)
      appendLine()
      appendLine("Artinya: ${prayer.translation}")
      append("(${prayer.source})")
    }

  Row(
    modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ActionPillButton(
        icon = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
        label =
          if (isSaved) stringResource(R.string.action_saved)
          else stringResource(R.string.action_save),
        active = isSaved,
        onClick = onToggleSave,
        modifier = Modifier.testTag("action_save_button"),
      )
      ActionPillButton(
        icon = if (isReadToday) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
        label =
          if (isReadToday) stringResource(R.string.action_read_done)
          else stringResource(R.string.action_mark_read),
        active = isReadToday,
        onClick = onToggleReadToday,
        modifier = Modifier.testTag("action_read_button"),
      )
      ActionPillButton(
        icon = Icons.Outlined.ContentCopy,
        label = stringResource(R.string.action_copy),
        active = false,
        onClick = {
          clipboardManager.setText(AnnotatedString(formattedCopy))
          onCopyFeedback()
        },
        modifier = Modifier.testTag("action_copy_button"),
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
      NavArrowButton(
        icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        contentDescription = stringResource(R.string.prev_prayer),
        enabled = canNavigatePrev,
        onClick = onPrev,
        modifier = Modifier.testTag("reader_prev_button"),
      )
      NavArrowButton(
        icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = stringResource(R.string.next_prayer),
        enabled = canNavigateNext,
        onClick = onNext,
        modifier = Modifier.testTag("reader_next_button"),
      )
    }
  }
}

@Composable
private fun ActionPillButton(
  icon: ImageVector,
  label: String,
  active: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier =
      modifier
        .heightIn(min = 42.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(if (active) palette.tabActiveBg else palette.tabInactiveBg)
        .border(
          width = 1.dp,
          color = if (active) palette.tabActiveBg else palette.secondaryButtonBorder,
          shape = RoundedCornerShape(10.dp),
        )
        .clickable(onClick = onClick)
        .padding(horizontal = 10.dp, vertical = 8.dp),
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = if (active) palette.tabActiveText else palette.tabInactiveText,
      modifier = Modifier.size(16.dp),
    )
    Spacer(modifier = Modifier.width(5.dp))
    Text(
      text = label,
      color = if (active) palette.tabActiveText else palette.tabInactiveText,
      style = MaterialTheme.typography.labelMedium,
      fontWeight = FontWeight.SemiBold,
    )
  }
}

@Composable
private fun NavArrowButton(
  icon: ImageVector,
  contentDescription: String,
  enabled: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  Box(
    modifier =
      modifier
        .size(42.dp)
        .clip(CircleShape)
        .background(palette.tabInactiveBg.copy(alpha = if (enabled) 1f else 0.45f))
        .border(
          1.dp,
          palette.secondaryButtonBorder.copy(alpha = if (enabled) 1f else 0.4f),
          CircleShape,
        )
        .clickable(enabled = enabled, onClick = onClick),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      imageVector = icon,
      contentDescription = contentDescription,
      tint =
        if (enabled) palette.tabInactiveText
        else palette.tabInactiveText.copy(alpha = 0.35f),
      modifier = Modifier.size(20.dp),
    )
  }
}
