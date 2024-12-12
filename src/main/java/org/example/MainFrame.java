package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainFrame extends JFrame implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger("MainFrame");

    // private
    // Container cp;
    JButton btnImmediatelyDelete, btnScheduleDelete, btnSetDir;
    TimeUtil timeUtil;
    JTextField textField;
    JTextArea resultTextArea;
    FileUtil fileUtil = new FileUtil();
    JLabel remainTime;

    public MainFrame(String title) {
        super(title);
        // cp = this.getContentPane();
        // cp.setBackground(new Color(244,244,244));
        this.setBounds(400,300,850,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("MainFrame start");
        setDesign();
        setVisible(true);
    }

    public void setDesign(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        btnImmediatelyDelete = new JButton("즉시삭제");
        btnScheduleDelete = new JButton("예약삭제");
        btnSetDir = new JButton("삭제폴더지정");

        btnImmediatelyDelete.setPreferredSize(new Dimension(160,40));
        btnScheduleDelete.setPreferredSize(new Dimension(160,40));
        btnSetDir.setPreferredSize(new Dimension(160,40));

        btnImmediatelyDelete.setFont(new Font("default", Font.BOLD, 16));
        btnScheduleDelete.setFont(new Font("default", Font.BOLD, 16));
        btnSetDir.setFont(new Font("default", Font.BOLD, 16));

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

        JLabel jLabel = new JLabel(" 파일 삭제 프로그램");
        ImageIcon icon = new ImageIcon("src/main/resources/img/pshc_logo.png");
        jLabel.setIcon(icon);
        this.setIconImage(icon.getImage());

        jLabel.setFont(new Font("default", Font.BOLD, 22));
        this.add(jLabel);

        this.add(Box.createRigidArea(new Dimension(65, 0)));

        this.add(btnImmediatelyDelete);
        this.add(btnScheduleDelete);
        this.add(btnSetDir);


        this.add(new JLabel("다음 삭제까지 남은 시간"));


        remainTime = new JLabel("00:00:00");
        this.add(remainTime);


        this.add(Box.createRigidArea(new Dimension(350, 0)));


        this.add(new JLabel("예약시간 매일"));

        textField = new JTextField(3);

        this.add(textField);

        this.add(new JLabel("시 삭제(입력값 : 0~23)"));

        resultTextArea = new JTextArea();
        resultTextArea.setLineWrap(true); // 자동 줄바꿈
        resultTextArea.setWrapStyleWord(true); // 들여 쓰기
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(820,350));

        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source==btnImmediatelyDelete){
            logger.info("btnImmediatelyDelete clicked");
            JOptionPane.showMessageDialog(this, "즉시삭제를 시작합니다.");
            List<String> ymlData =  fileUtil.yamlDataLoader();
            fileUtil.deleteFile(ymlData, resultTextArea);
        } else if (source==btnScheduleDelete){
            if (btnScheduleDelete.getText() == "예약삭제"){
                logger.info("btnScheduleDelete clicked");
                if (textField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this, "예약 시간을 지정해 주세요.");
                    return;
                } else {
                    try{
                        int hour = Integer.parseInt(textField.getText());
                        if (hour > 23 || hour < 0){
                            JOptionPane.showMessageDialog(this, "올바른 값을 지정해 주세요. (0~23) ");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "올바른 값을 지정해 주세요. (0~23) ");
                        return;
                    }
                }
                timeUtil = new TimeUtil(textField.getText(), remainTime, resultTextArea);
                timeUtil.start();
                textField.setEditable(false);
                btnScheduleDelete.setText("예약삭제중단");
                btnSetDir.setEnabled(false);
            } else{
                timeUtil.interrupt();
                btnScheduleDelete.setText("예약삭제");
                btnSetDir.setEnabled(true);
                textField.setEditable(true);
                remainTime.setText("00:00:00");
            }
        } else if (source==btnSetDir) {
            logger.info("btnSetDir clicked");
            new SetDirDialog(this, "삭제 폴더 지정", true);
        }
    }
}
