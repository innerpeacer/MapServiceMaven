package cn.platalk.lbs.api.event;

import org.json.JSONObject;

public abstract class SocketEventBase {
    protected final SocketEventType eventType;
    protected final String description;

    public SocketEventBase(SocketEventType type, String description) {
        this.eventType = type;
        this.description = description;
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("type", eventType.getType());
        result.put("description", description);
        return result;
    }
}
