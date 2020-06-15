package cn.platalk.lbs.api.event;

public enum SocketEventType {
    ResponseSuccess(1001),
    ResponseError(1002),
    UpLinkConnected(2001),
    UpLinkDisconnected(2002),
    DownLinkConnected(2003),
    DownLinkDisconnected(2004),
    Data(3001),
    CustomEvent(4001);

    final int type;

    SocketEventType(int t) {
        this.type = t;
    }

    public int getType() {
        return type;
    }
}
