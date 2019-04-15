package cn.cebest.util;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class DownloadUtil {
	/**
	 * 
	 * @param url 被下载文件的http链接
	 * @param fileName，下载到本地后保存的文件名
	 * @throws Exception
	 */
	public void downloadFile(String url, String fileName) throws Exception {
		String dir = "F:\\img\\";
		URL httpurl = new URL(url);
		File dirfile = new File(dir);  
        if (!dirfile.exists()) {  
        	dirfile.mkdirs();
        }
		FileUtils.copyURLToFile(httpurl, new File(dir+fileName));
}
	
}
