package com.github.k4czp3r.tapop100.domain;

public class KspKeyPair {


    private final String privateKey;
    private final String publicKey;
    public KspKeyPair(String privateKey, String publicKey)
    {
        this.privateKey = privateKey;
        this.publicKey = publicKey;

    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
