package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SplashBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  visible: Boolean,
  onFinished: () -> Unit,
) {
  val scaleAnim = remember { Animatable(0.88f) }

  LaunchedEffect(visible) {
    if (visible) {
      scaleAnim.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing),
      )
      delay(430L)
      onFinished()
    }
  }

  AnimatedVisibility(
    visible = visible,
    exit = fadeOut(animationSpec = tween(durationMillis = 300)),
  ) {
    Box(
      modifier =
        Modifier.fillMaxSize()
          .background(SplashBackground)
          .testTag("splash_screen")
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onFinished,
          ),
      contentAlignment = Alignment.Center,
    ) {
      BrandLogo(
        size = 96.dp,
        modifier = Modifier.scale(scaleAnim.value),
      )
    }
  }
}
