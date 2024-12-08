package org.example;
import javax.swing.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class TimeUtil extends Thread {
    String atTheTime;
    FileUtil fileUtil = new FileUtil();
    JLabel remainTime;

    public TimeUtil(String atTheTime, JLabel remainTime) {
        this.atTheTime = atTheTime;
        this.remainTime = remainTime;
    }

//    // 한국 표준시 00:00:00 까지 남은 시간이 몇 초인지 조회
//    public long getRemainingSecondsUntilNextDay(){
//        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
//        int HH = localDateTime.getHour();
//        int mm =  localDateTime.getMinute();
//        int ss = localDateTime.getSecond();
//        return 86400 - HH*3600 - mm*60 - ss;
//    }
//
//    //  한국 표준시 00:00:00 까지 대기
//    public void sleepNextDay() throws InterruptedException {
//        Thread.sleep(1000 * getRemainingSecondsUntilNextDay());
//    }

//    public long getRemainingSecondsUntilNextDay(){
//        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
//        int HH = localDateTime.getHour();
//        int mm =  localDateTime.getMinute();
//        int ss = localDateTime.getSecond();
//        if (Integer.parseInt(atTheTime) <= HH){
//            return 86400 - HH*3600 - mm*60 - ss;
//        } else {
//            return Integer.parseInt(atTheTime) * 3600L - HH*3600 - mm*60 - ss;
//        }
//    }

    public String getRemainingSecondsUntilNextDay(){
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        int HH = localDateTime.getHour();
        int mm =  localDateTime.getMinute();
        int ss = localDateTime.getSecond();
        long remainingSeconds;
        if (Integer.parseInt(atTheTime) <= HH){
            remainingSeconds = 86400 - HH*3600 - mm*60 - ss;
        } else {
            remainingSeconds = Integer.parseInt(atTheTime) * 3600L - HH*3600 - mm*60 - ss;
        }
        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


//    public void sleepNextDay() throws InterruptedException {
//        Thread.sleep(1000 * getRemainingSecondsUntilNextDay());
//    }

    public void run(){
        System.out.println(atTheTime);
        while (true){
            try {
                Thread.sleep(1000);
                String remainTimeText = getRemainingSecondsUntilNextDay();
                remainTime.setText(remainTimeText);
                if (remainTimeText.equals("00:00:00")){
                    List<String> ymlData =  fileUtil.yamlDataLoader();
                    fileUtil.deleteFile(ymlData);
                    Thread.sleep(60000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
