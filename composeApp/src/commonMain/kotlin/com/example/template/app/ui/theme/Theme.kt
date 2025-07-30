package com.example.template.app.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.template.app.viewModels.AppViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

private val lightThemeColors = lightColorScheme(
    primary = AppColor,
    primaryContainer = Blue400,
    onPrimary = Color.White,
    secondary = Color.White,
    secondaryContainer = Teal300,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = SurfaceLight
)

private val darkThemeColors = darkColorScheme(
    primary = AppColor,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = SurfaceDark,
    onSurface = Color.White
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkThemeColors
        else -> lightThemeColors
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                content()
            }
        }
    }
}