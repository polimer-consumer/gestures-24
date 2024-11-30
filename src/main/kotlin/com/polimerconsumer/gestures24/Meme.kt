package com.polimerconsumer.gestures24

import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

/**
 * Class that represents mem (lol :0 ), implements ScalablePanel. Meme scales
 * and moves based on mouse movement and rules in test task description.
 *
 * @param memeIcon meme image
 * @param parent parent JFrame into which meme is added
 */
class Meme(
    private val memeIcon: ImageIcon, parent: JFrame
) : ScalablePanel() {
    private val memeLabel = JLabel(memeIcon)

    init {
        memeLabel.isVisible = false
        parent.add(memeLabel)
    }

    override fun onPanelShow(x: Int, y: Int) {
        updateScaleAndPosition(x, y)
        memeLabel.isVisible = true
    }

    override fun onPanelHidden() {
        memeLabel.isVisible = false
    }

    override fun updateScaleAndPosition(x: Int, y: Int) {
        memeLabel.icon = scaleImage(scale)
        memeLabel.setBounds(
            x - memeLabel.icon.iconWidth / 2,
            y - memeLabel.icon.iconHeight / 2,
            memeLabel.icon.iconWidth,
            memeLabel.icon.iconHeight
        )
    }

    override fun isPanelVisible(): Boolean {
        return memeLabel.isVisible
    }

    private fun scaleImage(scale: Double): ImageIcon {
        val newWidth = (memeIcon.iconWidth * scale).toInt()
        val newHeight = (memeIcon.iconHeight * scale).toInt()
        val scaledImage = memeIcon.image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)
        return ImageIcon(scaledImage)
    }
}
