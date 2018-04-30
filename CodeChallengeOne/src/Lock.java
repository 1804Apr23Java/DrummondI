
public class Lock {
	private boolean lock;
	
	public Lock() {
		this.lock = false;
	}
	
	public boolean getLock() {
		if(this.lock)  { 
			return false;
		} else {
			this.lock = true;
			return true;
		}
	}
	
	public void releaseLock() {
		this.lock = false;
	}
}
