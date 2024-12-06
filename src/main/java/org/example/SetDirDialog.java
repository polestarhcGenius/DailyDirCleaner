package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;


public class SetDirDialog extends JDialog {
    JButton addDir, btnSave, btnCancel;
    FileUtil fileUtil = new FileUtil();
    List<String> ymlDate;


    public SetDirDialog(MainFrame mainFrame, String title, boolean modal){
        super(mainFrame, title, modal);
        ymlDate = fileUtil.yamlDataLoader();

        this.setBounds(mainFrame.getX() + 200, mainFrame.getY() + 125, 800, 250);
        this.getContentPane().setLayout(new FlowLayout());


        JPanel modalTopPanel = new JPanel();
        // modalTopPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10) );

        modalTopPanel.add(new JLabel("추가된 경로"));
        JTextArea dirTextArea = new JTextArea(5, 50);
        dirTextArea.setWrapStyleWord(true);
        JScrollPane dirScrollPane = new JScrollPane(dirTextArea);
        dirTextArea.setEditable(false);

        for (String dir : ymlDate) {
            dirTextArea.append(dir + "\n");
        }

        // dirTextArea.setText(ymlDate.toString().replace(",", "\n"));


        modalTopPanel.add(dirScrollPane);
        this.getContentPane().add(modalTopPanel);



        JPanel modalMiddlePanel = new JPanel();
        // modalMiddlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10) );
        modalMiddlePanel.add(new JLabel("경로 입력"));

        JTextField dirTextInput = new JTextField(50);
        modalMiddlePanel.add(dirTextInput);

        addDir = new JButton("추가");
        addDir.setBackground(new Color(228, 228, 228));
        addDir.setOpaque(true);
        addDir.setBorderPainted(false);
        addDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newDir = dirTextInput.getText();
                if (!newDir.isEmpty()) {
                    if (!new File(newDir).isDirectory()){
                        JOptionPane.showMessageDialog(null, "디렉토리" + newDir + "는 존재하지 않거나, 올바르지 않은 경로입니다.");
                        return;
                    }
                    dirTextArea.append(newDir + "\n");
                    dirTextInput.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "경로를 입력해 주세요.");
                }
            }
        });

        modalMiddlePanel.add(addDir);



        this.getContentPane().add(modalMiddlePanel);





        JPanel modalBottomPanel = new JPanel();

        btnSave = new JButton("저장");
        btnSave.setBackground(new Color(228, 228, 228));
        btnSave.setOpaque(true);
        btnSave.setBorderPainted(false);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println(dirTextArea.getText());
                // System.out.println(dirTextArea.toString());

                try {
                    fileUtil.yamlDataWriter(dirTextArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        modalBottomPanel.add(btnSave);




        btnCancel = new JButton("취소");
        btnCancel.setBackground(new Color(228, 228, 228));
        btnCancel.setOpaque(true);
        btnCancel.setBorderPainted(false);
        modalBottomPanel.add(btnCancel);

        this.getContentPane().add(modalBottomPanel);



        this.setVisible(true);
    }



}

