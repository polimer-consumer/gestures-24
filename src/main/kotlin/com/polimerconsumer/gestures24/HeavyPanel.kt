package com.polimerconsumer.gestures24

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.swing.JPanel
import java.awt.Color

class HeavyPanel : JPanel() {
    init {
        Thread.sleep(3000)
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
