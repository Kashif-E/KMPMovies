package com.kashif.common.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import javafx.embed.swing.JFXPanel
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.SwingUtilities

@Composable
fun JavaFXPanel(
    container: JPanel,
    panel: JFXPanel,
    modifier: Modifier = Modifier,
    onCreate: () -> Unit = {}
) {

    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = modifier.onGloballyPositioned { coordinates ->
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size

            // Convert Compose coordinates to Swing coordinates
            container.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )

            container.validate()
            container.repaint()
        },
        measurePolicy = { measurables, constraints ->
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            // Layout the container to take up the maximum available size
            layout(width, height) {
                // No content to place, just ensuring the container is sized correctly
            }
        }
    )

    DisposableEffect(Unit) {
        container.apply {
            layout = BorderLayout()
            add(panel)
            border = BorderFactory.createEmptyBorder()
        }

        // JavaFX panel setup
        SwingUtilities.invokeLater {
            onCreate.invoke()
        }

        onDispose {
            SwingUtilities.invokeLater {
                container.remove(panel)
            }
        }
    }

    // Use SwingPanel to integrate the Swing JPanel into Compose

    SwingPanel(
        factory = { container },
        modifier = modifier
    )

}
