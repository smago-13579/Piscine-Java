package edu.school21.sockets.models;

import java.time.LocalDateTime;

public class Message {
    private String message;
    private LocalDateTime time;

    public Message(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public Message(String message) {
        this.message = message;
    }

    public Message() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
