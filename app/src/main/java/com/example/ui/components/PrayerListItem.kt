package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Prayer
import com.example.ui.theme.DoaTheme

@Composable
fun PrayerListItem(
  prayer: Prayer,
  isSelected: Boolean,
  isSaved: Boolean,
  isReadToday: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val palette = DoaTheme.colors
  val backgroundColor by
    animateColorAsState(
      targetValue = if (isSelected) palette.itemSelectedBg else palette.cardSurface,
      label = "prayerItemBg",
    )
  val borderColor by
    animateColorAsState(
      targetValue = if (isSelected) palette.accentGreen else palette.borderPrimary,
      label = "prayerItemBorder",
    )

  Row(
    modifier =
      modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(backgroundColor)
        .border(
          width = if (isSelected) 1.5.dp else 1.dp,
          color = borderColor,
          shape = RoundedCornerShape(14.dp),
        )
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 14.dp)
        .testTag("prayer_item_${prayer.id}"),
    verticalAlignment = Alignment.Top,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Text(
          text = prayer.category.label,
          color = palette.accentGreen,
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.SemiBold,
        )
        if (isReadToday) {
          Text(
            text = stringResource(R.string.badge_read),
            color = palette.readBadgeText,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier =
              Modifier.clip(RoundedCornerShape(99.dp))
                .background(palette.readBadgeBg)
                .padding(horizontal = 8.dp, vertical = 2.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = prayer.title,
        color = palette.textPrimary,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )

      Spacer(modifier = Modifier.height(3.dp))

      Text(
        text = prayer.contextHint,
        color = palette.textSecondary,
        style = MaterialTheme.typography.bodySmall,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
      )
    }

    if (isSaved) {
      Spacer(modifier = Modifier.width(10.dp))
      Icon(
        imageVector = Icons.Filled.Bookmark,
        contentDescription = stringResource(R.string.action_saved),
        tint = palette.accentGreen,
        modifier = Modifier.size(18.dp).padding(top = 2.dp),
      )
    }
  }
}
