package cn.platalk.lbs.api;

import cn.platalk.lbs.api.event.SocketDataEvent;
import cn.platalk.lbs.api.event.SocketEventBase;
import cn.platalk.lbs.api.event.SocketEventType;
import cn.platalk.lbs.api.event.SocketStatusEvent;
import cn.platalk.map.entity.base.impl.TYLocalPoint;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket/location/up/{userID}/{buildingID}")
public class LocationUpLinkSocket {
    private Session session;
    private String userID;
    private String buildingID;
    private String key;

    @OnOpen
    public void onOpen(Session session, @PathParam("userID") String userID, @PathParam("buildingID") String buildingID) {
        this.session = session;
        this.userID = userID;
        this.buildingID = buildingID;
        this.key = String.format("%s-%s", buildingID, userID);

        LocationUpSocketCaches.AddLink(this.key, this);

        if (LocationDownSocketCaches.ExistLink(this.key)) {
            LocationDownLinkSocket downLink = LocationDownSocketCaches.GetLink(this.key);
            downLink.sendEventMessage(SocketStatusEvent.UpLinkDisconnectEvent());

            sendEventMessage(SocketStatusEvent.DownLinkConnectEvent());
        }
    }

    @OnClose
    public void onClose() {
        if (LocationDownSocketCaches.ExistLink(this.key)) {
            LocationDownLinkSocket downLink = LocationDownSocketCaches.GetLink(this.key);
            downLink.sendEventMessage(SocketStatusEvent.UpLinkDisconnectEvent());
        }
        LocationUpSocketCaches.RemoveLink(this.key);
    }

    @OnError
    public void onError(Session session, Throwable error) {
//        System.out.println("onError");
//        System.out.println(error.toString());
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        JSONObject json = new JSONObject(msg);
        try {
            int eventType = json.getInt("type");
            if (eventType == SocketEventType.Data.getType()) {
                Long timestamp = null;
                TYLocalPoint location = null;
                Boolean bleStatus = null;

                Integer serial = json.optInt("serial");
                JSONObject dataObject = json.optJSONObject("data");
                if (dataObject != null) {
                    timestamp = dataObject.optLong("timestamp");
                    double x = dataObject.optDouble("x");
                    double y = dataObject.optDouble("y");
                    int floor = dataObject.optInt("floor");

                    if (!Double.isNaN(x) && !Double.isNaN(y)) {
                        location = new TYLocalPoint(x, y, floor);
                    }
                    bleStatus = dataObject.optBoolean("bleStatus");
                }

//                Reply Success Receive Message
//                sendEventMessage(SocketStatusEvent.ResponseSuccessEvent(serial));

                if (LocationDownSocketCaches.ExistLink(this.key)) {
                    LocationDownLinkSocket downLink = LocationDownSocketCaches.GetLink(this.key);
                    SocketDataEvent data = new SocketDataEvent("Location Data: " + serial);
                    data.setData(location, timestamp, bleStatus);
                    data.setSerial(serial);
                    downLink.sendEventMessage(data);
                }
            }
        } catch (JSONException e) {
//            Respond Error Message to Uploader
//            sendEventMessage(SocketStatusEvent.ResponseErrorEvent(msg));
//            e.printStackTrace();
        }

    }

    public void sendEventMessage(SocketEventBase event) {
        try {
            this.session.getBasicRemote().sendText(event.toJson().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
