package domain;

public class ParamsBean {

    private String request;

    public ParamsBean(String str) {
        this.request = str;
    }

    public String getRequest() {
        return this.request;
    }

    public void setRequest(String str) {
        this.request = str;
    }

}
