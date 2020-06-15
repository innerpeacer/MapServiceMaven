package cn.platalk.lbs.api.event;

import org.json.JSONObject;

public class SocketCustomEvent extends SocketEventBase {

    private String data;
    
    public SocketCustomEvent(String description) {
        super(SocketEventType.CustomEvent, description);
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("data", data);
        return json;
    }
}
