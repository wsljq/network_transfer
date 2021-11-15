package com.antfact.twitter.bean;

import java.util.Arrays;


public class PostData {
    private byte[] source;
    private  String data;

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PostData{" +
            "source=" + Arrays.toString(source) +
            ", data='" + data + '\'' +
            '}';
    }
    public PostData(){

    }
    public PostData(byte[] source, String data) {
        this.source = source;
        this.data = data;
    }
}
