package com.tahhan.spendless.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun ThousandsSeparator(
    selectedSeparator: Separator,
    onSeparatorSelected: (Separator) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedOffset by remember { mutableFloatStateOf(0f) }
    val animatedOffset by animateFloatAsState(
        targetValue = selectedOffset,
        animationSpec = tween(durationMillis = 700)
    )
    val density = LocalDensity.current
    var width by remember { mutableStateOf(0.dp) }
    var backgroundWidth by remember { mutableStateOf(0.dp) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .onGloballyPositioned { layoutCoordinates ->
                width = with(density) { layoutCoordinates.size.width.toDp() - 8.dp }
                backgroundWidth = (width / 3).value.dp
                selectedOffset = when (selectedSeparator) {
                    Separator.DOT -> 0f
                    Separator.COMMA -> width.value / 3
                    Separator.SPACE -> 2 * width.value / 3
                }
            }
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f))
            .padding(4.dp)
    ) {
        Box( // Acts as an animated background
            modifier = Modifier
                .fillMaxHeight()
                .width(backgroundWidth)
                .offset(x = animatedOffset.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp)
                )
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Separator(
                text = "1.000",
                onClick = {
                    onSeparatorSelected(Separator.DOT)
                    selectedOffset = 0f
                },
                modifier = Modifier.weight(1f)
            )

            Separator(
                text = "1,000",
                onClick = {
                    onSeparatorSelected(Separator.COMMA)
                    selectedOffset = (width / 3).value
                },
                modifier = Modifier.weight(1f)
            )

            Separator(
                text = "1 000",
                onClick = {
                    onSeparatorSelected(Separator.SPACE)
                    selectedOffset = (2f * width.value / 3)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Separator(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxHeight()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

enum class Separator {
    DOT,
    COMMA,
    SPACE
}