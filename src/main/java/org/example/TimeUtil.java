package org.example;
import javax.swing.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class TimeUtil extends Thread {
    String atTheTime;
    JLabel remainTime;
    FileUtil fileUtil = new FileUtil();
    JTextArea resultTextArea;

    public TimeUtil(String atTheTime, JLabel remainTime, JTextArea resultTextArea) {
        this.atTheTime = atTheTime;
        this.remainTime = remainTime;
        this.resultTextArea = resultTextArea;
    }

    public String getRemainingSecondsUntilNextDay(){
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        int HH = localDateTime.getHour();
        int mm =  localDateTime.getMinute();
        int ss = localDateTime.getSecond();
        long remainingSeconds;
        if (Integer.parseInt(atTheTime) <= HH){
            remainingSeconds = 86400 - HH*3600 - mm*60 - ss + Integer.parseInt(atTheTime) * 3600L;
        } else {
            remainingSeconds = Integer.parseInt(atTheTime) * 3600L - HH*3600 - mm*60 - ss;
        }

        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(1000);
                String remainTimeText = getRemainingSecondsUntilNextDay();
                remainTime.setText(remainTimeText);
                if (remainTimeText.equals("00:00:00") || remainTimeText.equals("00:00:01")){
                    List<String> ymlData =  fileUtil.yamlDataLoader();
                    fileUtil.deleteFile(ymlData, resultTextArea);
                    Thread.sleep(60000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
