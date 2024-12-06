package org.example;
import org.yaml.snakeyaml.Yaml;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtil {
    //삭제 대상 폴더 정보가 담긴 yml 파일 읽기
    public List<String> yamlDataLoader(){
        try{
            Map<String, List<String>> dirData = new Yaml().load(new FileReader("DirConfig.yml"));
            return dirData.get("dir");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "설정파일 DirConfig.yml이 존재하지 않습니다. 프로그램을 종료합니다.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
        assert files != null;
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


    public void deleteFile(List<String> pathList){
        for (String path : pathList){
            if (!new File(path).isDirectory()){ // 경로 유효성 검증
                JOptionPane.showMessageDialog(null, "디렉토리" + path + "는 존재하지 않거나, 올바르지 않은 경로입니다.");
                continue;
            }
            List<String> fileList = getFileList(path);
            for (String file : fileList){
                boolean result = new File(file).delete(); // 삭제 수행
                if (result){
                    System.out.println("Deleted file: " + file);
                } else {
                    JOptionPane.showMessageDialog(null, file + "은 시스템 파일이거나 현재 실행중인 파일이므로 삭제에 실패했습니다.");
                    System.out.println("Failed to delete file: " + file);
                }
            }
        }
    }


}
