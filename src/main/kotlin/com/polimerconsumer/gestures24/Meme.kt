package com.polimerconsumer.gestures24

import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Meme(image: ImageIcon, parent: JFrame) {
    private val memeIcon = image
    private val memeLabel = JLabel(memeIcon)
    private val scaleFactor = 0.001
    private val baseScale = 0.25

    private var lastX = -1
    private var lastY = -1
    private var isScaling = true
    private var scale = baseScale
    private var entrySide = EntrySide.UNDEFINED

    init {
        memeLabel.isVisible = false
        parent.add(memeLabel)
    }

    fun onMouseEnter(x: Int, y: Int, side: EntrySide) {
        lastX = x
        lastY = y
        scale = baseScale
        isScaling = true
        entrySide = side
        updateImageAndPosition(x, y)
        memeLabel.isVisible = true
    }

    fun onMouseExit() {
        memeLabel.isVisible = false
    }

    fun onMouseMove(x: Int, y: Int) {
        if (!memeLabel.isVisible) return

        val delta = when (entrySide) {
            EntrySide.RIGHT -> -(x - lastX)
            EntrySide.LEFT -> x - lastX
            EntrySide.TOP -> y - lastY
            EntrySide.BOTTOM -> -(y - lastY)
            EntrySide.UNDEFINED -> 0
        }

        if (delta > 0) {
            scale += abs(delta) * scaleFactor
            isScaling = scale > baseScale
        } else if (delta < 0) {
            scale -= abs(delta) * scaleFactor
            isScaling = scale < 1.0
        }

        scale = min(1.0, max(baseScale, scale))
        updateImageAndPosition(x, y)

        lastX = x
        lastY = y
    }

    private fun updateImageAndPosition(x: Int, y: Int) {
        memeLabel.icon = scaleImage(scale)
        memeLabel.setBounds(
            x - memeLabel.icon.iconWidth / 2,
            y - memeLabel.icon.iconHeight / 2,
            memeLabel.icon.iconWidth,
            memeLabel.icon.iconHeight
        )
    }

    private fun scaleImage(scale: Double): ImageIcon {
        val newWidth = (memeIcon.iconWidth * scale).toInt()
        val newHeight = (memeIcon.iconHeight * scale).toInt()
        val scaledImage = memeIcon.image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)
        return ImageIcon(scaledImage)
    }
}
