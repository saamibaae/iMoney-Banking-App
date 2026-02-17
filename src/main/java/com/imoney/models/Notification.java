package com.imoney.models;

import com.imoney.enums.NotificationType;
import com.imoney.enums.Priority;
import java.util.Date;

public class Notification {

    private NotificationType notificationType;
    private String message;
    private Date timestamp;
    private boolean isRead;
    private Priority priority;

    public Notification(NotificationType type, String message, Priority priority) {
        this.notificationType = type;
        this.message = message;
        this.priority = priority;
        this.timestamp = new Date();
        this.isRead = false;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getPriorityIcon() {
        switch (priority) {
            case CRITICAL: return "🔴";
            case HIGH: return "🟠";
            case MEDIUM: return "🟡";
            case LOW: return "🟢";
            default: return "⚪";
        }
    }

    @Override
    public String toString() {
        String readStatus = isRead ? "[READ]" : "[UNREAD]";
        return String.format("%s %s %s | %s | %tF %<tT",
            getPriorityIcon(), priority, readStatus, message, timestamp);
    }
}
