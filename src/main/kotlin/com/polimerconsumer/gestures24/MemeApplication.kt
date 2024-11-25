package com.polimerconsumer.gestures24

import com.polimerconsumer.gestures24.geom.Border
import java.awt.MouseInfo
import java.awt.Rectangle
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.Timer

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

        var lastOnScreenX = -1
        var lastOnScreenY = -1

        Timer(1) {
            val pointerLocation = MouseInfo.getPointerInfo().location
            lastOnScreenX = pointerLocation.x
            lastOnScreenY = pointerLocation.y
        }.start()

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                val currentOnScreenX = e.xOnScreen
                val currentOnScreenY = e.yOnScreen

                val frameBounds = frame.bounds

                val entrySide = findEntrySide(
                    lastOnScreenX, lastOnScreenY,
                    currentOnScreenX, currentOnScreenY,
                    frameBounds
                )

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

fun findEntrySide(
    lastOnScreenX: Int,
    lastOnScreenY: Int,
    currentOnScreenX: Int,
    currentOnScreenY: Int,
    frameBounds: Rectangle
): EntrySide {
    val leftEdge = Border(0, frameBounds.y, 0, frameBounds.y + frameBounds.height)
    val rightEdge = Border(frameBounds.width, frameBounds.y, frameBounds.width, frameBounds.y + frameBounds.height)
    val topEdge = Border(frameBounds.x, 0, frameBounds.x + frameBounds.width, 0)
    val bottomEdge = Border(frameBounds.x, frameBounds.height, frameBounds.x + frameBounds.width, frameBounds.height)

    val mouseTrajectory = Border(lastOnScreenX, lastOnScreenY, currentOnScreenX, currentOnScreenY)

    return when {
        mouseTrajectory.intersects(leftEdge) -> EntrySide.LEFT
        mouseTrajectory.intersects(rightEdge) -> EntrySide.RIGHT
        mouseTrajectory.intersects(topEdge) -> EntrySide.TOP
        mouseTrajectory.intersects(bottomEdge) -> EntrySide.BOTTOM
        else -> EntrySide.UNDEFINED
    }
}
