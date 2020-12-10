package com.github.k4czp3r.tapop100.domain;

public class HandshakeParams {
    private String key;

    public HandshakeParams(){}

    public void setKey(String key) {
        this.key = String.format("-----BEGIN PUBLIC KEY-----\n%s\n-----END PUBLIC KEY-----\n", key);
    }

    public String getKey() {
        return key;
    }
}
