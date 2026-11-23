package com.example.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.PrayerRepository
import com.example.ui.theme.BrandLogoCrescent
import com.example.ui.theme.DoaTheme
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@Composable
fun MemorizationTipsCard(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val gradientBrush =
    Brush.linearGradient(
      colors = listOf(palette.gradientStart, palette.gradientMid, palette.gradientEnd)
    )

  Row(
    modifier =
      modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(gradientBrush)
        .border(1.dp, palette.popupBorder, RoundedCornerShape(16.dp))
        .clickable(onClick = onClick)
        .padding(16.dp)
        .testTag("memorization_tips_card"),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Row(
      modifier = Modifier.weight(1f),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box(
        modifier =
          Modifier.size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center,
      ) {
        Icon(
          imageVector = Icons.Outlined.Lightbulb,
          contentDescription = null,
          tint = BrandLogoCrescent,
          modifier = Modifier.size(22.dp),
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = stringResource(R.string.tips_card_title),
          color = Color.White,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = stringResource(R.string.tips_card_subtitle),
          color = Color.White.copy(alpha = 0.86f),
          style = MaterialTheme.typography.bodySmall,
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = null,
      tint = Color.White,
      modifier = Modifier.size(22.dp),
    )
  }
}

@Composable
fun MemorizationTipsPopup(
  visible: Boolean,
  onOpenModal: () -> Unit,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val scope = rememberCoroutineScope()
  val dragOffsetY = remember { Animatable(0f) }
  val density = LocalDensity.current

  LaunchedEffect(visible) {
    if (visible) dragOffsetY.snapTo(0f)
  }

  AnimatedVisibility(
    visible = visible,
    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(tween(220)),
    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(tween(180)),
    modifier = modifier,
  ) {
    val gradientBrush =
      Brush.linearGradient(
        colors = listOf(palette.gradientStart, palette.gradientMid, palette.gradientEnd)
      )

    Row(
      modifier =
        Modifier.widthIn(max = 420.dp)
          .fillMaxWidth()
          .offset { IntOffset(0, dragOffsetY.value.roundToInt()) }
          .shadow(10.dp, RoundedCornerShape(16.dp))
          .clip(RoundedCornerShape(16.dp))
          .background(gradientBrush)
          .border(1.dp, palette.popupBorder, RoundedCornerShape(16.dp))
          .pointerInput(Unit) {
            detectVerticalDragGestures(
              onDragEnd = {
                val threshold = with(density) { 48.dp.toPx() }
                if (dragOffsetY.value > threshold) {
                  onDismiss()
                } else {
                  scope.launch { dragOffsetY.animateTo(0f, tween(160)) }
                }
              },
              onVerticalDrag = { _, dragAmount ->
                scope.launch {
                  dragOffsetY.snapTo((dragOffsetY.value + dragAmount).coerceAtLeast(0f))
                }
              },
            )
          }
          .clickable(onClick = onOpenModal)
          .padding(start = 14.dp, top = 10.dp, bottom = 10.dp, end = 6.dp)
          .testTag("memorization_tips_popup"),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          imageVector = Icons.Outlined.Lightbulb,
          contentDescription = null,
          tint = BrandLogoCrescent,
          modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = stringResource(R.string.tips_card_title),
            color = Color.White,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
          )
          Text(
            text = "Ketuk untuk lihat 5 langkah ringkas",
            color = BrandLogoCrescent.copy(alpha = 0.92f),
            style = MaterialTheme.typography.bodySmall,
          )
        }
      }

      IconButton(
        onClick = onDismiss,
        modifier = Modifier.size(48.dp).testTag("dismiss_tips_popup_button"),
      ) {
        Icon(
          imageVector = Icons.Filled.Close,
          contentDescription = stringResource(R.string.close_label),
          tint = Color.White,
          modifier = Modifier.size(18.dp),
        )
      }
    }
  }
}

@Composable
fun MemorizationTipsModal(
  visible: Boolean,
  onDismiss: () -> Unit,
) {
  if (!visible) return

  BackHandler(enabled = visible, onBack = onDismiss)

  val palette = DoaTheme.colors
  val scope = rememberCoroutineScope()
  val offsetY = remember { Animatable(0f) }
  val density = LocalDensity.current
  val tips = remember { PrayerRepository.memorizationTips }

  Box(
    modifier =
      Modifier.fillMaxSize()
        .background(Color.Black.copy(alpha = 0.50f))
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          onClick = onDismiss,
        )
        .padding(16.dp),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      modifier =
        Modifier.widthIn(max = 540.dp)
          .fillMaxWidth()
          .heightIn(max = 620.dp)
          .offset { IntOffset(0, offsetY.value.roundToInt()) }
          .clip(RoundedCornerShape(20.dp))
          .background(palette.cardSurface)
          .border(1.dp, palette.borderPrimary, RoundedCornerShape(20.dp))
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {},
          )
          .pointerInput(Unit) {
            detectVerticalDragGestures(
              onDragEnd = {
                val threshold = with(density) { 96.dp.toPx() }
                if (offsetY.value > threshold) {
                  onDismiss()
                } else {
                  scope.launch { offsetY.animateTo(0f, tween(180)) }
                }
              },
              onVerticalDrag = { _, dragAmount ->
                scope.launch {
                  offsetY.snapTo((offsetY.value + dragAmount).coerceAtLeast(0f))
                }
              },
            )
          }
          .windowInsetsPadding(WindowInsets.navigationBars)
          .testTag("memorization_tips_modal")
    ) {
      // Title bar (strictly no subtitle and no "Mengerti" footer button per spec)
      Row(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 8.dp, top = 12.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          text = stringResource(R.string.tips_modal_title),
          color = palette.textHeading,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
        )
        IconButton(
          onClick = onDismiss,
          modifier = Modifier.size(48.dp).testTag("close_tips_modal_button"),
        ) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close_label),
            tint = palette.textSecondary,
          )
        }
      }

      HorizontalDivider(thickness = 1.dp, color = palette.divider)

      Column(
        modifier =
          Modifier.fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
      ) {
        tips.forEach { tip ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            Box(
              modifier =
                Modifier.size(28.dp)
                  .clip(CircleShape)
                  .background(palette.readBadgeBg),
              contentAlignment = Alignment.Center,
            ) {
              Text(
                text = tip.number.toString(),
                color = palette.readBadgeText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = tip.title,
                color = palette.textHeading,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = tip.description,
                color = palette.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
              )
            }
          }
        }
      }
    }
  }
}
