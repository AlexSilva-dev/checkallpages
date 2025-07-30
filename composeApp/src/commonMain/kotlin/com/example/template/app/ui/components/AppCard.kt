package com.example.template.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AppCard(
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    modifier: Modifier = Modifier
        .padding(20.dp)
        .clip(shape = MaterialTheme.shapes.large)
        .background(color = MaterialTheme.colorScheme.secondary),
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        modifier = modifier
    ) {
        content()
    }
}