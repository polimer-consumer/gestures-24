package com.polimerconsumer.gestures24.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.polimerconsumer.gestures24.main
import javax.swing.SwingUtilities

class LaunchAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        SwingUtilities.invokeLater {
            main()
        }
    }
}
