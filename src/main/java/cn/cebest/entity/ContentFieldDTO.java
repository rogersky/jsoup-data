package cn.cebest.entity;

public class ContentFieldDTO {
	private int fieldId;
	private String fieldTag;
	private int fieldType;
	private String fieldValue;
	private int fileId;
	private String fileName;
	private String filePath;
	private int fileType;

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldTag() {
		return fieldTag;
	}

	public void setFieldTag(String fieldTag) {
		this.fieldTag = fieldTag;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public ContentFieldDTO(int fieldId, String fieldTag, int fieldType) {
		super();
		this.fieldId = fieldId;
		this.fieldTag = fieldTag;
		this.fieldType = fieldType;
	}

	public ContentFieldDTO() {
		super();
	}
	
	

}
