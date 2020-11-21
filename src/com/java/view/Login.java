package com.java.view;

import com.data.dao.UserDao;
import com.data.model.User;
import com.java.chat.startClient;
import util.DbUtil;
import util.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Login {

    public void login() {
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Font font = new Font("Cooper Black", Font.BOLD, 20);

        //set frame position
        JFrame jFrame = new JFrame("Happy Chat");
        jFrame.setBounds(ss.width / 3, ss.height / 4,600, 250);

        //set frame Icon
        ImageIcon imageIcon = new ImageIcon("src/com/java/image/Icon.png");
        jFrame.setIconImage(imageIcon.getImage());

        //define components
        //north
        ImageIcon header = new ImageIcon("src/com/java/image/UONA.png");
        header.setImage(header.getImage().getScaledInstance(600, 140,Image.SCALE_DEFAULT ));
        JLabel jl1 = new JLabel(header);

        //center
        JPanel jp_jtp1 = new JPanel();
        jp_jtp1.setLayout(new GridLayout(2, 3));

        JLabel south_jl1 = new JLabel("Username",JLabel.CENTER);
        JLabel south_jl2 = new JLabel("Password",JLabel.CENTER);

        JTextField jtf = new JTextField();
        JPasswordField jpf = new JPasswordField();


        JButton jb1 = new JButton("Login");
        JButton jb2 = new JButton("Register");

        //Define button function
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
                String name = jtf.getText();
                String pass = new String(jpf.getPassword());
                User user = new User();
                user.setUsername(name);
                user.setPassword(pass);

                if (StringUtil.isEmpty(name.toString())) {
                    JOptionPane.showMessageDialog(null, "name can't be null!");
                    return;
                }
                if (StringUtil.isEmpty(pass.toString())) {
                    JOptionPane.showMessageDialog(null, "Password can't be null!");
                    return;
                }
                UserDao ud = new UserDao();
                DbUtil du = new DbUtil();
                try {
                    Connection con = du.getCon();
                    if (ud.login(con, user)) {
                        startClient sc = new startClient();
                        sc.startClient(user);
                    } else {
                        jtf.setText(""); //clear text field when not match
                        jpf.setText("");
                        JOptionPane.showMessageDialog(null, "Username and password don't match！");
                    }
                    System.out.println("Database connection success");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Database connection failure");
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register reg = new Register();
                jFrame.dispose();
                reg.register();
            }
        });

        jp_jtp1.add(south_jl1);
        jp_jtp1.add(jtf);
        jp_jtp1.add(jb1);
        jp_jtp1.add(south_jl2);
        jp_jtp1.add(jpf);
        jp_jtp1.add(jb2);

        //JFrame
        jFrame.add(jl1, BorderLayout.NORTH);
        jFrame.add(jp_jtp1, BorderLayout.CENTER);

        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
    }
}



