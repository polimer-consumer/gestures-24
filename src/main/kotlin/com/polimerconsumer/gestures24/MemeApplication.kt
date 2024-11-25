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
        val gap = 10

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                val bounds = frame.bounds
                val entrySide = when {
                    e.x <= gap -> EntrySide.LEFT
                    e.x >= bounds.width - gap -> EntrySide.RIGHT
                    e.y <= frame.insets.top + gap -> EntrySide.TOP
                    e.y >= bounds.height - gap -> EntrySide.BOTTOM
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
