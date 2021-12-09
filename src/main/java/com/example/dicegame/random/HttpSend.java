package com.example.dicegame.random;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSend {

    private String url;
    private HttpURLConnection connection;
    private String httpMethode;

    private int parameterCount = 0;

    public HttpSend(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getNumberOfParameters() {
        return parameterCount;
    }

    public String getHttpMethode() {
        return httpMethode;
    }

    public void setHttpMethode(String httpMethode) {
        this.httpMethode = httpMethode;
    }

    public void addParameter(String parameterName, String value) {
        if (parameterCount == 0) {
            url += "/?";
        } else {
            url += "&";
        }
        url += parameterName + "=" + value;
        parameterCount++;
    }

    public InputStream start() throws IOException {
        return connection.getInputStream();
    }

    public int getStatus() throws IOException {
        return connection.getResponseCode();
    }

    public void init() throws IOException{
        URL urlNet=new URL(url);
        connection=(HttpURLConnection) urlNet.openConnection();
        connection.setRequestMethod(httpMethode);
    }


}
