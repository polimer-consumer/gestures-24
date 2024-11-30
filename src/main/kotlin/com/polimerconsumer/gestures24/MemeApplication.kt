package com.polimerconsumer.gestures24

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.imageio.ImageIO.read

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Base window").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(1080, 720)
            layout = null
            isVisible = true
        }

        val imageStream = Meme::class.java.getResourceAsStream("/memes/tigrib.png")
            ?: throw IllegalArgumentException("Image not found")

        val image = read(imageStream)
        val memeIcon = ImageIcon(image)
        val meme = Meme(memeIcon, frame)

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                val bounds = frame.bounds
                val insets = frame.insets
                val distanceToLeft = e.x
                val distanceToRight = bounds.width - e.x
                val distanceToTop = e.y - insets.top
                val distanceToBottom = bounds.height - e.y

                val entrySide = when (
                    minOf(distanceToLeft, distanceToRight, distanceToTop, distanceToBottom)
                ) {
                    distanceToLeft -> EntrySide.LEFT
                    distanceToRight -> EntrySide.RIGHT
                    distanceToTop -> EntrySide.TOP
                    distanceToBottom -> EntrySide.BOTTOM
                    else -> EntrySide.UNDEFINED
                }

                meme.onMouseEnter(e.x, e.y, entrySide)
            }

            override fun mouseExited(e: MouseEvent) {
                meme.onMouseExit()
            }
        })

        frame.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                meme.onMouseMove(e.x, e.y)
            }
        })
    }
}
