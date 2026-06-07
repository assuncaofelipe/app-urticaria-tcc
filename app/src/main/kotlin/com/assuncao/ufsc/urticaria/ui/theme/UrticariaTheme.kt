package com.assuncao.ufsc.urticaria.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Paleta calma e acolhedora — tons médicos (azul-lavanda)
private val LightColors = lightColorScheme(
    primary = Color(0xFF5B7FBE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD9E3F5),
    onPrimaryContainer = Color(0xFF0D3580),
    secondary = Color(0xFF8E7FBE),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8E3F7),
    onSecondaryContainer = Color(0xFF2D1D6E),
    background = Color(0xFFF5F7FF),
    onBackground = Color(0xFF1A1C2A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C2A),
    onSurfaceVariant = Color(0xFF737589),
    outline = Color(0xFFBFC1D0),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9BB5E8),
    onPrimary = Color(0xFF0D2D6B),
    primaryContainer = Color(0xFF1E4494),
    onPrimaryContainer = Color(0xFFD9E3F5),
    secondary = Color(0xFFBFB3E8),
    onSecondary = Color(0xFF2D1D6E),
    background = Color(0xFF12131A),
    onBackground = Color(0xFFE4E5F0),
    surface = Color(0xFF1E2030),
    onSurface = Color(0xFFE4E5F0),
    onSurfaceVariant = Color(0xFFA8AAC0),
)

@Composable
fun UrticariaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
