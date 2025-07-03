package com.okjunkstore.beta.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class FcmHttpV1Request {
    @SerializedName("message")
    public Message message;

    public FcmHttpV1Request(Message message) {
        this.message = message;
    }

    public static class Message {
        public String topic;
        public Notification notification;
        public Map<String, String> data;
    }

    public static class Notification {
        public String title;
        public String body;
    }
}

