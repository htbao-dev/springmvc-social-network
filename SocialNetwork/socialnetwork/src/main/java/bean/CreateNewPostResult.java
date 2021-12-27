package bean;

import java.io.File;
import java.util.List;

public class CreateNewPostResult {
	private int status;
	private long postID;
	private List<File> uploadedFiles;
	public CreateNewPostResult(int status, long postID, List<File> uploadedFiles) {
		super();
		this.status = status;
		this.postID = postID;
		this.uploadedFiles = uploadedFiles;
	}

	public int getStatus() {
		return status;
	}

	public long getPostID() {
		return postID;
	}
	
	public List<File> getUploadedFiles(){
		return List.copyOf(uploadedFiles);
	}
	
}
