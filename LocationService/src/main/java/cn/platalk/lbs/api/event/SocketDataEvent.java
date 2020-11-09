package cn.platalk.lbs.api.event;

import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import org.json.JSONObject;

public class SocketDataEvent extends SocketEventBase {

    TYLocalPoint location;
    Long timestamp;
    Boolean bleStatus;
    Integer serial;

    public SocketDataEvent(String description) {
        super(SocketEventType.Data, description);
    }

    public void setData(TYLocalPoint location, Long timestamp, Boolean bleStatus) {
        this.location = location;
        this.timestamp = timestamp;
        this.bleStatus = bleStatus;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();

        json.put("serial", serial);
        JSONObject dataObject = new JSONObject();
        dataObject.put("timestamp", timestamp);
        if (location != null) {
            dataObject.put("x", location.getX());
            dataObject.put("y", location.getY());
            dataObject.put("floor", location.getFloor());
        }
        dataObject.put("status", location != null);
        dataObject.put("bleStatus", bleStatus);
        json.put("data", dataObject);
        
        return json;
    }
}
