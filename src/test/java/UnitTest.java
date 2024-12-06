import org.example.FileUtil;
import org.example.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UnitTest {
    FileUtil fileUtil = new FileUtil();
    TimeUtil timeUtil = new TimeUtil();

    // 자정까지 남은시간초 테스트 테스트
    public static void getRemainingSecondsUntilNextDayTest(){
        // Arrange
        SimpleDateFormat resultSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat expectedSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        TimeUtil timeUtil = new TimeUtil();

        // Act
        long result = timeUtil.getRemainingSecondsUntilNextDay();

        // Assert
        calendar.add(Calendar.SECOND, (int)result);
        String expectResult = expectedSdf.format(calendar.getTime()) + " 00:00:00";
        String testResult = resultSdf.format(calendar.getTime());
        if (!expectResult.equals(testResult)) {
            System.out.println(expectResult);
            System.out.println(testResult);
            throw new RuntimeException("내일까지_남은시간_조회_테스트 실패");
        }
    }

    // 자정까지 대기 테스트
    public static void sleepNextDayTest() throws InterruptedException {
        // Arrange
        SimpleDateFormat resultSdf = new SimpleDateFormat("HH:mm:ss");
        TimeUtil timeUtil = new TimeUtil();

        // Act
        timeUtil.sleepNextDay();

        // Assert
        String testResult = resultSdf.format(new Date());
        if (!testResult.equals("00:00:00")) {
            System.out.println(testResult);
            throw new RuntimeException("자정까지 대기 테스트 실패");
        }
    }

    // 설정파일 정보 불러오기 테스트
    public static void yamlDataLoaderTest(){
        // Arrange

        // Act

        // Assert

    }


    public static void main(String[] args) {
        getRemainingSecondsUntilNextDayTest();




    }




}
