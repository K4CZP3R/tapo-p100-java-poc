package domain;

public class Handshake {
    private final String method = "handshake";
    private final HandshakeParams params;

    public Handshake(HandshakeParams params) {
        this.params = params;
    }

    public Object getParams() {
        return params;
    }

    public String getMethod() {
        return method;
    }
}
