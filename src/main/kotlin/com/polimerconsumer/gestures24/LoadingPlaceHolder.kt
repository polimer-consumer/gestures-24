package com.polimerconsumer.gestures24

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class LoadingPlaceholder : JPanel() {
    init {
        background = Color(100, 50, 200, 128)
        isOpaque = false
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val size = width
        val x = (width - size) / 2
        val y = (height - size) / 2
        g.color = Color.YELLOW
        g.fillOval(x, y, size, size)
    }
}
