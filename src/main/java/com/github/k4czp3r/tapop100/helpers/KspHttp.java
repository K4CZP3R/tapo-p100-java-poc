package com.github.k4czp3r.tapop100.helpers;


import com.squareup.okhttp.*;
import org.sonatype.inject.Nullable;

import java.io.IOException;

public class KspHttp {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient okHttpClient;

    public KspHttp() {
        this.okHttpClient = new OkHttpClient();
    }

    public Response makePost(String url, String json, @Nullable String cookie) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Cookie", cookie != null ? cookie : "")
                .url(url)
                .post(body)
                .build();
        boolean executed = false;
        Response response = null;
        while (!executed) {
            try {
                response = okHttpClient.newCall(request).execute();
                executed = true;
            } catch (IOException ex) {
                KspDebug.out("Request failed, retry...");
            }
        }
        return response;

    }
}
