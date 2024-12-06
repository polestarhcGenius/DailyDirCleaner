import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.regex.Pattern;


public class DummyMaker {
    public static void main(String[] args) throws IOException {

//        String path = "C:\\Users\\polestarhc\\Desktop\\업무\\1.JAVA_project_dir\\DailyDirCleaner\\src\\test\\resources\\dummyDir";
        // String path = "D:\\";
        String sourcePath = "D:\\dummy_backup";
        String targetPath = "C:\\Users\\polestarhc\\Desktop\\업무\\1.JAVA_project_dir\\DailyDirCleaner\\src\\test\\resources\\dummyDir\\";

        Path startPath = Paths.get(sourcePath);

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String beforePath = file.toFile().getAbsolutePath();
                    String[] fileNameSplit = beforePath.split(Pattern.quote("\\"));
                    String fileName = fileNameSplit[file.getNameCount()];
                    File beforeFile = new File(beforePath);
                    File afterFile = new File(targetPath + fileName);
                    Files.copy(beforeFile.toPath(), afterFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(file.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        File file = new File(targetPath + "마지막_파일.txt");
        file.delete();
        try {
            if (file.createNewFile()){
                System.out.println("마지막 파일 생성완료");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
