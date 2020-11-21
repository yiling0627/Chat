package com.java.chat;

import com.data.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class startClient {

    private TextArea receieveInput;
    private DataInputStream dis;
    private DataOutputStream dos;


    public void startClient(User user){

        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Font font = new Font("Cooper Black", Font.BOLD, 20);

        //set frame position
        JFrame jFrame = new JFrame(user.getUsername()+" Chat");
        jFrame.setBounds(ss.width / 3, ss.height / 4,600, 630);

        //set frame Icon
        ImageIcon imageIcon = new ImageIcon("src/com/java/image/Icon.png");
        jFrame.setIconImage(imageIcon.getImage());

        //north
        //header image
        ImageIcon header = new ImageIcon("src/com/java/image/UONA.png");
        header.setImage(header.getImage().getScaledInstance(600, 140,Image.SCALE_DEFAULT ));
        JLabel jl1 = new JLabel(header);

        //Center
        JPanel jp_cb = new JPanel();
        receieveInput = new TextArea("",25,50);
        jp_cb.add(receieveInput);

        //south
        JPanel jp_sb = new JPanel();
        TextField userInput = new TextField("", 45);
        Button btn_sb = new Button("Send");
        btn_sb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dos.writeUTF(user.getUsername() + ": " + userInput.getText());
                    dos.flush();
//                    receieveInput.append('\n'+userInput.getText());
                    userInput.setText("");
                }
                catch(IOException ioe) {
                    receieveInput.append('\n'+ioe.toString());
                }
            }
        });
        jp_sb.add(userInput);
        jp_sb.add(btn_sb);

        //JFrame
        jFrame.add(jl1, BorderLayout.NORTH);
        jFrame.add(jp_cb, BorderLayout.CENTER);
        jFrame.add(jp_sb, BorderLayout.SOUTH);

        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        try {
            //Connect to chat port 9999
            Socket socket = new Socket("localhost", 9999);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            listenServerReply(dis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Listen for replies from the server
    public void listenServerReply(final DataInputStream dis){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String line = null;
                try {
                    while((line = dis.readUTF()) != null){
                        receieveInput.append('\n' + line);
                        System.out.println("client receive msg from server: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
