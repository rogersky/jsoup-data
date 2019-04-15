package cn.cebest.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class Newest {
	/** 发布时间 */
	private String pubTime;
	/** 标题 */
	private String title;
	private String attachment;
	
	private String subTitle;
	/** 缩略图 */
	private String thumbnail;
	
	/** 摘要 */
	private String summary;
	/** 详情 */
	private String content;
	/** 来源*/
	private String source;
	private List<ContentFieldDTO> contentFieldDTOs;
	private String columnId = "95";
	//审核状态(99999表示通过)
	private String auditState = "99999";
	private boolean pcShow = true;
	
	
	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public boolean isPcShow() {
		return pcShow;
	}

	public void setPcShow(boolean pcShow) {
		this.pcShow = pcShow;
	}

	public Newest(String columnId){
		this.columnId = columnId;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<ContentFieldDTO> getContentFieldDTOs() {
		return contentFieldDTOs;
	}
	public void setContentFieldDTOs(List<ContentFieldDTO> contentFieldDTOs) {
		this.contentFieldDTOs = contentFieldDTOs;
	}
	
	public List<ContentFieldDTO> getContentFields(Newest newest,ContentFieldDTO fileDto,ContentFieldDTO picDto) {
		List<ContentFieldDTO> list = new ArrayList<>();
		ContentFieldDTO dto = null;

		dto = new ContentFieldDTO(932, "TITLE", 1);
		dto.setFieldValue(newest.getTitle());
		list.add(dto);

		dto = new ContentFieldDTO(953, "SUMMARY", 2);
		dto.setFieldValue(newest.getSummary());
		list.add(dto);
		
		dto = new ContentFieldDTO(821, "SUBTITLE", 1);
		dto.setFieldValue(newest.getSubTitle());
		list.add(dto);
		//时间
		if(StringUtils.isNotEmpty(this.pubTime)){
			dto = new ContentFieldDTO(3278, "TIME", 9);
			dto.setFieldValue(newest.getPubTime());
			list.add(dto);
		}
		//文件操作
		if(fileDto != null){
			list.add(fileDto);
		}
		//缩略图dto
		if(picDto != null){
			list.add(picDto);
		}
		dto = new ContentFieldDTO(935, "SOURCE", 1);
		dto.setFieldValue(newest.getSource());
		list.add(dto);
		
		dto = new ContentFieldDTO(947, "PC_TEXTCONTENT", 3);
		dto.setFieldValue(newest.getContent());
		list.add(dto);

		return list;
	}
	
	
	@Override
	public String toString() {
		return "Newest [pubTime=" + pubTime + ", title=" + title + ", thumbnail=" + thumbnail + ", summary=" + summary + ", content=" + content + "]";
	}
}
