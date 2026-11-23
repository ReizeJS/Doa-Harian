package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppTimeZone
import com.example.model.ContextualRecommendation
import com.example.ui.theme.BrandLogoCrescent
import com.example.ui.theme.DoaTheme
import com.example.ui.theme.JetBrainsMonoFontFamily

@Composable
fun RecommendationCard(
  recommendation: ContextualRecommendation,
  selectedTimeZone: AppTimeZone?,
  onSelectTimeZone: (AppTimeZone) -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val gradientBrush =
    Brush.linearGradient(
      colors = listOf(palette.gradientStart, palette.gradientMid, palette.gradientEnd)
    )
  var showTzMenu by remember { mutableStateOf(false) }

  Box(
    modifier =
      modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(gradientBrush)
        .border(1.dp, palette.popupBorder, RoundedCornerShape(16.dp))
        .clickable(onClick = onClick)
        .padding(16.dp)
        .testTag("recommendation_card")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            Icon(
              imageVector = Icons.Outlined.Schedule,
              contentDescription = null,
              tint = BrandLogoCrescent,
              modifier = Modifier.size(14.dp),
            )
            Text(
              text =
                "${stringResource(R.string.recommendation_prefix)} · ${recommendation.timeFormatted} ${recommendation.timeZoneLabel} · ${recommendation.contextLabel}",
              color = BrandLogoCrescent,
              fontFamily = JetBrainsMonoFontFamily,
              fontWeight = FontWeight.Medium,
              fontSize = 11.sp,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Box {
            Text(
              text = recommendation.timeZoneLabel,
              color = Color.White,
              fontFamily = JetBrainsMonoFontFamily,
              fontWeight = FontWeight.SemiBold,
              fontSize = 10.5.sp,
              modifier =
                Modifier.clip(RoundedCornerShape(99.dp))
                  .background(Color.White.copy(alpha = 0.16f))
                  .clickable { showTzMenu = true }
                  .padding(horizontal = 8.dp, vertical = 3.dp)
                  .testTag("timezone_selector_pill"),
            )

            DropdownMenu(
              expanded = showTzMenu,
              onDismissRequest = { showTzMenu = false },
              modifier = Modifier.background(palette.cardSurface),
            ) {
              AppTimeZone.entries.forEach { tzOption ->
                val isSelected = selectedTimeZone == tzOption
                DropdownMenuItem(
                  text = {
                    Text(
                      text = tzOption.description,
                      color = palette.textPrimary,
                      style = MaterialTheme.typography.bodyMedium,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                  },
                  trailingIcon = {
                    if (isSelected) {
                      Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = palette.accentGreen,
                        modifier = Modifier.size(16.dp),
                      )
                    }
                  },
                  onClick = {
                    showTzMenu = false
                    onSelectTimeZone(tzOption)
                  },
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(6.dp))
        AnimatedContent(
          targetState = recommendation.prayer,
          transitionSpec = {
            (slideInVertically { it / 4 } + fadeIn()) togetherWith
              (slideOutVertically { -it / 4 } + fadeOut())
          },
          label = "recommendationPrayerTransition",
        ) { prayer ->
          Column {
            Text(
              text = prayer.title,
              color = Color.White,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = prayer.contextHint,
              color = Color.White.copy(alpha = 0.86f),
              style = MaterialTheme.typography.bodySmall,
              maxLines = 2,
              overflow = TextOverflow.Ellipsis,
            )
          }
        }
      }

      Spacer(modifier = Modifier.width(10.dp))

      Box(
        modifier =
          Modifier.size(34.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center,
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(20.dp),
        )
      }
    }
  }
}
