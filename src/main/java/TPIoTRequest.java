public class TPIoTRequest<T> {
    private String method;
    private T params;
    private transient long requestID;
    private long requestTimeMils;
    private String terminalUUID;

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public T getParams() {
        return this.params;
    }

    public void setParams(T t) {
        this.params = t;
    }

    public String getTerminalUUID() {
        return this.terminalUUID;
    }

    public void setTerminalUUID(String str) {
        this.terminalUUID = str;
    }

    public long getRequestID() {
        return this.requestID;
    }

    public void setRequestID(long j) {
        this.requestID = j;
    }

    public long getRequestTimeMils() {
        return this.requestTimeMils;
    }

    public void setRequestTimeMils(long j) {
        this.requestTimeMils = j;
    }
}
