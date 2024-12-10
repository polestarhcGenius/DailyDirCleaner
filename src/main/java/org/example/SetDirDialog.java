package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetDirDialog extends JDialog {
    private static final Logger logger = LoggerFactory.getLogger(SetDirDialog.class);

    JButton addDir, btnSave, btnCancel;
    FileUtil fileUtil = new FileUtil();
    List<String> ymlData;
    JPanel subPanel;

    public JPanel makeSubPanel(List<String> dirData){
        logger.info("makeSubPanel start, dirData : {}", dirData);
        subPanel = new JPanel();
        subPanel.setBackground(Color.WHITE);
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

        for (String dir: dirData){
            JPanel rowPanel = new JPanel(new BorderLayout());

            JTextArea dirTextArea = new JTextArea(1, 50);
            dirTextArea.setWrapStyleWord(true);
            dirTextArea.setEditable(false);
            dirTextArea.append(dir);

            JButton btnDelete = new JButton("삭제");
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rowPanel.setVisible(false);
                    ymlData.remove(String.valueOf(dir));
                }
            });
            rowPanel.add(dirTextArea, BorderLayout.CENTER);
            rowPanel.add(btnDelete, BorderLayout.EAST);
            subPanel.add(rowPanel);
        }
        return subPanel;
    }

    public SetDirDialog(MainFrame mainFrame, String title, boolean modal){
        super(mainFrame, title, modal);
        ymlData = fileUtil.yamlDataLoader();
        ymlData = ymlData.stream()
                .filter(dir -> new File(dir).isDirectory())
                .collect(Collectors.toList());
        try {
            fileUtil.yamlDataWriter(ymlData);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }


        this.setBounds(mainFrame.getX() + 200, mainFrame.getY() + 125, 800, 250);
        this.getContentPane().setLayout(new FlowLayout());

        JPanel modalTopPanel = new JPanel();
        modalTopPanel.add(new JLabel("추가된 경로"));

        JScrollPane scroll = new JScrollPane(makeSubPanel(ymlData));
        scroll.setPreferredSize(new Dimension(650, 100));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        modalTopPanel.add(scroll);

        this.getContentPane().add(modalTopPanel);


        JPanel modalMiddlePanel = new JPanel();
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
                logger.info("추가 버튼 클릭, newDir : {}", newDir);
                if (!newDir.isEmpty()) {
                    if (!new File(newDir).isDirectory()){
                        JOptionPane.showMessageDialog(null, "디렉토리" + newDir + "는 존재하지 않거나, 올바르지 않은 경로입니다.");
                        dirTextInput.setText("");
                        return;
                    }

                    if (ymlData.contains(newDir) || ymlData.contains(newDir + File.separator) || ymlData.contains(newDir.substring(0, newDir.length()-1))){
                        JOptionPane.showMessageDialog(null, "디렉토리" + newDir + "는 이미 추가된 경로입니다.");
                        dirTextInput.setText("");
                        return;
                    }


                    ymlData.add(newDir);
                    JPanel rowPanel = new JPanel(new BorderLayout());

                    JTextArea dirTextArea = new JTextArea(1, 50);
                    dirTextArea.setWrapStyleWord(true);
                    dirTextArea.setEditable(false);
                    dirTextArea.append(newDir);

                    JButton btnDelete = new JButton("삭제");
                    btnDelete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            rowPanel.setVisible(false);
                            ymlData.remove(String.valueOf(newDir));
                        }
                    });

                    rowPanel.add(dirTextArea, BorderLayout.CENTER);
                    rowPanel.add(btnDelete, BorderLayout.EAST);

                    subPanel.add(rowPanel);
                    subPanel.revalidate();
                    subPanel.repaint();

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
                logger.info("저장 버튼 클릭");
                try {
                    fileUtil.yamlDataWriter(ymlData);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                SetDirDialog.this.setVisible(false);
            }
        });

        modalBottomPanel.add(btnSave);
        btnCancel = new JButton("취소");
        btnCancel.setBackground(new Color(228, 228, 228));
        btnCancel.setOpaque(true);
        btnCancel.setBorderPainted(false);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("취소 버튼 클릭");
                SetDirDialog.this.setVisible(false);
            }
        });
        modalBottomPanel.add(btnCancel);
        this.getContentPane().add(modalBottomPanel);
        this.setVisible(true);
    }
}

