package com.shinonometn.toybrick.common

import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JOptionPane
import kotlin.system.exitProcess

data class UIAppCriticalErrorMessage(val title: String, val message: String, val exception: Exception?)

fun UIAppCriticalErrorMessage.promptSwingDialog(icon: Image? = null, exitStatus: Int = 1) {
    if (icon != null) JOptionPane.showMessageDialog(
        null, message, title, JOptionPane.ERROR_MESSAGE, ImageIcon(icon)
    ) else JOptionPane.showMessageDialog(
        null, message, title, JOptionPane.ERROR_MESSAGE
    )
    exitProcess(exitStatus)
}