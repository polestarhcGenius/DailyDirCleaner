package org.example;
import java.time.Clock;
import java.time.LocalDateTime;

public class TimeUtil {
    // 한국 표준시 00:00:00 까지 남은 시간이 몇 초인지 조회
    public long getRemainingSecondsUntilNextDay(){
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        int HH = localDateTime.getHour();
        int mm =  localDateTime.getMinute();
        int ss = localDateTime.getSecond();
        return 86400 - HH*3600 - mm*60 - ss;
    }

    //  한국 표준시 00:00:00 까지 대기
    public void sleepNextDay() throws InterruptedException {
        Thread.sleep(1000 * getRemainingSecondsUntilNextDay());
    }

}
