package com.polimerconsumer.gestures24

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Abstract class that provides common methods and fields for mouse events and scaling for frames (ex. Meme).
 *
 * @param scaleFactor scaling factor used to adjust component's size based on mouse movement
 * @param baseScale initial scale of component
 */
abstract class ScalablePanel(
    private val scaleFactor: Double = 0.001,
    private val baseScale: Double = 0.25
) {
    var scale = baseScale
        protected set

    private var lastX = -1
    private var lastY = -1
    private var entrySide = EntrySide.UNDEFINED

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
        updateScaleAndPosition(x, y)
    }

    /**
     * Updates components parameters when it is visible.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     */
    protected abstract fun onPanelShow(x: Int, y: Int)

    /**
     * Updates components parameters when it's hidden.
     */
    protected abstract fun onPanelHidden()

    /**
     * Updates scale and position of component.
     *
     * @param x the x-coordinate of mouse
     * @param y the y-coordinate of mouse
     */
    protected abstract fun updateScaleAndPosition(x: Int, y: Int)

    protected abstract fun isPanelVisible(): Boolean
}
