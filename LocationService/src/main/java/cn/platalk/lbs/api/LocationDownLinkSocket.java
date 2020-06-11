package cn.platalk.lbs.api;

import cn.platalk.lbs.api.event.SocketEventBase;
import cn.platalk.lbs.api.event.SocketStatusEvent;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket/location/down/{userID}/{buildingID}")
public class LocationDownLinkSocket {
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

        LocationDownSocketCaches.AddLink(this.key, this);

        if (LocationUpSocketCaches.ExistLink(this.key)) {
            LocationUpLinkSocket upLink = LocationUpSocketCaches.GetLink(this.key);
            upLink.sendEventMessage(SocketStatusEvent.DownLinkConnectEvent());
        }
    }

    @OnClose
    public void onClose() {
        if (LocationUpSocketCaches.ExistLink(this.key)) {
            LocationUpLinkSocket upLink = LocationUpSocketCaches.GetLink(this.key);
            upLink.sendEventMessage(SocketStatusEvent.DownLinkDisconnectEvent());
        }
        LocationDownSocketCaches.RemoveLink(this.key);
    }

    @OnMessage
    public void onMessage(String msg, Session session) {

    }

    public void sendEventMessage(SocketEventBase event) {
        try {
            this.session.getBasicRemote().sendText(event.toJson().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}