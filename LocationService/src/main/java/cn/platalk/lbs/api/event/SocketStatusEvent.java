package cn.platalk.lbs.api.event;

public class SocketStatusEvent extends SocketEventBase {

    SocketStatusEvent(SocketEventType type, String description) {
        super(type, description);
    }

    public static SocketStatusEvent ResponseSuccessEvent(int serial) {
        return new SocketStatusEvent(SocketEventType.ResponseSuccess, "Success Response For " + serial);
    }

    public static SocketStatusEvent ResponseErrorEvent(String msg) {
        return new SocketStatusEvent(SocketEventType.ResponseError, msg);
    }

    public static SocketStatusEvent UpLinkConnectEvent() {
        return new SocketStatusEvent(SocketEventType.UpLinkConnected, "Up Link Connected!");
    }

    public static SocketStatusEvent UpLinkDisconnectEvent() {
        return new SocketStatusEvent(SocketEventType.UpLinkDisconnected, "Up Link Disconnected!");
    }

    public static SocketStatusEvent DownLinkConnectEvent() {
        return new SocketStatusEvent(SocketEventType.DownLinkConnected, "Down Link Connected!");
    }

    public static SocketStatusEvent DownLinkDisconnectEvent() {
        return new SocketStatusEvent(SocketEventType.DownLinkDisconnected, "Down Link Disconnected!");
    }
}
