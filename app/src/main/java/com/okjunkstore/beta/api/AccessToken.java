package com.okjunkstore.beta.api;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {
    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"beta-6165e\",\n" +
                    "  \"private_key_id\": \"8133e4af0285265f5524194c0e6bf34093238f5c\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQplfV1sDo1emP\\nd72AtIB7mcgQGTEdCOk/cQylOLCoDvTZrEbsl3L6e+FYlpgR+35kz598DeTAV4ym\\nyI3SSDFv4+YbvQDx3LR3MhQeoD/rs4h6YlanhW6QLo2FbUng/KPXPyFS9eoXDLvU\\no6BndximLeCLhvq0rrlIh4yPGWCx+BhBf9A0iCh6tftCXEQ5oosI8OwrzOWYXDyP\\nX9lsbGs6pyuGpm1yg3UBan3cHG3M6wmXzLBu2hp4g++OhoZ/k8wlpQj8dMVIoRZz\\nMla0PeUEjNSj/AkFkpHzzCyqiQSQB1WxgoeDX2UjBA2ShOEBsXf7HMytWU/xKd3G\\nDd9lNsTlAgMBAAECggEALi+UI96sxx2K3NoplZLfGf0Zfw7Eg2PUldmFbhFVcW4W\\nHxT2giqoJfBEGqMDOH/OI6yd5g0BRN5cYRLl6dVXNChjaGJ3EhLD09q+/2SDob4V\\nCNo5yk1MAxh69T0S7H2gGuZ7juymi8Gi1lf+CskfKLHhEaGoX1SJ6RcwMwDwGZSm\\nFDFi1+N8uzlioJlqLrZGuy20/pDNecDwoY5L1EziVsYWDN0B+jH0CHR2BbK01MtF\\nhOaM1/SMqFqdkHglGIeXnjhlm0JJkA91bNcBepwGrzbz+PN8HL7EzTGwnNSugxX1\\n0UWp5FQbXXxSnfXOB558bSa2BeDT9n+xDYYNB9cT5QKBgQDuOBvDaevJmwyBDO6t\\nfTVvf/PZl5ruHaIU3cqVUdm0LRcGY7zjjfLaommgq6ILuv6+lWiIo8y7XzPmynhw\\n72nO7UsebCRC16pjkgZQ6end6p+Hqn/lWGpxXdE0vmKVStfStMJDQGJ3jaI9gDaf\\n0akOSierLWIiBBtPvjtLjHmc0wKBgQDgOTkCVvvvFlRMssD9oaXsBbUWvRqMg2Jk\\nNoQ/c8OsWPWv4BDSqf8wyLK3WiWHVLa62c1+KiSi+DhoJP5DSpa5p4ZfCaed1yMt\\naaTBYY0XgieMihvbrIqM+OYYirxELmU603ePPhCJ9SxyjpDjefaWxGbXwwH/5dPs\\nQof5r/kkZwKBgQDdRukjH6SR5KA8z5J1PLUCGUyddiHHxWblExA2m40pvCiDGuo+\\nRYCsbejIAiXH168BwS8UnXJzKf/ABZrS7NDQEcORQsp7/HCdhOqxBlbieCovjsS2\\neOaG36qQ37gbDSZk5tr8NTZB7yIhFpOm6M2sJF2st79L8Zyc+guCJb5hWQKBgQC8\\nfb48Bk2Rpq7FLzvnu10VNZMPA3dvCf/LiLzR7opetwYKjIz322qoYv9B/WkxA/j8\\nyJ3j4p+b1Jjui7KndOuSNI9UE3SxzqpGwdl7q3sFeSewEGXs3HY4ngAoP3CWzH3S\\n3vRSGXWj09RClTCd2eh+w6pE1S04JsdphGB0eQczHwKBgAtWDjCvN4Ra3eUdow99\\njLWmRl8HiP1CNIBcekB4jo5lkqopaq/J3X2EJH+mypD19rsJ3ahHMrGqN+zp95Z7\\nE+fN7YGUFxv/rrx3E/snCQ0aS7tO2zT91iqfZrdQplXFy3FV9DcDHKNGtqtBFfwj\\n7TcYlcjV7WVVosWHZhuhivG2\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-j8l36@beta-6165e.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"113051406093671751593\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-j8l36%40beta-6165e.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}\n";
            InputStream stream = new ByteArrayInputStream(jsonString.getBytes (StandardCharsets.UTF_8));

            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream)
                    .createScoped (Lists.newArrayList(firebaseMessagingScope));

            googleCredentials.refresh();

            String token =  googleCredentials.getAccessToken().getTokenValue();

            Log.e("AccessToken", "Access Token : " + token);
            return token;

        } catch (IOException e) {
            Log.e("AccessToken", "Access Token Error : " + e.getMessage());
            return null;
        }

    }
}
