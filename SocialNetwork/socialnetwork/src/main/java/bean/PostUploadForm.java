package bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PostUploadForm extends PostBEAN{
	
	public PostUploadForm() {
	}
	
	private CommonsMultipartFile[] fileDatas;

	public CommonsMultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(CommonsMultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}
	
	
}
