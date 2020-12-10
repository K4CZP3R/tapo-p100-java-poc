package com.github.k4czp3r.tapop100;

import com.google.gson.JsonObject;
import com.github.k4czp3r.tapop100.domain.HandshakeResponse;
import com.github.k4czp3r.tapop100.domain.KspKeyPair;
import com.github.k4czp3r.tapop100.helpers.KspDebug;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.*;


public class Main {

    public static void main(String[] args) throws Exception {//throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException {
        Properties prop = new Properties();
        File f = new File("tapop100.properties");
        if(!f.exists()) {
            f.createNewFile();
        }

        try(FileInputStream s = new FileInputStream("tapop100.properties")) {
            prop.load(s);
        }

        BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
        Optional<String> maybeIP = Optional.ofNullable(prop.getProperty("ip")).filter(s -> !s.equals("null"));
        Optional<String> maybeEmail = Optional.ofNullable(prop.getProperty("email")).filter(s -> !s.equals("null"));
        Optional<String> maybePassword = Optional.ofNullable(prop.getProperty("password")).filter(s -> !s.equals("null"));

        if(maybeIP.isEmpty()) {
            System.out.println("No IP of PowerPlug specified. Please adjust tapop100.properties");
        }
        if(maybeEmail.isEmpty()) {
            System.out.printf("No TP-Email-Address specified. Please adjust tapop100.properties");
        }
        if(maybePassword.isEmpty()) {
            System.out.println("No Password for the Email-Address specified. Please adjust tapop100.properties");
        }

        prop.put("ip", maybeIP.orElse("null"));
        prop.put("email", maybeEmail.orElse("null"));
        prop.put("password", maybePassword.orElse("null"));

        try(FileOutputStream s = new FileOutputStream("tapop100.properties")) {
            prop.store(s, "");
        }

        if(maybeIP.isEmpty() || maybeEmail.isEmpty() || maybePassword.isEmpty()) {
            System.out.println("script not configured, please adjust tapop100.properties and try again");
            System.exit(1);
        }

        final String ip = maybeIP.get();
        final String email = maybeEmail.get();
        final String password = maybePassword.get();

        if(args.length < 1) {
            System.out.println("Please provide parameters to this script, e.g. to turn on the switch: ");
            System.out.println("java -jar tapop100-1.0-SNAPSHOT-jar-with-dependencies.jar true");
            System.out.println("to turn it off: ");
            System.out.println("java -jar tapop100-1.0-SNAPSHOT-jar-with-dependencies.jar false");
            System.exit(1);
        }

        boolean enabled = Boolean.parseBoolean(args[0]);

        Security.addProvider(new BouncyCastleProvider());
        KspDebug.out("Generating keypair...");
        TapoFlow tapoFlow = new TapoFlow(ip);

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
        JsonObject resp = tapoFlow.loginRequest(email, password, c658a, handshakeResponse.getCookie());
        String token = resp.getAsJsonObject("result").get("token").getAsString();
        KspDebug.out("Got token: "+token);

        tapoFlow.setPlugState(c658a, token, handshakeResponse.getCookie(), enabled);


    }
}
