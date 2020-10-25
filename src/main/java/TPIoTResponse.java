public class TPIoTResponse {
    private int error_code;
    private String error_msg;
    private long responseTimeMils;
    private String result;

    public int getErrorCode() {
        return this.error_code;
    }

    public void setErrorCode(int i) {
        this.error_code = i;
    }

    public String getErrorMsg() {
        return this.error_msg;
    }

    public void setErrorMsg(String str) {
        this.error_msg = str;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public long getResponseTimeMils() {
        return this.responseTimeMils;
    }

    public void setResponseTimeMils(long j) {
        this.responseTimeMils = j;
    }
}
