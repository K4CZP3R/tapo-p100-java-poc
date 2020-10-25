import com.google.gson.JsonObject;
import com.squareup.okhttp.Response;
import domain.*;
import helpers.KspB64;
import helpers.KspDebug;
import helpers.KspHttp;
import helpers.KspJson;

import java.io.IOException;

public class TapoFlow {

    private final KspHttp kspHttp;

    private final String address;

    public TapoFlow(String address) {
        this.kspHttp = new KspHttp();
        this.address = address;

    }


    public HandshakeResponse makeHandshake(KspKeyPair kspKeyPair) {
        HandshakeParams handshakeParams = new HandshakeParams();
        handshakeParams.setKey(kspKeyPair.getPublicKey());

        Handshake handshake = new Handshake(handshakeParams);


        try {
            KspDebug.out("Making handshake...");
            Response response = this.kspHttp.makePost(String.format("http://%s/app", address), KspJson.convertToString(handshake), null);
            String responseBody = response.body().string();
            String cookie = response.header("Set-Cookie").split(";")[0];
            KspDebug.out("Server responded with: " + responseBody);
            return new HandshakeResponse(
                    cookie,
                    KspJson.convertToObj(responseBody)
            );
        } catch (IOException ex) {
            KspDebug.out("Something went wrong: " + ex.getMessage());
            return null;
        }
    }

    public void changePlugState(C658a c658a, String token, String cookie){
        try{
            DeviceInfoParams deviceInfoParams = new DeviceInfoParams();
            deviceInfoParams.setDeviceOn(true);

            TPIoTRequest<DeviceInfoParams> tpIoTRequest = new TPIoTRequest<>();
            tpIoTRequest.setMethod("set_device_info");
            tpIoTRequest.setParams(deviceInfoParams);
            tpIoTRequest.setRequestTimeMils(System.currentTimeMillis());
            tpIoTRequest.setTerminalUUID("88-54-DE-AD-52-E1");

            KspDebug.out(KspJson.convertToString(tpIoTRequest));

            String encrypted = c658a.mo38009b_enc(KspJson.convertToString(tpIoTRequest));
            ParamsBean paramsBean = new ParamsBean(encrypted);
            SecurePassthrough securePassthrough = new SecurePassthrough(paramsBean);
            String requestBody = KspJson.convertToString(securePassthrough);

            Response response = this.kspHttp.makePost(String.format("http://%s/app?token=%s", address, token), requestBody, cookie);
            String respBody = response.body().string();

            JsonObject jsonObject = KspJson.convertToObj(respBody);
            String encryptedResponse = jsonObject.getAsJsonObject("result").get("response").getAsString();
            String dec = c658a.mo38006a_dec(encryptedResponse);

            KspDebug.out("Server response: " + respBody);
            KspDebug.out("Decrypted: " + dec);


        }
        catch (Exception ex)
        {
            KspDebug.out("Something went wrong! " + ex.getMessage());

        }
    }

    public void changeStatusLed(C658a c658a, String token, String cookie){
        try{
            PlugDeviceInfoParams plugDeviceInfoParams = new PlugDeviceInfoParams();
            plugDeviceInfoParams.setLedEnable(false);

            TPIoTRequest<PlugDeviceInfoParams> tpIoTRequest = new TPIoTRequest<>();
            tpIoTRequest.setMethod("set_led_status");
            tpIoTRequest.setParams(plugDeviceInfoParams);

            KspDebug.out(KspJson.convertToString(tpIoTRequest));

            String encrypted = c658a.mo38009b_enc(KspJson.convertToString(tpIoTRequest));
            ParamsBean paramsBean = new ParamsBean(encrypted);
            SecurePassthrough securePassthrough = new SecurePassthrough(paramsBean);
            String requestBody = KspJson.convertToString(securePassthrough);

            Response response = this.kspHttp.makePost(String.format("http://%s/app?token=%s", address, token), requestBody, cookie);
            String respBody = response.body().string();

            JsonObject jsonObject = KspJson.convertToObj(respBody);
            String encryptedResponse = jsonObject.getAsJsonObject("result").get("response").getAsString();
            String dec = c658a.mo38006a_dec(encryptedResponse);
            KspDebug.out(dec);
            KspDebug.out("Server response: " + response.body().string());


        }
        catch (Exception ex)
        {
            KspDebug.out("Something went wrong! " + ex.getMessage());

        }
    }

    public JsonObject loginRequest(String username, String password, C658a c658a, String cookie) {
        try {
            LoginDeviceRequest loginDeviceRequest = new LoginDeviceRequest();
            loginDeviceRequest.setUsername(KspB64.encodeToString(KspEncryption.shaDigestUsername(username).getBytes()));
            loginDeviceRequest.setPassword(KspB64.encodeToString(password.getBytes()));

            TPIoTRequest<LoginDeviceRequest> tpIoTRequest = new TPIoTRequest<>();
            tpIoTRequest.setMethod("login_device");
            tpIoTRequest.setParams(loginDeviceRequest);
            tpIoTRequest.setRequestTimeMils(0);

            String getMe = KspJson.convertToString(tpIoTRequest);
            KspDebug.out("Unencrypted request content: " + KspJson.convertToString(tpIoTRequest));
            KspDebug.out("Encrypting request content...");
            String encrypted = c658a.mo38009b_enc(KspJson.convertToString(tpIoTRequest));

            ParamsBean paramsBean = new ParamsBean(encrypted);

            SecurePassthrough securePassthrough = new SecurePassthrough(paramsBean);

            String requestBody = KspJson.convertToString(securePassthrough);
            KspDebug.out("request Body " + requestBody);

            KspDebug.out("Sending login request...");
            Response response = this.kspHttp.makePost(String.format("http://%s/app", address), requestBody, cookie);
            String resp = response.body().string();
            KspDebug.out("Server responsed with (encrypted): " + resp);

            JsonObject jsonResponse = KspJson.convertToObj(resp);
            String encryptedResponse = jsonResponse.getAsJsonObject("result").get("response").getAsString();
            String decryptedResponse = c658a.mo38006a_dec(encryptedResponse);
            KspDebug.out("Decrypted response: " + decryptedResponse);
            return KspJson.convertToObj(decryptedResponse);

        } catch (Exception ex) {
            KspDebug.out("Something went wrong: " + ex.getMessage());
            return null;
        }
    }

}
