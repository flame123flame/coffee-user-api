package framework.model;

import lombok.Data;

@Data
public class WebSocketNotificationRes {
    private String type;
    private String message;
    private String username;
}
