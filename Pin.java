package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener {
    
    JPasswordField t1, t2, t3;
    JButton b1, b2;                               
    JLabel l1, l2, l3, l4;
    String pin;
    
    Pin(String pin) {
        this.pin = pin;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 1080);
        add(background);
        
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        
        JLabel lOldPin = new JLabel("Old PIN:");
        lOldPin.setFont(new Font("System", Font.BOLD, 16));
        lOldPin.setForeground(Color.WHITE);

        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        
        t3 = new JPasswordField();  // Old PIN input field
        t3.setFont(new Font("Raleway", Font.BOLD, 25));
        
        t1 = new JPasswordField();  // New PIN input field
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        t2 = new JPasswordField();  // Confirm new PIN input field
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setLayout(null);
        
        l1.setBounds(280, 280, 800, 35);
        background.add(l1);

        lOldPin.setBounds(180, 340, 150, 35);
        background.add(lOldPin);

        l2.setBounds(180, 390, 150, 35);
        background.add(l2);
        
        l3.setBounds(180, 440, 200, 35);
        background.add(l3);
        
        t3.setBounds(350, 340, 180, 25);
        background.add(t3);
        
        t1.setBounds(350, 390, 180, 25);
        background.add(t1);
        
        t2.setBounds(350, 440, 180, 25);
        background.add(t2);
        
        b1.setBounds(390, 500, 150, 35);
        background.add(b1);
        
        b2.setBounds(390, 550, 150, 35);
        background.add(b2);
        
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {        
            String oldPin = t3.getText().trim();
            String npin = t1.getText().trim();
            String rpin = t2.getText().trim();

            if (ae.getSource() == b1) {
                if (oldPin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Old PIN");
                    return;
                }
                if (npin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter New PIN");
                    return;
                }
                if (rpin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Re-Enter New PIN");
                    return;
                }
                if (!npin.equals(rpin)) {
                    JOptionPane.showMessageDialog(null, "New PIN and Re-Entered PIN do not match!");
                    return;
                }
                if (npin.length() < 4 || npin.length() > 6) {
    JOptionPane.showMessageDialog(null, "PIN must be 4 to 6 digits long!");
    return;
}


                // Validate Old PIN from Database
                Conn c1 = new Conn();
                String validateQuery = "SELECT pin FROM login WHERE pin = '" + oldPin + "'";
                ResultSet rs = c1.s.executeQuery(validateQuery);

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Incorrect Old PIN! Please try again.");
                    return;
                }

                // Update PIN if Old PIN is correct
                String q1 = "UPDATE bank SET pin = '" + npin + "' WHERE pin = '" + oldPin + "'";
                String q2 = "UPDATE login SET pin = '" + npin + "' WHERE pin = '" + oldPin + "'";
                String q3 = "UPDATE signup3 SET pin = '" + npin + "' WHERE pin = '" + oldPin + "'";
                
                c1.s.executeUpdate(q1);
                c1.s.executeUpdate(q2);
                c1.s.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transactions(npin).setVisible(true);
            
            } else if (ae.getSource() == b2) {
                new Transactions(pin).setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Pin("").setVisible(true);
    }
}

  
