package com.polimerconsumer.gestures24

import kotlinx.coroutines.*
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class HeavyPanelHandler(
    private val parent: JFrame,
    private val scaleFactor: Double = 0.001,
    private val baseScale: Double = 0.25
) {
    private val placeholder = LoadingPlaceholder()
    private var heavyPanel: HeavyPanel? = null
    private var scale = baseScale
    private var lastX = -1
    private var lastY = -1
    private var entrySide = EntrySide.UNDEFINED
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val baseWidth = 200
    private val baseHeight = 200

    init {
        parent.add(placeholder)
        placeholder.isVisible = true

        // Load heavy panel async
        coroutineScope.launch {
            heavyPanel = HeavyPanel.create().apply {
                bounds = placeholder.bounds
            }

            SwingUtilities.invokeLater {
                parent.add(heavyPanel)
                parent.remove(placeholder)
                parent.repaint()
            }
        }
    }

    fun onMouseEnter(x: Int, y: Int, side: EntrySide) {
        lastX = x
        lastY = y
        scale = baseScale
        entrySide = side
        onPanelShow(x, y)
    }

    fun onMouseExit() {
        onPanelHidden()
    }

    fun onMouseMove(x: Int, y: Int) {
        if (!isPanelVisible()) return

        val delta = when (entrySide) {
            EntrySide.RIGHT -> -(x - lastX)
            EntrySide.LEFT -> x - lastX
            EntrySide.TOP -> y - lastY
            EntrySide.BOTTOM -> -(y - lastY)
            EntrySide.UNDEFINED -> 0
        }

        if (delta > 0) {
            scale += abs(delta) * scaleFactor
        } else if (delta < 0) {
            scale -= abs(delta) * scaleFactor
        }

        scale = min(1.0, max(baseScale, scale))
        lastX = x
        lastY = y
        updatePositionAndScale(x, y)
    }

    fun updatePositionAndScale(x: Int, y: Int) {
        val component = heavyPanel ?: placeholder
        val scaledWidth = (baseWidth * scale).toInt()
        val scaledHeight = (baseHeight * scale).toInt()
        component.setBounds(
            x - scaledWidth / 2,
            y - scaledHeight / 2,
            scaledWidth,
            scaledHeight
        )
    }

    fun onPanelShow(x: Int, y: Int) {
        updatePositionAndScale(x, y)
        (heavyPanel ?: placeholder).isVisible = true
    }

    fun onPanelHidden() {
        (heavyPanel ?: placeholder).isVisible = false
    }

    fun isPanelVisible(): Boolean {
        return placeholder.isVisible || (heavyPanel?.isVisible == true)
    }

    fun cleanup() {
        coroutineScope.cancel()
    }
}
