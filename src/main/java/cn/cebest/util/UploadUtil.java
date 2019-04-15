package cn.cebest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpException;

import com.alibaba.fastjson.JSONObject;

import cn.cebest.entity.Newest;

public class UploadUtil {
	private String columnId;
	private String tenantId;
	private String session;
	
	//没有http,项目后台域名
	private String webSite;
	
	/**
	 * 
	 * @param tenantId:
	 * @param columnId:保存内存所属栏目id
	 * @param webSite:域名
	 * @param session：cookie
	 */
	public UploadUtil(String tenantId, String columnId, String webSite,String session){
		this.tenantId = tenantId;
		this.columnId = columnId; 
		this.webSite = webSite;
		this.session = session;
	}
	
	/**
	 * 
	 * @param fileUrl 被上传文件的文件名
	 * @throws Exception
	 * @return 上传文件后返回的结果
	 */
	public Map<String, Object> uplaodFile(String fileUrl) throws HttpException, IOException {
		String requestUrl = "http://"+webSite+"/api/upload/uploadService/uploadFile?tenantId="+tenantId+"&resumableChunkNumber=1&resumableChunkSize=104857600&resumableTotalSize={size}&resumableIdentifier={size}-{name}&resumableFilename={_name}&resumableRelativePath={_name}&resumableTotalChunks=1";
		String url = "F:\\img\\" + fileUrl;
		File file = new File(url);
		Long size = file.length();
		String fileName = file.getName();
		requestUrl = requestUrl.replace("{size}", size.toString());
		//requestUrl = requestUrl.replace("{_name}", fileName);
		requestUrl = requestUrl.replace("{_name}", URLEncoder.encode(fileName,"utf-8"));
		String name =  fileName.split("\\.")[0]+fileName.split("\\.")[1];
		//requestUrl = requestUrl.replace("{name}",name);
		requestUrl = requestUrl.replace("{name}",URLEncoder.encode(name,"utf-8"));
		//requestUrl = requestUrl.replace(" ", "-");
        HttpClient client = new HttpClient();
        //String encode = URLEncoder.encode(requestUrl, "utf-8");
        PostMethod post = new PostMethod(requestUrl);
        FilePart fp;
        try {
        	
        	post.addRequestHeader("Accept", "application/octet-stream, text/plain, */*");
			post.addRequestHeader("Accept-Encoding", "gzip, deflate");
			post.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.9");
			post.addRequestHeader("Cache-Control", "no-cache");
			post.addRequestHeader("Content-Type", "application/octet-stream; charset=UTF-8");
			post.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
			post.addRequestHeader("Pragma", "no-cache");

			post.addRequestHeader("Cookie", session);
			post.addRequestHeader("Host", webSite);
			post.addRequestHeader("Origin", "http://" + webSite);
			post.addRequestHeader("Referer", requestUrl);
        	
            fp = new FilePart("formFile", new File(url));
            
            Part[] parts = {fp};
            MultipartRequestEntity entity = new MultipartRequestEntity(parts, new HttpMethodParams());
            post.setRequestEntity(entity);
            client.executeMethod(post); 
            String result= post.getResponseBodyAsString();//释放连接，以免超过服务器负荷 post.releaseConnection();
            System.out.println(result);//返回
            
            //解析返回结果
            JSONObject jsonObject = JSONObject.parseObject(result);
            @SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) jsonObject.get("data");
            return map;
        } catch (FileNotFoundException e) {
        	System.out.println("上传文件失败=============:"+fileUrl);
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 
	 * @param fileUrl 被上传图片的文件名
	 * @throws Exception
	 * @return 上传文件后返回的结果
	 */
	public Map<String, Object> uplaodImg(String fileUrl) throws HttpException, IOException {
		//String requestUrl = "http://"+webSite+"/api/upload/uploadService/uploadFile?tenantId="+tenantId+"&resumableChunkNumber=1&resumableChunkSize=104857600&resumableTotalSize={size}&resumableIdentifier={size}-{name}&resumableFilename={_name}&resumableRelativePath={_name}&resumableTotalChunks=1";
		String requestUrl = "http://"+webSite+"/api/imageRepository/imageRepositoryService/uploadImage?appId=0&name=1.jpg&id=undefined&tenantId=131061&authPermission=43";
		//String requestUrl = "http://localhost:8080/index";
		String url = "F:\\img\\" + fileUrl;
		File file = new File(url);
		Long size = file.length();
		String fileName = file.getName();
		requestUrl = requestUrl.replace("{size}", size.toString());
		//requestUrl = requestUrl.replace("{_name}", fileName);
		requestUrl = requestUrl.replace("{_name}", URLEncoder.encode(fileName,"utf-8"));
		String name =  fileName.split("\\.")[0]+fileName.split("\\.")[1];
		//requestUrl = requestUrl.replace("{name}",name);
		requestUrl = requestUrl.replace("{name}",URLEncoder.encode(name,"utf-8"));
		//requestUrl = requestUrl.replace(" ", "-");
        HttpClient client = new HttpClient();
        //String encode = URLEncoder.encode(requestUrl, "utf-8");
        PostMethod post = new PostMethod(requestUrl);
        FilePart fp;
        try {
        	
        	post.addRequestHeader("Accept", "application/json, text/plain, */*");
			post.addRequestHeader("Accept-Encoding", "gzip, deflate");
			post.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
			post.addRequestHeader("Connection", "keep-alive");
			post.addRequestHeader("Content-Type", "image/png");
			post.addRequestHeader("Cookie", session);
			post.addRequestHeader("Content-Length", size+"");
			post.addRequestHeader("Host", webSite);
			post.addRequestHeader("Origin", "http://" + webSite);
			post.addRequestHeader("Referer", requestUrl);
			post.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
			post.addRequestHeader("X-Requested-With", "XMLHttpRequest");
			
			post.addRequestHeader("Cache-Control", "no-cache");
			post.addRequestHeader("Pragma", "no-cache");

        	
            fp = new FilePart("formFile", file);
            
            Part[] parts = {fp};
            MultipartRequestEntity entity = new MultipartRequestEntity(parts, new HttpMethodParams());
            post.setRequestEntity(entity);
            client.executeMethod(post); 
            String result= post.getResponseBodyAsString();//释放连接，以免超过服务器负荷 post.releaseConnection();
            System.out.println(result);//返回
            
            //解析返回结果
            JSONObject jsonObject = JSONObject.parseObject(result);
            @SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) jsonObject.get("data");
            return map;
        } catch (FileNotFoundException e) {
        	System.out.println("上传文件失败=============:"+fileUrl);
            e.printStackTrace();
        }
        return null;
	}
	
	
	
	
	/**
	 * 保存内容header
	 */
	public Map<String, String> getRequestHeaders() {
	        Map<String, String> headers = new HashMap<>();
	        headers.put("Accept", "application/json, text/plain, */*");
			headers.put("Accept-Encoding", "gzip, deflate");
			headers.put("Accept-Language", "zh-CN,zh;q=0.9");
			headers.put("Cache-Control", "no-cache");
			headers.put("Content-Type", "application/json; charset=UTF-8");
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
			headers.put("Pragma", "no-cache");

			headers.put("Cookie", session);
			headers.put("Host", webSite);
			headers.put("Origin", "http://" + webSite);
			headers.put("Referer", "http://"+webSite+"/manage/addContent?columnId="+columnId+"&appId=200");
	        return headers;
	  }
	 
	 /**
	  * 保存内容
	  * @param saveUrl：保存内容的url
	  * @param news：参数试题
	  * @throws Exception
	  */
	 public void saveContent(Newest news) throws Exception{
		String saveUrl = "http://"+webSite+"/manager/originalForward.do?url=%2Fapi%2Fcmscontent%2FappContentService%2Finsert%3FappId%3D200%26tenantId%3D"+tenantId+"&tenantId="+tenantId+"&authPermission="+columnId+":27";
        
        String ss = HttpClientUtils.doPost(saveUrl, JSONObject.toJSONString(news), this.getRequestHeaders());
        System.out.println(ss);
	 }
	 
}
