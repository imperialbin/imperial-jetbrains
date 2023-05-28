package `in`.imperialb.imperial

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction

class Notifications {
    companion object {
        fun notify(message: String, documentId: String? = null) {
            val notification = Notification(
                "IMPERIAL",
                "IMPERIAL",
                message,
                com.intellij.notification.NotificationType.INFORMATION,
            )
            
            if (documentId != null) {
                notification.addAction(NotificationAction.createSimple("Open Document") {
                    val url = "https://impb.in/$documentId"
                    java.awt.Desktop.getDesktop().browse(java.net.URI(url))
                })
                notification.addAction(NotificationAction.createSimple("Copy URL") {
                    val url = "https://impb.in/$documentId"
                    val clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
                    val selection = java.awt.datatransfer.StringSelection(url)
                    clipboard.setContents(selection, selection)
                })
            }
            com.intellij.notification.Notifications.Bus.notify(notification)
        }

        fun notifyError(message: String) {
            val notification = Notification(
                "IMPERIAL",
                "IMPERIAL",
                message,
                com.intellij.notification.NotificationType.ERROR
            )
            com.intellij.notification.Notifications.Bus.notify(notification)
        }

        fun notifyWarning(message: String) {
            val notification = Notification(
                "IMPERIAL",
                "IMPERIAL",
                message,
                com.intellij.notification.NotificationType.WARNING
            )
            com.intellij.notification.Notifications.Bus.notify(notification)
        }
    }
}