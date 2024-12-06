package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    Container cp;
    JButton btnImmediatelyDelete, btnScheduleDelete, btnSetDir;



    public MainFrame(String title) {
        super(title);
        cp = this.getContentPane();
        this.setBounds(400,300,850,500);
        cp.setBackground(new Color(244,244,244));
        setDesign();
        setVisible(true);
    }

    public void setDesign(){
        this.setLayout(new FlowLayout());

        // 160 40
        btnImmediatelyDelete = new JButton("즉시삭제");
        btnScheduleDelete = new JButton("예약삭제");
        btnSetDir = new JButton("삭제폴더지정");

        btnImmediatelyDelete.setPreferredSize(new Dimension(160,40));
        btnScheduleDelete.setPreferredSize(new Dimension(160,40));
        btnSetDir.setPreferredSize(new Dimension(160,40));

        btnImmediatelyDelete.setFont(new Font("default", Font.BOLD, 16));
        btnScheduleDelete.setFont(new Font("default", Font.BOLD, 16));
        btnSetDir.setFont(new Font("default", Font.BOLD, 16));



        this.add(btnImmediatelyDelete);
        this.add(btnScheduleDelete);
        this.add(btnSetDir);

        btnImmediatelyDelete.setBackground(new Color(228, 228, 228));
        btnImmediatelyDelete.setOpaque(true);
        btnImmediatelyDelete.setBorderPainted(false);

        btnScheduleDelete.setBackground(new Color(228, 228, 228));
        btnScheduleDelete.setOpaque(true);
        btnScheduleDelete.setBorderPainted(false);

        btnSetDir.setBackground(new Color(228, 228, 228));
        btnSetDir.setOpaque(true);
        btnSetDir.setBorderPainted(false);

        btnImmediatelyDelete.addActionListener(this);
        btnScheduleDelete.addActionListener(this);
        btnSetDir.addActionListener(this);

//        System.out.println(btnImmediatelyDelete.getPreferredSize());
//        System.out.println(btnScheduleDelete.getPreferredSize());
//        System.out.println(btnSetDir.getPreferredSize());


    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source==btnImmediatelyDelete){
            JOptionPane.showMessageDialog(this, "즉시삭제 버튼클릭");
        } else if (source==btnScheduleDelete){
            JOptionPane.showMessageDialog(this, "예약삭제 버튼클릭");
            if (btnScheduleDelete.getText() == "예약삭제"){
                btnScheduleDelete.setText("예약삭제중단");
                btnSetDir.setEnabled(false);
            } else{
                btnScheduleDelete.setText("예약삭제");
                btnSetDir.setEnabled(true);
            }

        } else if (source==btnSetDir) {
            JOptionPane.showMessageDialog(this, "3번째 버튼클릭");
        }
    }


}
