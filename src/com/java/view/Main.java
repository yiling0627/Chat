package com.java.view;

import com.java.chat.startServer;

public class Main {
    public static void main(String[] args) {
        Login log = new Login();
        log.login();
        startServer s = new startServer();
        s.startServer();
    }
}
