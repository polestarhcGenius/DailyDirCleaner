import org.example.FileUtil;
import java.util.List;

public class Main {
    public static void main(String[] args){
        System.out.println("삭제 시작");
        FileUtil fileUtil = new FileUtil();
        List<String> ymlData =  fileUtil.yamlDataLoader();
        fileUtil.deleteFile(ymlData);
    }
}