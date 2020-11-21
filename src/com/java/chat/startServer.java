package com.java.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class startServer {

    static Vector<Socket> v = new Vector<>();

    public void startServer(){
        try {
            //The server listens for client connections on port 9990
            ServerSocket ss = new ServerSocket(9999);
            System.out.println("server is listening...");
            while(true){
                //The blocked Accept method returns the Socket object only when a client is connected
                Socket s = ss.accept();
                v.add(s); //add all clients' socket
                System.out.println("a client has connected!");
                //Start the thread to handle the communication
                new CommunicateThread(s).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CommunicateThread extends Thread{

        Socket socket;
        DataInputStream dis;
        DataOutputStream dos;

        public CommunicateThread(Socket socket){
            this.socket = socket;
            try {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            super.run();
            //Read messages sent from the client
            String msg = null;
            try {
                while((msg = dis.readUTF()) != null){
                    for (Socket socket : v) {
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
