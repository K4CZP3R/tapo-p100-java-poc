package com.github.k4czp3r.tapop100;

import com.github.k4czp3r.tapop100.domain.KspKeyPair;
import com.github.k4czp3r.tapop100.helpers.KspB64;
import com.github.k4czp3r.tapop100.helpers.KspDebug;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class KspEncryption {
    public static KspKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
        instance.initialize(1024, new SecureRandom());
        KeyPair generateKeyPair = instance.generateKeyPair();

        String publicKey = new String(java.util.Base64.getMimeEncoder().encode(((RSAPublicKey) generateKeyPair.getPublic()).getEncoded()));
        String privateKey = new String(java.util.Base64.getMimeEncoder().encode(((RSAPrivateKey) generateKeyPair.getPrivate()).getEncoded()));

        return new KspKeyPair(privateKey, publicKey);
    }

    public static C658a decodeTapoKey(String key, KspKeyPair keyPair) {
        KspDebug.out("Will try to decode the following key: " + key);



        try {
            byte[] decode = KspB64.decode(key.getBytes("UTF-8"));
            byte[] decode2 = KspB64.decode(keyPair.getPrivateKey());
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey p = kf.generatePrivate(new PKCS8EncodedKeySpec(decode2));
            instance.init(Cipher.DECRYPT_MODE, p);
            byte[] doFinal = instance.doFinal(decode);
            byte[] bArr = new byte[16];
            byte[] bArr2 = new byte[16];
            System.arraycopy(doFinal, 0, bArr, 0, 16);
            System.arraycopy(doFinal, 16, bArr2, 0, 16);
            return new C658a(bArr, bArr2);

        }
        catch (Exception ex)
        {
            KspDebug.out("Something went wrong: " + ex.getMessage());
            return null;
        }

    }

    public static String shaDigestUsername(String str) throws NoSuchAlgorithmException {
        byte[] bArr = str.getBytes();
        byte[] digest = MessageDigest.getInstance("SHA1").digest(bArr);

        StringBuilder sb = new StringBuilder();
        for(byte b : digest){
            String hexString = Integer.toHexString(b & 255);
            if(hexString.length() == 1){
                sb.append("0");
                sb.append(hexString);
            } else {
                sb.append(hexString);
            }
        }
        String a = sb.toString();
        return sb.toString();
    }

}
