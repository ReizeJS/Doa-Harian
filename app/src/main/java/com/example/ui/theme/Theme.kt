package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DoaLightColorScheme =
  lightColorScheme(
    primary = LightEmeraldPalette.accentGreen,
    onPrimary = Color.White,
    primaryContainer = LightEmeraldPalette.readBadgeBg,
    onPrimaryContainer = LightEmeraldPalette.readBadgeText,
    secondary = LightEmeraldPalette.tabInactiveBg,
    onSecondary = LightEmeraldPalette.tabInactiveText,
    background = LightEmeraldPalette.pageBackground,
    onBackground = LightEmeraldPalette.textPrimary,
    surface = LightEmeraldPalette.cardSurface,
    onSurface = LightEmeraldPalette.textPrimary,
    surfaceVariant = LightEmeraldPalette.arabicBlockBg,
    onSurfaceVariant = LightEmeraldPalette.textSecondary,
    outline = LightEmeraldPalette.borderPrimary,
    outlineVariant = LightEmeraldPalette.divider,
  )

private val DoaDarkColorScheme =
  darkColorScheme(
    primary = DarkEmeraldPalette.accentGreen,
    onPrimary = Color(0xFF08150F),
    primaryContainer = DarkEmeraldPalette.readBadgeBg,
    onPrimaryContainer = DarkEmeraldPalette.readBadgeText,
    secondary = DarkEmeraldPalette.tabInactiveBg,
    onSecondary = DarkEmeraldPalette.tabInactiveText,
    background = DarkEmeraldPalette.pageBackground,
    onBackground = DarkEmeraldPalette.textPrimary,
    surface = DarkEmeraldPalette.cardSurface,
    onSurface = DarkEmeraldPalette.textPrimary,
    surfaceVariant = DarkEmeraldPalette.arabicBlockBg,
    onSurfaceVariant = DarkEmeraldPalette.textSecondary,
    outline = DarkEmeraldPalette.borderPrimary,
    outlineVariant = DarkEmeraldPalette.divider,
  )

@Composable
fun DoaHarianTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val emeraldPalette = if (darkTheme) DarkEmeraldPalette else LightEmeraldPalette
  val colorScheme = if (darkTheme) DoaDarkColorScheme else DoaLightColorScheme

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        val insetsController = WindowCompat.getInsetsController(window, view)
        // Top header is always dark Forest Emerald (#0F6E52 or #10261C), so keep status bar icons light
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = !darkTheme
      }
    }
  }

  CompositionLocalProvider(LocalEmeraldPalette provides emeraldPalette) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = DoaTypography,
      content = content,
    )
  }
}

object DoaTheme {
  val colors: EmeraldPalette
    @Composable get() = LocalEmeraldPalette.current
}
