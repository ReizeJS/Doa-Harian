package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BrandLogoBook
import com.example.ui.theme.BrandLogoCrescent
import com.example.ui.theme.BrandLogoSquare

/**
 * Custom vector BrandLogo:
 * Rounded square #0B553F, curved open book pages #ECF6F0, and crescent moon #A7F0D1.
 */
@Composable
fun BrandLogo(
  modifier: Modifier = Modifier,
  size: Dp = 36.dp,
  squareColor: Color = BrandLogoSquare,
) {
  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height
    val corner = w * 0.24f

    // Rounded background square
    drawRoundRect(
      color = squareColor,
      topLeft = Offset.Zero,
      size = Size(w, h),
      cornerRadius = CornerRadius(corner, corner),
    )

    // Crescent moon (#A7F0D1) centered near top
    val moonCenterX = w * 0.50f
    val moonCenterY = h * 0.27f
    val moonOuterR = w * 0.145f
    val crescentPath =
      Path().apply {
        addOval(
          androidx.compose.ui.geometry.Rect(
            left = moonCenterX - moonOuterR,
            top = moonCenterY - moonOuterR,
            right = moonCenterX + moonOuterR,
            bottom = moonCenterY + moonOuterR,
          )
        )
      }
    val cutoutPath =
      Path().apply {
        val cutOffsetX = w * 0.065f
        val cutOffsetY = -h * 0.03f
        val cutR = moonOuterR * 0.84f
        addOval(
          androidx.compose.ui.geometry.Rect(
            left = moonCenterX + cutOffsetX - cutR,
            top = moonCenterY + cutOffsetY - cutR,
            right = moonCenterX + cutOffsetX + cutR,
            bottom = moonCenterY + cutOffsetY + cutR,
          )
        )
      }
    val finalCrescent =
      Path().apply {
        op(crescentPath, cutoutPath, androidx.compose.ui.graphics.PathOperation.Difference)
      }
    drawPath(path = finalCrescent, color = BrandLogoCrescent)

    // Open book curved silhouette (#ECF6F0)
    val strokeWidth = (w * 0.068f).coerceAtLeast(1.8f)
    val bookPath =
      Path().apply {
        // Left page top arch -> center spine -> right page top arch
        moveTo(w * 0.21f, h * 0.48f)
        quadraticTo(w * 0.36f, h * 0.44f, w * 0.50f, h * 0.55f)
        quadraticTo(w * 0.64f, h * 0.44f, w * 0.79f, h * 0.48f)
        // Right edge down
        lineTo(w * 0.79f, h * 0.75f)
        // Right page bottom arch -> spine bottom -> left page bottom arch
        quadraticTo(w * 0.64f, h * 0.70f, w * 0.50f, h * 0.80f)
        quadraticTo(w * 0.36f, h * 0.70f, w * 0.21f, h * 0.75f)
        close()
      }
    drawPath(
      path = bookPath,
      color = BrandLogoBook,
      style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round),
    )

    // Center spine vertical line
    drawLine(
      color = BrandLogoBook,
      start = Offset(w * 0.50f, h * 0.55f),
      end = Offset(w * 0.50f, h * 0.79f),
      strokeWidth = strokeWidth * 0.9f,
      cap = StrokeCap.Round,
    )
  }
}
