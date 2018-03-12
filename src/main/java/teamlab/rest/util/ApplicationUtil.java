package teamlab.rest.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class ApplicationUtil {

	/**
	 * 全てのフィールドがnullかチェックする
	 * @param object
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static boolean checkAllNull(Object object) throws IllegalArgumentException, IllegalAccessException {
		boolean checkNull = true;
		for(Field field : object.getClass().getDeclaredFields()){
			field.setAccessible(true);
			if(field.get(object) != null){
				checkNull = false;
				break;
			}
		}
		return checkNull;
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
