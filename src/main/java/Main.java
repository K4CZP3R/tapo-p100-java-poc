import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.HandshakeResponse;
import domain.KspKeyPair;
import helpers.KspDebug;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.*;


public class Main {

    public static void main(String[] args) throws Exception {//throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException {


        Security.addProvider(new BouncyCastleProvider());
        KspDebug.out("Generating keypair...");
        TapoFlow tapoFlow = new TapoFlow("192.168.137.13");

        KspKeyPair kspKeyPair = KspEncryption.generateKeyPair();



        KspDebug.out("Sending handshake!");
        HandshakeResponse handshakeResponse = tapoFlow.makeHandshake(kspKeyPair);
        if (handshakeResponse == null) {
            System.exit(1);
        }


        String keyFromTapo = handshakeResponse.getResponse().getAsJsonObject("result").get("key").getAsString();
        KspDebug.out("Tapo's key is: " + keyFromTapo);
        KspDebug.out("Our session cookie is: " + handshakeResponse.getCookie());

        KspDebug.out("Will try to decode it!");
        C658a c658a = KspEncryption.decodeTapoKey(keyFromTapo, kspKeyPair);
        if (c658a == null) {
            System.exit(1);
        }
        KspDebug.out("Decoded!");

        KspDebug.out("Will try to login!");
        JsonObject resp = tapoFlow.loginRequest("TPLINK_EMAIL_HERE", "TPLINK_PASSWORD_HERE", c658a, handshakeResponse.getCookie());
        String token = resp.getAsJsonObject("result").get("token").getAsString();
        KspDebug.out("Got token: "+token);

        tapoFlow.changePlugState(c658a, token, handshakeResponse.getCookie());


    }
}
