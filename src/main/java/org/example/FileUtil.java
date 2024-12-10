package org.example;
import org.yaml.snakeyaml.Yaml;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    //삭제 대상 폴더 정보가 담긴 yml 파일 읽기
    public List<String> yamlDataLoader(){
        try{
            Map<String, List<String>> dirData = new Yaml().load(new FileReader("DirConfig.yml"));
            return dirData.get("dir");
        } catch (Exception e) {
            logger.error(e.getMessage());
            try{
                Map<String, List<String>> dirData = new Yaml().load(new FileReader("src/main/resources/DirConfig.yml"));
                return dirData.get("dir");
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void yamlDataWriter(List<String> dirs) throws FileNotFoundException {
        Map<String, List<String>> data = new LinkedHashMap<String, List<String>>();
        data.put("dir", dirs);

        Yaml yaml = new Yaml();
        PrintWriter writer = new PrintWriter(new File("DirConfig.yml"));
        yaml.dump(data, writer);
    }

    // 대상 파일의 생성 시간 조회
    public long getCreationTime(String path){
        Path file = Paths.get(path);
        try {
            FileTime creationTime = (FileTime) Files.getAttribute(file, "creationTime");
            return creationTime.toMillis();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 최신 파일과 폴더를 제외한 모든 항목 얻기
    public List<String> getFileList(String path){
        String lastFile = null;
        long baseLine = 0;
        File dir = new File(path);
        String[] files = dir.list();
        List<String> result = new ArrayList<String>();

        if (files == null) {
            return result;
        }


        for (String s : files) {
            File file = new File(dir.getPath() + File.separator + s);
            if (file.exists() && file.isFile()) {
                result.add(dir.getPath() + File.separator + s);
                long creationTime = getCreationTime(dir.getPath() + File.separator + s);
                if (creationTime > baseLine){
                    baseLine = creationTime;
                    lastFile = dir.getPath() + File.separator + s;
                }
            }
        }

        result.remove(String.valueOf(lastFile));
        return result;
    }

    public String getDiskInfo(){
        String result = "";
        String drive;
        double totalSize, freeSize, useSize;
        File[] roots = File.listRoots();
        for (File root : roots) {
            drive = root.getAbsolutePath();
            totalSize = root.getTotalSpace() / (1024.0*1024*1024);
            freeSize = root.getFreeSpace() / (1024.0*1024*1024);
            result += drive + " 드라이브 남은 용량 : " + Math.floor(freeSize/totalSize*100) + "%\n";
        }
        return result;
    }

    public void deleteFile(List<String> pathList, JTextArea resultTextArea){
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        String startTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("\n< {} 삭제 시작>\n", startTime);
        resultTextArea.append("\n < " + startTime + " 삭제 시작>\n");
        
        String logData = "";
        for (String path : pathList){

            List<String> fileList = getFileList(path);
            int totalCount = fileList.size();
            int errorCount = 0;
            String err_msg = "";

            if (!new File(path).isDirectory()){ // 경로 유효성 검증
                logData += path + ": 해당 디렉토리는 존재하지 않거나, 올바르지 않은 경로입니다.\n";
                continue;
            }

            for (String file : fileList){
                Path filePath = Paths.get(file);
                try{
                    Files.delete(filePath);
                    logger.info("Deleted file: {}", file);
                    resultTextArea.append("Deleted file: " + file + "\n");
                } catch (IOException e) {
                    errorCount++;
                    err_msg += e + "\n";
                    logger.error("Failed to delete file: " + file + " " + e + "\n");
                    resultTextArea.append("Failed to delete file: " + file + " " + e + "\n");
                }
            }

            int successCount = totalCount - errorCount;
            logData += path + " 총 " + totalCount + "건 중 " + successCount + "건 성공, " + errorCount + "건 실패\n";
            logData += err_msg + "\n";
        }

        localDateTime = LocalDateTime.now(Clock.systemDefaultZone());
        String endTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String diskInfo = getDiskInfo();
        logger.info("\n< {} 삭제 결과>\n {} \n {}", endTime, logData, diskInfo);
        resultTextArea.append("\n < " + endTime + " 삭제 결과>\n");
        resultTextArea.append(logData);
        resultTextArea.append(diskInfo);
    }

}
