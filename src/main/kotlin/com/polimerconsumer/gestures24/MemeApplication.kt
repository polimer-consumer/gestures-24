package com.polimerconsumer.gestures24

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Base window").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(1080, 720)
            layout = null
            isVisible = true
        }

        val imagePath = {}::class.java.getResource("/memes/tigrib.png")?.path
            ?: throw IllegalArgumentException("Image not found")

        val meme = Meme(imagePath, frame)

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                val bounds = frame.bounds
                val entrySide = when {
                    e.x <= 0 -> EntrySide.LEFT
                    e.x >= bounds.width - 1 -> EntrySide.RIGHT
                    e.y <= 0 -> EntrySide.TOP
                    e.y >= bounds.height - 1 -> EntrySide.BOTTOM
                    else -> EntrySide.UNDEFINED
                }

                println(entrySide)

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
