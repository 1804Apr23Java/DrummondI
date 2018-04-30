import java.util.List;

public class Producer<T> implements Runnable {
	private List<T> sharedResource;
	private final int MAX_LENGTH;
	
	public Producer(List<T> sharedResource, final int MAX_LENGTH) {
		this.sharedResource = sharedResource;
		this.MAX_LENGTH = MAX_LENGTH;
	}
	
	public void run() {
		
	}
}
