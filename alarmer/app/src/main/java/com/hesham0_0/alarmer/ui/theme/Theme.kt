package com.hesham0_0.alarmer.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = AlarmerPrimary,
    onPrimary = AlarmerOnPrimary,
    primaryContainer = AlarmerPrimaryContainer,

    background = AlarmerBackground,
    onBackground = AlarmerOnBackground,

    surface = AlarmerSurface,
    onSurface = AlarmerOnSurface,
    surfaceVariant = AlarmerSurfaceVariant,
    onSurfaceVariant = AlarmerOnSurfaceVariant,

    outline = AlarmerOutline,
)

private val DarkColorScheme = darkColorScheme(
    primary = AlarmerRed,
    onPrimary = Color.White,
    primaryContainer = AlarmerRedMuted,

    background = AlarmerDarkBackground,
    onBackground = AlarmerDarkOnBackground,

    surface = AlarmerDarkSurface,
    onSurface = AlarmerDarkOnSurface,

    surfaceVariant = AlarmerDarkSurfaceVariant,
    onSurfaceVariant = AlarmerDarkOnSurfaceVariant,

    outline = AlarmerDarkOutline
)

@Composable
fun AlarmerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}