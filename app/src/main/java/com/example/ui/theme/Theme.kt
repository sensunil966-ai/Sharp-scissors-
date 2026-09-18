package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF14120B),
    primaryContainer = GoldContainer,
    onPrimaryContainer = OnGoldContainer,
    secondary = GoldAccent,
    onSecondary = Color(0xFF1A1504),
    secondaryContainer = Color(0xFF2B2516),
    onSecondaryContainer = GoldLight,
    tertiary = GoldLight,
    onTertiary = Color(0xFF1F1805),
    background = BlackBackground,
    onBackground = TextPrimary,
    surface = BlackSurface,
    onSurface = TextPrimary,
    surfaceVariant = BlackSurfaceCard,
    onSurfaceVariant = TextSecondary,
    outline = BlackSurfaceBorder,
    outlineVariant = Color(0xFF4A4328)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = GoldDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBF4E4),
    onPrimaryContainer = Color(0xFF3B2A05),
    secondary = GoldPrimary,
    onSecondary = Color.Black,
    tertiary = Color(0xFF5E491A),
    background = Color(0xFF111116), // Retain the signature barbershop atmosphere
    onBackground = TextPrimary,
    surface = BlackSurface,
    onSurface = TextPrimary,
    surfaceVariant = BlackSurfaceCard,
    onSurfaceVariant = TextSecondary,
    outline = BlackSurfaceBorder,
  )

@Composable
fun SharpScissorsTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

