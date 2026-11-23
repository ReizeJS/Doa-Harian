package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MainTab
import com.example.ui.theme.DoaTheme

@Composable
fun HeaderBar(
  isWideLayout: Boolean,
  activeTab: MainTab,
  savedCount: Int,
  isDarkMode: Boolean,
  isOffline: Boolean,
  onLogoClick: () -> Unit,
  onSelectTab: (MainTab) -> Unit,
  onToggleDarkMode: () -> Unit,
) {
  val palette = DoaTheme.colors

  Column(modifier = Modifier.fillMaxWidth()) {
    Box(
      modifier =
        Modifier.fillMaxWidth()
          .background(palette.headerBackground)
          .windowInsetsPadding(WindowInsets.statusBars),
      contentAlignment = Alignment.Center,
    ) {
      Row(
        modifier =
          Modifier.widthIn(max = 1152.dp)
            .fillMaxWidth()
            .height(if (isWideLayout) 64.dp else 54.dp)
            .padding(horizontal = if (isWideLayout) 24.dp else 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        // Left: BrandLogo (clean without title text on mobile per spec; with title on wide desktop)
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier =
            Modifier.clip(RoundedCornerShape(10.dp))
              .clickable(onClick = onLogoClick)
              .padding(vertical = 4.dp, horizontal = 2.dp)
              .testTag("header_logo_button"),
        ) {
          BrandLogo(size = if (isWideLayout) 36.dp else 34.dp)
          if (isWideLayout) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = stringResource(R.string.app_name),
              color = Color.White,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
          }
        }

        // Center tabs on Wide / Tablet / Desktop layout
        if (isWideLayout) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            DesktopHeaderTab(
              label = stringResource(R.string.tab_doa_desktop),
              selected = activeTab == MainTab.DOA,
              onClick = { onSelectTab(MainTab.DOA) },
              modifier = Modifier.testTag("desktop_tab_doa"),
            )
            DesktopHeaderTab(
              label = "${stringResource(R.string.tab_tersimpan)} ($savedCount)",
              selected = activeTab == MainTab.TERSIMPAN,
              onClick = { onSelectTab(MainTab.TERSIMPAN) },
              modifier = Modifier.testTag("desktop_tab_tersimpan"),
            )
          }
        }

        // Right: Theme toggle
        if (isWideLayout) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
              Modifier.clip(RoundedCornerShape(10.dp))
                .background(palette.headerButton)
                .border(1.dp, palette.headerBorder, RoundedCornerShape(10.dp))
                .clickable(onClick = onToggleDarkMode)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .testTag("theme_toggle_button"),
          ) {
            Icon(
              imageVector = if (isDarkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
              contentDescription =
                if (isDarkMode) stringResource(R.string.light_mode_label)
                else stringResource(R.string.dark_mode_label),
              tint = Color.White,
              modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text =
                if (isDarkMode) stringResource(R.string.light_mode_label)
                else stringResource(R.string.dark_mode_label),
              color = Color.White,
              style = MaterialTheme.typography.labelLarge,
            )
          }
        } else {
          IconButton(
            onClick = onToggleDarkMode,
            modifier =
              Modifier.size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .testTag("theme_toggle_button"),
          ) {
            Box(
              modifier =
                Modifier.size(36.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(palette.headerButton)
                  .border(1.dp, palette.headerBorder, RoundedCornerShape(10.dp)),
              contentAlignment = Alignment.Center,
            ) {
              Icon(
                imageVector =
                  if (isDarkMode) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                contentDescription =
                  if (isDarkMode) stringResource(R.string.light_mode_label)
                  else stringResource(R.string.dark_mode_label),
                tint = Color.White,
                modifier = Modifier.size(19.dp),
              )
            }
          }
        }
      }
    }

    HorizontalDivider(thickness = 1.dp, color = palette.headerBorder)

    // Offline indicator banner
    AnimatedVisibility(
      visible = isOffline,
      enter = expandVertically(),
      exit = shrinkVertically(),
    ) {
      Row(
        modifier =
          Modifier.fillMaxWidth()
            .background(palette.offlineBg)
            .border(width = 0.5.dp, color = palette.offlineBorder)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("offline_indicator"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
      ) {
        Icon(
          imageVector = Icons.Outlined.CloudOff,
          contentDescription = null,
          tint = palette.offlineText,
          modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = stringResource(R.string.offline_banner),
          color = palette.offlineText,
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
        )
      }
    }
  }
}

@Composable
private fun DesktopHeaderTab(
  label: String,
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val textColor by
    animateColorAsState(
      targetValue = if (selected) Color.White else Color.White.copy(alpha = 0.72f),
      animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
      label = "headerTabTextColor",
    )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier =
      modifier.clip(RoundedCornerShape(8.dp))
        .clickable(onClick = onClick)
        .padding(horizontal = 12.dp, vertical = 10.dp),
  ) {
    Text(
      text = label,
      color = textColor,
      style = MaterialTheme.typography.labelLarge,
      fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
    )
    Spacer(modifier = Modifier.height(6.dp))
    Box(
      modifier =
        Modifier.width(if (selected) 36.dp else 0.dp)
          .height(3.dp)
          .clip(RoundedCornerShape(99.dp))
          .background(if (selected) palette.headerIndicator else Color.Transparent)
    )
  }
}
