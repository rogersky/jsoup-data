package cn.cebest.main.bxjc;


import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.cebest.entity.ContentFieldDTO;
import cn.cebest.entity.Newest;
import cn.cebest.util.DownloadUtil;
import cn.cebest.util.UploadUtil;

/**
 * 原网站：http://www.bnbm.com.cn/common/news_end.asp?id=1658
 * 北新建材,新闻栏目
 * @author Roger
 *
 */
public class Main {
	//全局变量
	private static String tenantId = "124554";
	private static String webSite = "1812115029.pool1-gcsite.make.yun300.cn";
	private static String cookie = "SESSION=1b295508-b860-4621-a031-cc1703997c0a; JSESSIONID=4F52503056563600039A5DD468B3A48A; fr=2756a7f1ffc9434991118ef3143df18d";
	
	
	
	public static void main(String[] args) throws Exception {
		//新闻数据
		importNewsData();
		//定期公告
		//importNotice();
		//临时公告
		//importNoticeTemp();
	}
	
	
	
	
	/**
	 * 投资者关系---定期公告
	 * @throws Exception
	 */
	public static void importNotice() throws Exception {
		String sourceUrl = "http://www.bnbm.com.cn/common/ifm_touzi_01.asp?class_id={page}";
		String columnId = "239";
		
		for(int i=132; i<135; i++){
			String url = sourceUrl.replace("{page}", String.valueOf(i));
			//获取公告列表
			Document document = Jsoup.connect(url).get();
			Elements elements = document.select("table[width='93%'] tbody");
			for (int j=0; j<elements.size(); j++) {
				try {
					Elements trEle = elements.get(j).select("tr");
					//获取时间
					//String time = trEle.select("td:eq(0)").text();
					String time = "2012/12/12";
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					String date = String.valueOf(format.parse(time).getTime());
					//获取标题
					String title = trEle.select("td:eq(0)").text();
					//获取文件路径
					String fileUrl = trEle.select("td:eq(2)").select("a").attr("href").replace("..", "http://www.bnbm.com.cn");
					String[] arr = trEle.select("td:eq(2)").select("a").attr("href").split("\\.");
					String houzhui = arr[arr.length-1];
					if(houzhui.contains("upload")){
						continue;
					}
					//下载文件
					DownloadUtil downloadUtil = new DownloadUtil();
					downloadUtil.downloadFile(fileUrl, title+"." + houzhui);
					
					//上传文件
					UploadUtil uploadUtil = new UploadUtil(tenantId, columnId, webSite, cookie);
					Map<String, Object> retMap = uploadUtil.uplaodFile(title+"." + houzhui);
					
					//文件dto 
					ContentFieldDTO dto = new ContentFieldDTO(836, "ATTACHMENT", 13);
					dto.setFileId((int)retMap.get("id"));
					dto.setFilePath("/api/upload/uploadService/dowload?fileId="+(int)retMap.get("id")+"&tenantId="+tenantId);
					dto.setFileName(retMap.get("fileName")+"."+retMap.get("extName"));
					
					//构建内容实体
					Newest newest = new Newest(columnId);
					newest.setTitle(title);
					newest.setPubTime(date);
					newest.setContentFieldDTOs(newest.getContentFields(newest, dto,null));
					
					//保存内容
					uploadUtil.saveContent(newest);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	
	
	/**
	 * 投资者关系---临时公告
	 * @throws Exception
	 */
	public static void importNoticeTemp() throws Exception {
		String sourceUrl = "http://www.bnbm.com.cn/common/touzi_pl_ls.asp?page={page}";
		String columnId = "239";
		
		for(int i=1; i<16; i++){
			String url = sourceUrl.replace("{page}", String.valueOf(i));
			//获取公告列表
			Document document = Jsoup.connect(url).get();
			Elements elements = document.select("td[valign='top'] table[width='670']");
			for (int j=1; j<elements.size(); j++) {
				try {
					Elements trEle = elements.get(j).select("table tbody tr table tr");
					//获取时间
					String time = trEle.select("td:eq(0)").text();
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					String date = String.valueOf(format.parse(time).getTime());
					//获取标题
					String title = trEle.select("td:eq(1)").text();
					//获取文件路径
					String fileUrl = trEle.select("td:eq(3)").select("a").attr("href").replace("..", "http://www.bnbm.com.cn");
					
					//下载文件
					DownloadUtil downloadUtil = new DownloadUtil();
					downloadUtil.downloadFile(fileUrl, title+".pdf");
					
					//上传文件
					UploadUtil uploadUtil = new UploadUtil(tenantId, columnId, webSite, cookie);
					Map<String, Object> retMap = uploadUtil.uplaodFile(title+".pdf");
					
					//文件dto 
					ContentFieldDTO dto = new ContentFieldDTO(836, "ATTACHMENT", 13);
					dto.setFileId((int)retMap.get("id"));
					dto.setFilePath("/api/upload/uploadService/dowload?fileId="+(int)retMap.get("id")+"&tenantId="+tenantId);
					dto.setFileName(retMap.get("fileName")+"."+retMap.get("extName"));
					
					//构建内容实体
					Newest newest = new Newest(columnId);
					newest.setTitle(title);
					newest.setPubTime(date);
					newest.setContentFieldDTOs(newest.getContentFields(newest, dto,null));
					
					//保存内容
					uploadUtil.saveContent(newest);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			
			
		}
	}
	
	
	
	/**
	 * 新闻
	 */
	public static void importNewsData() throws Exception {
		String sourceUrl = "http://www.bnbm.com.cn/common/news.asp?page={page}";
		String columnId = "320";
		
		int m = 1;
		for(int i=1; i<42; i++){
			String url = sourceUrl.replace("{page}", String.valueOf(i));
			
			//获取新闻列表
			Document document = Jsoup.connect(url).get();
			Elements elements = document.select("tbody table[width='795'][cellpadding='0']");
			for (Element element : elements) {
				Element aElem = element.selectFirst("a");
				
				String href = "http://www.bnbm.com.cn/common/" + aElem.attr("href");
				String title = aElem.text();
				String time = element.selectFirst(".font").text();
				
				//获取时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	            String date = String.valueOf(format.parse(time).getTime());
				//获取新闻详情
				Document detailDocument = Jsoup.connect(href).get();
				
				/*Elements spanEles = detailDocument.selectFirst("table[width='95%'][cellpadding='0'] tbody").select("span");
				for (Element span : spanEles) {
					span.attr("style", "");
					span.attr("class", "content");
				}*/
				
				Element element2 = detailDocument.selectFirst("table[width='95%'][cellpadding='0'] tbody");
				Elements images = element2.select("img");
				for (Element element3 : images) {
					String src = "http://www.bnbm.com.cn" + element3.attr("src");
					DownloadUtil downloadUtil = new DownloadUtil();
					String[] split = element3.attr("src").split("/");
					try {
						downloadUtil.downloadFile(src, split[split.length-1]);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
				
				//String content = detailDocument.selectFirst("table[width='95%'][cellpadding='0'] tbody").html();
				//content = content.replace("src=\"", "src=\"http://www.bnbm.com.cn");
				
				
				
				/*//新闻概要
				String summary = detailDocument.selectFirst("table[width='95%'][cellpadding='0'] tbody").text();
				if(StringUtils.isNotEmpty(summary) && summary.length()>80){
					summary = summary.substring(0, 80);
				}
				
				//缩略图dto
				ContentFieldDTO picDto = new ContentFieldDTO(3260, "THUMBNAIL", 11);
				picDto.setFileId(521);
				picDto.setFilePath("/repository/image/EPxeS0_iR92-C-gOGLNV-A.jpg");
				picDto.setFileName("news05");
				picDto.setFileType(11);
				
				//构建内容实体
				Newest newest = new Newest(columnId);
				newest.setTitle(title);
				newest.setPubTime(date);
				newest.setContent(content);
				newest.setSummary(summary);
				newest.setContentFieldDTOs(newest.getContentFields(newest, null,picDto));
				
				UploadUtil uploadUtil = new UploadUtil(tenantId, columnId, webSite, cookie);
				uploadUtil.saveContent(newest);
				System.out.println("第"+(m)+"条数据导入成功");
				m = m + 1;*/
			}
		}
	}
	
	
}
