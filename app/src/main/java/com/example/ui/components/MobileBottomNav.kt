package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.MainTab
import com.example.ui.theme.DoaTheme

@Composable
fun MobileBottomNav(
  activeTab: MainTab,
  savedCount: Int,
  onSelectTab: (MainTab) -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors

  Column(
    modifier =
      modifier.fillMaxWidth()
        .background(palette.bottomNavBg)
        .windowInsetsPadding(WindowInsets.navigationBars)
  ) {
    HorizontalDivider(thickness = 1.dp, color = palette.divider)
    Row(
      modifier = Modifier.fillMaxWidth().height(58.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      BottomNavItem(
        label = stringResource(R.string.tab_doa),
        selected = activeTab == MainTab.DOA,
        selectedIcon = Icons.Filled.MenuBook,
        unselectedIcon = Icons.Outlined.MenuBook,
        onClick = { onSelectTab(MainTab.DOA) },
        modifier = Modifier.weight(1f).testTag("bottom_nav_doa"),
      )
      BottomNavItem(
        label = "${stringResource(R.string.tab_tersimpan)} ($savedCount)",
        selected = activeTab == MainTab.TERSIMPAN,
        selectedIcon = Icons.Filled.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        onClick = { onSelectTab(MainTab.TERSIMPAN) },
        modifier = Modifier.weight(1f).testTag("bottom_nav_tersimpan"),
      )
    }
  }
}

@Composable
private fun BottomNavItem(
  label: String,
  selected: Boolean,
  selectedIcon: ImageVector,
  unselectedIcon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val tint by
    animateColorAsState(
      targetValue = if (selected) palette.accentGreen else palette.textSecondary,
      label = "bottomNavTint",
    )
  val indicatorWidth by
    animateDpAsState(
      targetValue = if (selected) 48.dp else 0.dp,
      animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = 0.8f),
      label = "bottomNavIndicator",
    )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top,
    modifier =
      modifier
        .height(58.dp)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          onClick = onClick,
        ),
  ) {
    Box(
      modifier =
        Modifier.width(indicatorWidth)
          .height(3.dp)
          .clip(RoundedCornerShape(bottomStart = 3.dp, bottomEnd = 3.dp))
          .background(palette.accentGreen)
    )
    Spacer(modifier = Modifier.height(7.dp))
    Icon(
      imageVector = if (selected) selectedIcon else unselectedIcon,
      contentDescription = label,
      tint = tint,
      modifier = Modifier.size(22.dp),
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      color = tint,
      style = MaterialTheme.typography.labelSmall,
      fontSize = 11.5.sp,
      fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
    )
  }
}
