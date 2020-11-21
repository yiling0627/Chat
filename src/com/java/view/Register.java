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

public class Register {

    public void register() {
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        Font font = new Font("Cooper Black", Font.BOLD, 20);

        //set frame position
        JFrame jFrame = new JFrame("Register");
        jFrame.setBounds(ss.width / 3, ss.height / 4,600, 350);

        //set frame Icon
        ImageIcon imageIcon = new ImageIcon("src/com/java/image/Icon.png");
        jFrame.setIconImage(imageIcon.getImage());

        //north
        //header image
        ImageIcon header = new ImageIcon("src/com/java/image/UONA.png");
        header.setImage(header.getImage().getScaledInstance(600, 140,Image.SCALE_DEFAULT ));
        JLabel jl1 = new JLabel(header);

        //Center
        JPanel jp_reg = new JPanel();
        jp_reg.setLayout(new GridLayout(3, 2));

        //register part
        JLabel jl1_reg = new JLabel("Username", JLabel.CENTER);
        JLabel jl2_reg = new JLabel("Password", JLabel.CENTER);
        JLabel jl3_reg = new JLabel("Confirm", JLabel.CENTER);

        JTextField jt1_reg = new JTextField();
        JTextField jt2_reg = new JTextField();
        JTextField jt3_reg = new JTextField();

        jp_reg.add(jl1_reg);
        jp_reg.add(jt1_reg);
        jp_reg.add(jl2_reg);
        jp_reg.add(jt2_reg);
        jp_reg.add(jl3_reg);
        jp_reg.add(jt3_reg);

        //south
        JPanel jp_sb = new JPanel();
        jp_sb.setLayout(new GridLayout(1, 2));

        JButton jb1 = new JButton("Submit");
        JButton jb2 = new JButton("Return");

        //submit button
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name, pass, pass_confirm;
                name = jt1_reg.getText();
                pass = jt2_reg.getText();
                pass_confirm = jt3_reg.getText();
                if(StringUtil.isEmpty(name)) {
                    JOptionPane.showMessageDialog(null, "Username can't be null");
                    return;
                } else if(StringUtil.isEmpty(pass)){
                    JOptionPane.showMessageDialog(null, "Password can't be null");
                    return;
                }else if(StringUtil.isNotMatch(pass,pass_confirm)) {
                    JOptionPane.showMessageDialog(null, "Passwords typed don't match");
                    return;
                }else {
                    User user = new User();
                    user.setUsername(name);
                    user.setPassword(pass);
                    UserDao ud = new UserDao();
                    DbUtil du = new DbUtil();
                    try {
                        Connection con = du.getCon();
                        if(ud.insert(con, user)!=0) {
                            JOptionPane.showMessageDialog(null, "Register Success!");
                            startClient sc = new startClient();
                            sc.startClient(user);
                        } else {
                            JOptionPane.showMessageDialog(null, "Already exist！");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        System.out.println("Database connection failure");
                    }
                }
            }
        });
        //return button
        jb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login lg = new Login();
                jFrame.dispose();
                lg.login();
            }
        });

        jp_sb.add(jb1);
        jp_sb.add(jb2);

        //JFrame
        jFrame.add(jl1, BorderLayout.NORTH);
        jFrame.add(jp_reg, BorderLayout.CENTER);
        jFrame.add(jp_sb, BorderLayout.SOUTH);

        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
    }
}
