package `in`.imperialb.imperialjetbrains

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project


object Notify {
    private val notificationInstance = NotificationGroupManager.getInstance().getNotificationGroup("imperial.notifications")
    fun errorNotification(project: Project?, content: String) {
        notificationInstance
            .createNotification(content, NotificationType.ERROR).notify(project)
    }

    fun successNotification(project: Project?, content: String, documentID: String?) {
        if(documentID !== null && documentID.isNotEmpty()) {
            notificationInstance
                .createNotification(content, NotificationType.INFORMATION).addAction("in.imperialb.imperialjetbrains.CreateEntireDocument").notify(project)
            return;
        }
        notificationInstance
            .createNotification(content, NotificationType.INFORMATION).notify(project)
    }
}