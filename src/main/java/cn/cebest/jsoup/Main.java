package cn.cebest.jsoup;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

import cn.cebest.entity.ContentFieldDTO;
import cn.cebest.entity.Newest;
import cn.cebest.util.HttpClientUtils;

public class Main {
	
	public static String session = "SESSION=83ebac2f-c82e-4851-9a6d-64ae58ddbbd0; JSESSIONID=398426D567AAF99E26A693D64CBE7736; fr=2756a7f1ffc9434991118ef3143df18d";
	
	public static String tenantId = "116303";
	public static String webSite = "http://www.nanhaicorp.com";
	public static String columId = "95";
	public static String newsCategoryId = "20";
	
	public static void main(String[] args) throws Exception {
		//简体站
		List<String[]> rList = getJtList();
		
		//繁体站
		//List<String[]> rList = getFtList();
		
		//英文站
		//List<String[]> rList = getEtList();
		
		for (String[] str : rList) {
			tenantId = str[0];
			webSite = str[1];
			columId = str[2];
			newsCategoryId = str[3];
			
			run();
		}
		
	}
	/**
	 * 英文站
	 */
	public static List<String[]> getEtList(){
		String site = "http://en.nanhaicorp.com";
		String tenant = "116608";
		List<String[]> rList = new ArrayList<>();
		rList.add(new String[]{tenant,site,"95","15"});//集团动态
		return rList;
	}
	
	
	
	/**
	 * 繁体站
	 */
	public static List<String[]> getFtList(){
		String site = "http://ft.nanhaicorp.com";
		String tenant = "131061";
		List<String[]> rList = new ArrayList<>();
		rList.add(new String[]{tenant,site,"95","20"});//集团动态
		rList.add(new String[]{tenant,site,"179","24"});//大地影院
		rList.add(new String[]{tenant,site,"362","25"});//辰星科技
		rList.add(new String[]{tenant,site,"365","27"});//时代广告
		rList.add(new String[]{tenant,site,"368","34"});//半岛城邦
		rList.add(new String[]{tenant,site,"371","35"});//自由人花园
		rList.add(new String[]{tenant,site,"176","32"});//中企动力
		rList.add(new String[]{tenant,site,"182","33"});//新网
		return rList;
	}
	
	
	
	/**
	 * 简体站
	 */
	public static List<String[]> getJtList(){
		String site = "http://www.nanhaicorp.com";
		String tenant = "116303";
		List<String[]> rList = new ArrayList<>();
		rList.add(new String[]{tenant,site,"95","20"});//集团动态
		rList.add(new String[]{tenant,site,"179","24"});//大地影院
		rList.add(new String[]{tenant,site,"266","25"});//辰星科技
		rList.add(new String[]{tenant,site,"269","27"});//时代广告
		rList.add(new String[]{tenant,site,"272","34"});//半岛城邦
		rList.add(new String[]{tenant,site,"275","35"});//自由人花园
		rList.add(new String[]{tenant,site,"176","32"});//中企动力
		rList.add(new String[]{tenant,site,"182","33"});//新网
		return rList;
	}
	
	
	
	public static void run() throws ParseException{
		try {
			int totalPage = 15;
			String url = webSite + "/news_list/newsCategoryId="+newsCategoryId+"&FrontNews_list01-1435803560151_pageNo={page}&FrontNews_list01-1435803560151_pageSize=10.html";
			Set<Map<String, String>> urls = new HashSet<Map<String, String>>();
			//分页抓取详情链接
			for (int i = 0; i < totalPage; i++) {
				String rUrl = url.replace("{page}", String.valueOf(i+1));
				Document document = Jsoup.connect(rUrl).get();
				
				Elements elements = document.select("li[class='title'] a");
				
				for (Element element : elements) {
					Map<String, String> pMap = new HashMap<String, String>();
					pMap.put("detailUrl", webSite + element.attr("href"));
					pMap.put("summary", element.parent().parent().parent().parent().parent().select(".summary p").text().split("\\.\\.\\.")[0] + "...");
					
					urls.add(pMap);
				}
			}
			
			//抓取详情数据
			if(urls.size() > 0){
				int i = 0;
				for (Map<String, String> map : urls) {
					System.out.println("i**********************************:"+i);
					if(i == 8){
						System.out.println(7878);
					}
					Document document = Jsoup.connect(map.get("detailUrl")).get();
					//标题
					Element tE = document.select("h2").first();
					String title = null;
					if(tE != null){
						title = tE.html();
					}
							
					
					//来源
					Element sE = document.select("span[class='source']").first();
					String source = null;
					if(sE != null){
						//中文站
						source = sE.html().substring(12, sE.html().length());
						
						//英文站
						//source = sE.html().substring(16, sE.html().length());
					}
					//日期
					Element dE = document.select("span[class='date']").first();
					String date = null;
					if(dE != null){
						//中文站
						date = dE.html().substring(12, dE.html().length());
						
						//英文站
						//date = dE.html().substring(14, dE.html().length());
					}
					//详情
					Element cE = document.getElementById("infoContent");
					String content = null;
					if(cE != null){
						//替换图片超链接
						Elements imgs = cE.getElementsByTag("img");
						for (Element img : imgs) {
							img.attr("src", webSite +img.attr("src"));
						}
						content = cE.html();
						//TODO 上传图片
					}
					i++;
					System.out.println("标题："+title+"===来源:"+source+"====日期:"+date);
					
					
					//保存数据
					if(title != null){
						 Newest news = new Newest(columId);
						 news.setTitle(title);
                         //news.setKeyWords(title);
                         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                         Date da = format.parse(date);
                         news.setPubTime(String.valueOf(da.getTime()));
                         //中文站
                         //SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
                         //英文站
                         SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                         String format2 = format1.format(da);
                         news.setSubTitle(format2);
                         news.setContent(content);
                         news.setSummary(map.get("summary"));
                         List<ContentFieldDTO> contentFields = getContentFields(news);
                         news.setContentFieldDTOs(contentFields);
                         
                         //请求路径
                         //String postUrl = "http://1812255010.pool1-gcsite.make.yun300.cn/manage/addContent?columnId=32&appId=200&tenantId=126894";
                         String postUrl = "http://1811025005.pool1-gcsite.make.yun300.cn/manager/originalForward.do?url=%2Fapi%2Fcmscontent%2FappContentService%2Finsert%3FappId%3D200%26tenantId%3D"+tenantId+"&tenantId="+tenantId+"&authPermission="+columId+":27";
                         
                         Map<String, String> headers = getRequestHeaders();
                         String ss = HttpClientUtils.doPost(postUrl, JSONObject.toJSONString(news), headers);
                         System.out.println(ss);
					}
					
				}
			}
			
			System.out.println(urls.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	 public static Map<String, String> getRequestHeaders() {
	        Map<String, String> headers = new HashMap<>();
	        headers.put("Accept", "application/json, text/plain, */*");
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Cache-Control", "no-cache");
			headers.put("Content-Type", "application/json; charset=UTF-8");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
			headers.put("Pragma", "no-cache");

			headers.put("Cookie", session);
			headers.put("Host", "1811025005.pool1-gcsite.make.yun300.cn");
			headers.put("Origin", "http://1811025005.pool1-gcsite.make.yun300.cn");
			headers.put("Referer", "http://1811025005.pool1-gcsite.make.yun300.cn/manage/addContent?columnId="+columId+"&appId=200");
	        return headers;
	    }
	
	public static List<ContentFieldDTO> getContentFields(Newest newest) {
		List<ContentFieldDTO> list = new ArrayList<>();
		ContentFieldDTO dto = null;

		//dto = new ContentFieldDTO(302, "TITLE", 1);
		dto = new ContentFieldDTO(932, "TITLE", 1);
		dto.setFieldValue(newest.getTitle());
		list.add(dto);

		//dto = new ContentFieldDTO(323, "SUMMARY", 1);
		dto = new ContentFieldDTO(953, "SUMMARY", 2);
		dto.setFieldValue(newest.getSummary());
		list.add(dto);
		
		//dto = new ContentFieldDTO(323, "SUBTITLE", 2);
		dto = new ContentFieldDTO(935, "SUBTITLE", 1);
		dto.setFieldValue(newest.getSubTitle());
		list.add(dto);

		//dto = new ContentFieldDTO(317, "PC_TEXTCONTENT", 3);
		dto = new ContentFieldDTO(947, "PC_TEXTCONTENT", 3);
		dto.setFieldValue(newest.getContent());
		list.add(dto);

		dto = new ContentFieldDTO(314, "THUMBNAIL", 11);
		dto.setFilePath(newest.getThumbnail());
		list.add(dto);
		return list;
	}
}
