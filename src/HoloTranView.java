import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;


import java.io.File;
import javax.swing.*;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.JProgressBar;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class HoloTranView extends JFrame {

    private static String fullPath;
    Connection con;
    Statement st;
    ResultSet rs;
    private String userName;
    private String userID;
    private boolean loginSuccess;
    private static boolean uploadStatus;
    public boolean convertStatus;
    static ChannelSftp channelSftp;
    static Session session;
    static Channel channel;
    private static String FILEUPLOADPATH;

    HoloTranView() {
        super("Convert Video to Hologram");
        fullPath = "";
        con = null;
        loginSuccess = false;
        userName = "";
        userID = "";
        FILEUPLOADPATH = "";
        uploadStatus = false;
        convertStatus = false;
        channelSftp = null;
        session = null;
        channel = null;
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButtonBack1 = new javax.swing.JButton();
        jButtonBack2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(500, 400));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HOLOTRAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(24, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Please Select Your Video");


        jButton1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton1.setText("...");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose(1);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("LEFT");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("RIGHT");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("FRONT");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("BACK");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose(2);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose(3);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose(4);
            }

        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(205, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(188, 188, 188))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel3))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField1)
                                        .addComponent(jTextField2)
                                        .addComponent(jTextField3)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3)
                                        .addComponent(jButton4))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1)
                                        .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton2)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(jButton3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(jButton4))
                                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel7.setBackground(new java.awt.Color(0, 102, 102));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Save as..");

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose(5);
            }

        });


        jButtonBack1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButtonBack1.setText("BACK");
        jButtonBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonBack1ActionPerformed(e);
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
            }

        });

//        jTextField5.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField5ActionPerformed(evt);
//            }
//        });

        jButton6.setBackground(new java.awt.Color(204, 204, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 102, 102));
        jButton6.setText("CONVERT");

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(jTextField1.getText().length() >= 1 && jTextField2.getText().length() >= 1 && jTextField3.getText().length() >= 1 && jTextField4.getText().length() >= 1 && jTextField5.getText().length() >= 1) {
                    JOptionPane.showMessageDialog(null, "Convert finish");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select your path before start", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(202, 202, 202)
                                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(74, 74, 74)
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton5)
                                                .addGap(50,50,50)
                                                .addComponent(jButtonBack1)
                                        ))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton5)
                                        .addComponent(jLabel7))

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()
                                .addComponent(jButtonBack1)
                        )
        );


//        Design for multiple view
//        p1 View
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton99 = new javax.swing.JButton();
        jButton88 = new javax.swing.JButton();

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("HOLOTRAN");jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(447, 239));

        jButton99.setBackground(new java.awt.Color(255, 255, 255));
        jButton99.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton99.setForeground(new java.awt.Color(0, 102, 102));
        jButton99.setText("Convert Video");
        jButton99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton99ActionPerformed(evt);
            }
        });

        jButton88.setBackground(new java.awt.Color(255, 255, 255));
        jButton88.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton88.setForeground(new java.awt.Color(0, 102, 102));
        jButton88.setText("Upload To HOLOTUBE");
        jButton88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton88ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(164, 164, 164)
                                                .addComponent(jButton99, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(148, 148, 148)
                                                .addComponent(jButton88)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jButton99, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jButton88, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(82, Short.MAX_VALUE))
        );

//        p3 View
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton7 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("HOLOTRAN");

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Please Log In To HOLOTUBE");

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 102, 102));
        jButton7.setText("LOG IN");


        jButtonBack2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButtonBack2.setText("BACK");
        jButtonBack2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonBack1ActionPerformed(e);
                try {
                    con.close();
                } catch (Exception ex) {

                }
            }

        });

        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    String user = jTextField6.getText();
                    String pass = String.valueOf(jPasswordField1.getPassword());

                    if(user.length() >= 1 && pass.length() >= 1) {
                        String sql = "select user_id,user_name from User where user_name = '" + user + "' and user_pass = '" + pass + "'";
                        rs = st.executeQuery(sql);

                        if(!rs.next()) {
                            JOptionPane.showMessageDialog(null, "Username or password are not correct");
                        } else {
                            JOptionPane.showMessageDialog(null, "Login Successful");
                            userName = rs.getString(2);
                            userID = rs.getString(1);
                            loginSuccess = true;
                        }

                        if(loginSuccess) {
                            jButton7ActionPerformed(evt);
                            con.close();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please input username or password");
                    }
                    jTextField6.setText("");
                    jPasswordField1.setText("");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        jLabel11.setText("Don't have account?");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
                try {
                    con.close();
                    connect();
                } catch (Exception ex) {

                }
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("Username");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 102));
        jLabel14.setText("Password");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel13)
                                                        .addComponent(jLabel14))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                // .addComponent(jLabel12)
                                                // .addGap(124, 124, 124)
                                                .addComponent(jButtonBack2)
                                                .addGap(10, 10, 10)
                                        )))



                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(156, 156, 156))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(198, 198, 198))))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel10)
                                .addGap(32, 32, 32)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        //  .addComponent(jLabel12)
                                        .addComponent(jButtonBack2)
                                ))
        );
//        p4 View
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        //jComboBox1 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButtonBack4 = new javax.swing.JButton();
//        JProgressBar progressBar = new JProgressBar(0, 100);

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("HOLOTRAN");

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 153, 153));
        jLabel16.setText("UPLOAD TO HOLOTUBE");

        //jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 153, 153));
        jButton8.setText("UPLOAD");

        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(uploadStatus) {
                    JOptionPane.showMessageDialog(null,
                            "Please wait until current upload finish", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    uploadStatus = true;
                    final String SETURL = "139.59.111.233";
                    final int SETPORT = 22;
                    final String SFTUSER = "root"; // User Name
                    final String SFTPASS = "212224236"; // Password
                    final String SFTWORKINGDIR = "/var/www/html/video";
                    String LOCALDIRECTORY;

                    if(jTextField7.getText().length() < 1) {
                        JOptionPane.showMessageDialog(null,"Please choose a file to upload!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        uploadStatus = false;
                        jTextField7.setText("");
                        LOCALDIRECTORY = jTextField7.getText();
                    } else {
                        if(jTextField8.getText().length() > 1 && jTextField9.getText().length() > 1){
                            try {
                                LOCALDIRECTORY = jTextField7.getText();
                                JSch jsch = new JSch();
                                session = jsch.getSession(SFTUSER, SETURL, SETPORT);
                                session.setPassword(SFTPASS);
                                java.util.Properties config = new java.util.Properties();
                                config.put("StrictHostKeyChecking", "no");
                                session.setConfig(config);
                                session.connect(); // Create SFTP Session
                                channel = session.openChannel("sftp"); // Open SFTP Channel
                                channel.connect();
                                channelSftp = (ChannelSftp) channel;
                                channelSftp.cd(SFTWORKINGDIR); // Change Directory on SFTP Server

                                recursiveFolderUpload(LOCALDIRECTORY, SFTWORKINGDIR, userID);

                                if(uploadStatus) {
                                    String videoTitle = jTextField8.getText();
                                    String videoDescrip = jTextField8.getText();
                                    int userid = Integer.parseInt(userID);
                                    int fav = 0;
//                                    String input = "'" + userid + "', " + "'" + videoTitle + "', " + "'" + SFTWORKINGDIR + "/" + FILEUPLOADPATH + "', " + "'" + videoDescrip + "', " + "'" + fav + "'";
                                    String input = "'" + userid + "', " + "'" + videoTitle + "', " + "'" + "video/" + FILEUPLOADPATH + "', " + "'" + videoDescrip + "', " + "'" + fav + "'";
                                    connect();
                                    String sql = "INSERT INTO Video_info(id_user, video_title, video_url, video_subtitle, favorite_count) VALUES (" + input + ")";
                                    st.executeUpdate(sql);
                                    JOptionPane.showMessageDialog(null, "Your video upload completed");
                                    jButtonBack3ActionPerformed(e);
                                    try {
                                        con.close();
                                        connect();
                                    } catch (Exception ex) {

                                    }
                                }

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Error executing upload task: " + ex.getMessage(), "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                uploadStatus = false;
                                jTextField7.setText("");
                                channel = null;
                                session = null;
                                channelSftp = null;
                                LOCALDIRECTORY = jTextField7.getText();
                            } finally {
                                if (channelSftp != null)
                                    channelSftp.disconnect();
                                if (channel != null)
                                    channel.disconnect();
                                if (session != null)
                                    session.disconnect();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Please fill your video name and your video description", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            jTextField7.setText("");
                            jTextField8.setText("");
                            jTextField9.setText("");
                            LOCALDIRECTORY = jTextField7.getText();
                            uploadStatus = false;
                        }
                        uploadStatus = false;
                        channel = null;
                        session = null;
                        channelSftp = null;
                        jTextField7.setText("");
                        jTextField8.setText("");
                        jTextField9.setText("");
                        FILEUPLOADPATH = "";
                        LOCALDIRECTORY = jTextField7.getText();
                    }
                }
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 102));
        jLabel17.setText("Video Location");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 102));
        jLabel18.setText("Description");

        //jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        //jLabel19.setForeground(new java.awt.Color(0, 102, 102));
        //jLabel19.setText("Type");

//        progressBar.setPreferredSize(new java.awt.Dimension(200, 30));
//        progressBar.setStringPainted(true);

        jButton9.setText("Browse");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose(6);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 102));
        jLabel20.setText("Video Name");

        jButtonBack4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButtonBack4.setText("Log out");
        jButtonBack4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(uploadStatus) {
                    JOptionPane.showMessageDialog(null, "Please wait until upload finish", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    jTextField8.setText("");
                    jTextField9.setText("");
                    jTextField7.setText("");
                    jButtonBack3ActionPerformed(e);
                    try {
                        con.close();
                        connect();
                    } catch (Exception ex) {

                    }
                }

            }

        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(208, 208, 208))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(33, 33, 33)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(164, 164, 164))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                                        .addComponent(jLabel16)
                                                                        .addGap(187, 187, 187)))
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jButton9))
                                                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                                        .addComponent(progressBar)
                                                                        /*.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)*/)
                                                                .addComponent(jButtonBack4)
                                                                .addGap(55, 55, 55)
                                                        )))))
        );


        //p6


        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel16)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton9)
                                        .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                        .addComponent(progressBar)
                                        //.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19))
                                .addGap(13, 13, 13)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                                .addComponent(jButtonBack4)
                                .addContainerGap())

        );

        //p7
        jPanel7 = new javax.swing.JPanel();
        jLabel299 = new javax.swing.JLabel();
        jTextField199 = new javax.swing.JTextField();
        jTextField299 = new javax.swing.JTextField();
        jTextField399 = new javax.swing.JTextField();
        jButton1999 = new javax.swing.JButton();
        jButtonBack3 = new javax.swing.JButton();
        jLabel499 = new javax.swing.JLabel();
        jLabel599 = new javax.swing.JLabel();
        jLabel3999 = new javax.swing.JLabel();


        jPanel7.setBackground(new java.awt.Color(204, 204, 255));

        jLabel299.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel299.setForeground(new java.awt.Color(0, 153, 153));
        jLabel299.setText("Please Register");

        jButton1999.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1999.setForeground(new java.awt.Color(0, 153, 153));
        jButton1999.setText("Register");

        jButton1999.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String user = jTextField199.getText();
                    String pass1 = jTextField299.getText();
                    String pass2 = jTextField399.getText();
//                    String pass = String.valueOf(jPasswordField1.getPassword());
                    if(user.length() >= 1 && pass1.length() >= 1 && pass2.length() >= 1) {
                        if(pass1.equals(pass2)) {
                            String sql = "select user_name from User where user_name = '" + user + "'";
                            rs = st.executeQuery(sql);

                            if(!rs.next()) {
                                String input = "'" + user + "', " + "'" + pass1 + "'";
                                sql = "INSERT INTO User(user_name, user_pass) VALUES (" + input + ")";
                                st.executeUpdate(sql);
                                JOptionPane.showMessageDialog(null, "Register completed");
                                jButtonBack3ActionPerformed(e);
                                con.close();
                                try {
                                    connect();
                                } catch (Exception ex) {

                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "This username already exit");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Your password are not same");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill you username, password and confirm password");
                    }
                    jTextField199.setText("");
                    jTextField299.setText("");
                    jTextField399.setText("");
//                    jPasswordField1.setText("");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        jLabel499.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel499.setForeground(new java.awt.Color(0, 102, 102));
        jLabel499.setText("Password");

        jLabel599.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel599.setForeground(new java.awt.Color(0, 102, 102));
        jLabel599.setText("Confirm Password");

        jLabel3999.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3999.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3999.setText("Username");

        jButtonBack3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButtonBack3.setText("BACK");
        jButtonBack3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonBack3ActionPerformed(e);
                try {
                    con.close();
                    connect();
                } catch (Exception ex) {

                }
            }

        });



        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                                .addGap(87, 87, 87)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addComponent(jLabel3999)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                                                .addComponent(jTextField199, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addComponent(jLabel499)
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addComponent(jLabel599)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jTextField399, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jTextField299, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(136, 136, 136))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                                .addComponent(jButton1999, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(213, 213, 213))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel299, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(193, 193, 193))

                                )  .addComponent(jButtonBack3)
                                .addGap(20, 20, 20)


                        )
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel299, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextField199, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3999))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextField299, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel499))
                                        .addGap(18, 18, 18)

                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jTextField399, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel599))
                                        .addGap(32, 32, 32)
                                        .addComponent(jButton1999, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                        .addGap(22, 22, 22)
                                        .addComponent(jButtonBack3)
                                //.addGap(5, 50, 50)
                        )


        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);
        jPanel6.setVisible(false);
        jPanel7.setVisible(false);

//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                )
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    public void connect() {
        con = null;
        try {

            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
//            String user1 = "admin";
//            String pass1 = "b69d150d7677e04fb697130b9d06c3c1f742c9f5a11ad1b8";
            String user1 = "holotran";
            String pass1 = "holotran";
            String url1 = "jdbc:mysql://139.59.111.233:3306/test1";

            con = DriverManager.getConnection(url1, user1, pass1);
            st = con.createStatement();

            if(con != null) {
                System.out.println("connected");
            } else {
                System.out.println("connection fail");
            }

        } catch(Exception ex){
            System.out.println("connection error");
            ex.printStackTrace();
        }
    }

    private static void recursiveFolderUpload(String sourcePath, String destinationPath, String inputUserID) throws SftpException, FileNotFoundException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File sourceFile = new File(sourcePath);
        if (sourceFile.isFile()) {

            // copy if it is a file
            channelSftp.cd(destinationPath);
            if (!sourceFile.getName().startsWith("."))
                FILEUPLOADPATH = timestamp + inputUserID;
                FILEUPLOADPATH = FILEUPLOADPATH.replaceAll("[^a-zA-Z0-9]", "");
                FILEUPLOADPATH = FILEUPLOADPATH + sourceFile.getName();
                channelSftp.put(new FileInputStream(sourceFile), FILEUPLOADPATH, ChannelSftp.OVERWRITE);

        } else {

            System.out.println("inside else " + sourceFile.getName());
            JOptionPane.showMessageDialog(null, "Error cannot upload" , "Error",
                    JOptionPane.ERROR_MESSAGE);

            uploadStatus = false;
            File[] files = sourceFile.listFiles();

            if (files != null && !sourceFile.getName().startsWith(".")) {

                channelSftp.cd(destinationPath);
                SftpATTRS attrs = null;

                // check if the directory is already existing
                try {
                    attrs = channelSftp.stat(destinationPath + "/" + sourceFile.getName());
                } catch (Exception e) {
                    System.out.println(destinationPath + "/" + sourceFile.getName() + " not found");
                }

                // else create a directory
                if (attrs != null) {
                    System.out.println("Directory exists IsDir=" + attrs.isDir());
                } else {
                    System.out.println("Creating dir " + sourceFile.getName());
                    channelSftp.mkdir(sourceFile.getName());
                }

                for (File f: files) {
                    recursiveFolderUpload(f.getAbsolutePath(), destinationPath + "/" + sourceFile.getName(), inputUserID);
                }

            }
        }

    }


    public String getUpVideoLocation() {
        fullPath = jTextField3.getText();
        return this.fullPath;
    }

    public String getDownVideoLocation() {
        fullPath = jTextField4.getText();
        return this.fullPath;
    }

    public String getLeftVideoLocation() {
        fullPath = jTextField1.getText();
        return this.fullPath;
    }

    public String getRightVideoLocation() {
        fullPath = jTextField2.getText();
        return this.fullPath;
    }

    public String getOutputVideoLocation() {
        fullPath = jTextField5.getText();
        return this.fullPath + "//";
    }

    private void fileChoose(int input) {
        JFileChooser fileopen = new JFileChooser();
        switch (input) {
            case 5:     break;
            default:    FileFilter filter = new FileNameExtensionFilter("Videos","mp4");
                fileopen.addChoosableFileFilter(filter);
                break;
        }

        fileopen.setAcceptAllFileFilterUsed(false);

        int ret;

        switch (input) {
            case 5:     fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                ret = fileopen.showDialog(null, "Choose");
                break;
            default:    ret = fileopen.showDialog(null, "Choose file");
                break;
        }

        if (ret == JFileChooser.APPROVE_OPTION) {
            switch (input) {
                case 1: jTextField1.setText(fileopen.getSelectedFile().toString());
                    break;
                case 2: jTextField2.setText(fileopen.getSelectedFile().toString());
                    break;
                case 3: jTextField3.setText(fileopen.getSelectedFile().toString());
                    break;
                case 4: jTextField4.setText(fileopen.getSelectedFile().toString());
                    break;
                case 5: jTextField5.setText(fileopen.getSelectedFile().toString());
                    break;
                case 6: jTextField7.setText(fileopen.getSelectedFile().toString());
                    break;
            }
        }
    }


    void addHoloTranListener(ActionListener listenForHoloTran) {
        jButton6.addActionListener(listenForHoloTran);
    }

    //    FirstView Listener
    private void jButton99ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: -> To convert
        jPanel4.setVisible(false);
        jPanel2.setVisible(true);
        jPanel3.setVisible(true);
        pack();
    }

    private void jButtonBack1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: -> To Back
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);

        jPanel1.setVisible(true);
        jPanel4.setVisible(true);
        pack();
    }

    private void jButtonBack3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: -> To Back
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        //jPanel5.setVisible(false);
        jPanel7.setVisible(false);
        jPanel6.setVisible(false);
        jPanel1.setVisible(true);
        jPanel5.setVisible(true);
        userID = "";
        userName = "";
        loginSuccess = false;
        pack();
    }

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel5.setVisible(false);

        jPanel1.setVisible(true);
        jPanel7.setVisible(true);
        pack();

    }

//
    private void jButton88ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: -> To login
        jPanel4.setVisible(false);
        jPanel5.setVisible(true);
        connect();
        pack();
    }
    //    p3 Listener
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: -> To p4
        jPanel5.setVisible(false);
        jPanel6.setVisible(true);
        connect();
        pack();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtonBack1;
    private javax.swing.JButton jButtonBack2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;


    //    Variable for multiple view
//    First View Variable
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jButton88;
    private javax.swing.JButton jButton99;
    private javax.swing.JLabel jLabel8;

    //    p3 Variable
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField6;

    //    p4 Variable
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;

    //p7
    private javax.swing.JButton jButton1999;
    // private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel299;
    private javax.swing.JLabel jLabel3999;
    private javax.swing.JLabel jLabel499;
    private javax.swing.JLabel jLabel599;
    // private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jTextField199;
    private javax.swing.JTextField jTextField299;
    private javax.swing.JTextField jTextField399;
    private javax.swing.JButton jButtonBack3;
    private javax.swing.JButton jButtonBack4;

}