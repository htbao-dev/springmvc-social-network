package bean;

public class Result {
	private long id;
	private int status;
	public Result(long id, int status) {
		super();
		this.id = id;
		this.status = status;
	}
	public long getID() {
		return id;
	}
	public void setID(long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
