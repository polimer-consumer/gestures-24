package com.polimerconsumer.gestures24

import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Base window").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(800, 600)
            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}
