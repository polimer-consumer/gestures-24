package com.polimerconsumer.gestures24

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Base window").apply {
            defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            setSize(1080, 720)
            layout = null
            isVisible = true
        }

        val handler = HeavyPanelHandler(frame)

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
                println(entrySide)

                handler.onMouseEnter(e.x, e.y, entrySide)
            }

            override fun mouseExited(e: MouseEvent) {
                handler.onMouseExit()
            }
        })

        frame.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                handler.onMouseMove(e.x, e.y)
            }
        })

        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                handler.cleanup()
                frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            }
        })

    }
}
