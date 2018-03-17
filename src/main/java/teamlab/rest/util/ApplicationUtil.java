package teamlab.rest.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ApplicationUtil {
    
    /**
     * エスケープする文字のmap
     */
    public static final HashMap<String, String> ESCAPE_SEQUENCE = new HashMap<String,String>(){{
        put("&", "&amp;");
        put("\"", "&quot;");
        put("<", "&lt;");
        put(">", "&gt;");
        put("'", "&#39;");
    }};

    /**
     * 文字をエスケープする
     * @param target
     * @return
     */
    public static String translateEscapeSequence(String target){
        if(StringUtils.isEmpty(target))
            return target;
        String result = target;
        for(Map.Entry<String, String> escapeLiterature : ESCAPE_SEQUENCE.entrySet())
            result = target.replace(escapeLiterature.getKey(), escapeLiterature.getValue());
        return result;
    }
    
    /**
     * 拡張子を取得
     * @param path
     * @return
     */
    public static String getSuffix(String fileName) {
        if(!fileName.contains("."))
            return "";
        int lastDotPosition = fileName.lastIndexOf(".");
        if (lastDotPosition != -1) {
            return "." + fileName.substring(lastDotPosition + 1);
        }
        return "";
    }
    
    /**
     * export先のパスがあるかチェック
     * なければ作成する
     * @param exportPath
     */
    public static void checkDir(String exportPath){
        try {
            Files.createDirectories(Paths.get(exportPath));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
    
    /**
     * ファイルをアップロードする
     * @param multipartFile
     * @param exportPath
     */
    public static String uploadFile(MultipartFile multipartFile){
        if(multipartFile == null || multipartFile.getContentType() == null)
            return "";
        if(!multipartFile.getContentType().contains("image"))
            return "";
        Path path = Paths.get("uploadfile");
        checkDir(path.toString());
        String filename = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        String sufix = ApplicationUtil.getSuffix(multipartFile.getOriginalFilename());
        String exportPath = path.toString() + "/" + filename + sufix;
        try {
          BufferedInputStream in = new BufferedInputStream(multipartFile.getInputStream());
          BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(exportPath));
          FileCopyUtils.copy(in, out);
        } catch (IOException e) {
          throw new RuntimeException(e.getMessage(), e);
        }
        return exportPath;
    }
    
    /**
     * ファイルを削除する
     * @param filePath
     */
    public static void deleteFile(String filePath){
        if(org.springframework.util.StringUtils.isEmpty(filePath))
            return ;
        checkDir(filePath);
        new File(filePath).delete();
    }
}
