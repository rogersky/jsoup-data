package cn.cebest.jsoup;


import cn.cebest.entity.ContentFieldDTO;
import cn.cebest.entity.Newest;
import cn.cebest.util.DownloadUtil;
import cn.cebest.util.UploadUtil;

public class Test {
	public static void main(String[] args) throws Exception {
		testUploadImg();
	}
	
	/**
	 * 下载方法测试
	 * @throws Exception
	 */
	public static void testDownload() throws Exception{
		DownloadUtil downloadUtil = new DownloadUtil();
		
		downloadUtil.downloadFile("http://www.bnbm.com.cn/upload/UploadFiles/2018/2/201827181252305.pdf", "123.pdf");
	}
	
	/**
	 * 上传文件方法测试
	 * @throws Exception
	 */
	public static void testUploadFile() throws Exception{
		String session = "SESSION=7c23a085-bfad-4ee5-a83e-3fb18e618c8b; td_cookie=1827537116; fr=2756a7f1ffc9434991118ef3143df18d";
		UploadUtil uploadUtil = new UploadUtil("131061", null, "1901155043.pool1-gcsite.make.yun300.cn", session);
		
		uploadUtil.uplaodFile("123.pdf");
	}
	
	
	
	
	
	
	
	
	/**
	 * 上传图片方法测试
	 * @throws Exception
	 */
	public static void testUploadImg() throws Exception{
		String session = "SESSION=7ad62dc9-f6be-4f00-9393-c580b9d6f206; fr=2756a7f1ffc9434991118ef3143df18d; td_cookie=2081420722; JSESSIONID=AF1BB27D5126266DE2C6867E4251B9FD";
		UploadUtil uploadUtil = new UploadUtil("131061", null, "1901155043.pool1-gcsite.make.yun300.cn", session);
		
		uploadUtil.uplaodImg("1.jpg");
	}
	
	
	/**
	 * 保存无文件内容方法测试
	 * @throws Exception
	 */
	public static void testSaveContent() throws Exception{
		
		String tenantId = "131061";
		String columnId = "305";
		String webSite = "1901155043.pool1-gcsite.make.yun300.cn";
		String cookie = "SESSION=cc7bdc77-3cd7-49aa-bc18-d69420f5da2e; td_cookie=1827537116; fr=2756a7f1ffc9434991118ef3143df18d";
		
		
		UploadUtil uploadUtil = new UploadUtil(tenantId, columnId, webSite, cookie);
		
		Newest newest = new Newest(columnId);
		
		newest.setTitle("123");
		newest.setSource("2011-11-11");
		
		//此行必须
		newest.setContentFieldDTOs(newest.getContentFields(newest, null,null));
		uploadUtil.saveContent(newest);
	}
	
	/**
	 * 保存有文件内容方法测试
	 * @throws Exception
	 */
	public static void testSaveContentConFile() throws Exception{
		
		/**
		 * 全局参数
		 */
		String tenantId = "131061";
		String columnId = "305";
		String webSite = "1901155043.pool1-gcsite.make.yun300.cn";
		String cookie = "SESSION=a59fc8ac-cee4-4430-a3af-b9741b486ebf; fr=2756a7f1ffc9434991118ef3143df18d; td_cookie=2081420722; JSESSIONID=D485E765FE58CCBFCF383091197A99B1";
		//String fileName = "123.pdf";
		
		UploadUtil uploadUtil = new UploadUtil(tenantId, columnId, webSite, cookie);
		//上传文件
		//Map<String, Object> retMap = uploadUtil.uplaodFile(fileName);
		
		Newest newest = new Newest(columnId);
		newest.setTitle("123");
		newest.setSource("2011-11-11");
		
		//文件dto 
		/*ContentFieldDTO dto = new ContentFieldDTO(836, "ATTACHMENT", 13);
		dto.setFileId((int)retMap.get("id"));
		dto.setFilePath("/api/upload/uploadService/dowload?fileId="+(int)retMap.get("id")+"&tenantId="+tenantId);
		dto.setFileName(retMap.get("fileName")+"."+retMap.get("extName"));*/
		
		//缩略图dto
		ContentFieldDTO picDto = new ContentFieldDTO(3140, "THUMBNAIL", 11);
		picDto.setFileId(929);
		picDto.setFilePath("/repository/image/qgzo9e52SQGASRYxeuwlfw.png");
		picDto.setFileName("3");
		picDto.setFileType(11);
		//此行必须
		newest.setContentFieldDTOs(newest.getContentFields(newest, null, picDto));
		uploadUtil.saveContent(newest);
	}
	
	
}
