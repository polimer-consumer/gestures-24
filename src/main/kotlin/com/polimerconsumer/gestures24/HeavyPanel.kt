package com.polimerconsumer.gestures24

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.swing.JPanel
import java.awt.Color

class HeavyPanel : JPanel() {
    init {
        // Simulate heavy computation during initialization
        performHeavyInitialization()
    }

    private fun performHeavyInitialization() {
        // Simulate long operation
        for (i in 1..30_000_000_000) {
            val longOperation = i * i
        }
        background = Color(100, 200, 100)
    }

    companion object {
        /**
         * Factory function to initialize panel in background.
         */
        suspend fun create(): HeavyPanel = withContext(Dispatchers.Default) {
            HeavyPanel()
        }
    }
}
