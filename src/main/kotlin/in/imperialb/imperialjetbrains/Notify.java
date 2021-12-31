package in.imperialb.imperialjetbrains;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

public class Notify {
    private static final NotificationGroup notificationInstance = NotificationGroupManager.getInstance().getNotificationGroup("IMPERIAL");
    public static void errorNotification(String content) {
        notificationInstance.createNotification(content, NotificationType.ERROR).notify();
    }

    public static void successNotification(Project project, String content) {
        notificationInstance.createNotification(content, NotificationType.INFORMATION).notify(project);
    }
}
