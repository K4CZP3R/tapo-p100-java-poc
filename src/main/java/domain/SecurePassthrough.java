package domain;

public class SecurePassthrough {
    private final String method = "securePassthrough";
    private final ParamsBean params;

    public SecurePassthrough(ParamsBean params) {
        this.params = params;
    }

    public Object getParams() {
        return params;
    }

    public String getMethod() {
        return method;
    }
}
