package com.github.k4czp3r.tapop100;

import com.github.k4czp3r.tapop100.helpers.KspB64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class C658a {

    Cipher f21776a_enc;
    Cipher f21777b_dec;

    public C658a(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
        f21776a_enc = Cipher.getInstance("AES/CBC/PKCS7Padding");
        f21776a_enc.init(1, secretKeySpec, ivParameterSpec);
        f21777b_dec = Cipher.getInstance("AES/CBC/PKCS7Padding");
        f21777b_dec.init(2, secretKeySpec, ivParameterSpec);


    }

    public String mo38009b_enc(String str) throws Exception {
        byte[] doFinal;
        doFinal = this.f21776a_enc.doFinal(str.getBytes());
        String encrypted = KspB64.encodeToString(doFinal);
        return encrypted.replace("\r\n","");
    }

    public String mo38006a_dec(String str) throws Exception{
        byte[] data = KspB64.decode(str.getBytes("UTF-8"));
        byte[] doFinal;
        doFinal = this.f21777b_dec.doFinal(data);
        return new String(doFinal);
    }
}
