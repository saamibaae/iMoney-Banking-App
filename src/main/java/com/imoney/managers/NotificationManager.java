package com.imoney.managers;

import com.imoney.models.Notification;
import com.imoney.enums.NotificationType;
import com.imoney.enums.Priority;
import com.imoney.interfaces.Notifiable;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements Notifiable {

    private ArrayList<Notification> notifications;

    public NotificationManager() {
        this.notifications = new ArrayList<>();
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Notification createNotification(NotificationType type, String message, Priority priority) {
        Notification notification = new Notification(type, message, priority);
        notifications.add(notification);
        sendNotification(message);
        return notification;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("🔔 NOTIFICATION: " + message);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getUnreadNotifications() {
        List<Notification> unread = new ArrayList<>();
        for (Notification n : notifications) {
            if (!n.isRead()) {
                unread.add(n);
            }
        }
        return unread;
    }

    public List<Notification> getAllNotifications() {
        return new ArrayList<>(notifications);
    }

    public void markAsRead(Notification notification) {
        notification.setRead(true);
    }

    public void clearAllNotifications() {
        notifications.clear();
    }

    public void displayNotifications() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   NOTIFICATIONS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        int unreadCount = getUnreadNotifications().size();
        System.out.printf("Total: %d | Unread: %d%n%n", notifications.size(), unreadCount);

        int count = 1;
        for (Notification notification : notifications) {
            System.out.printf("%d. %s%n", count++, notification);
        }
    }
}
