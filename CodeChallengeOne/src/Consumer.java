import java.util.List;

public class Consumer<T> implements Runnable {
	private List<T> sharedResource;
	private final int MAX_LENGTH;
	
	public Consumer(List<T> sharedResource, final int MAX_LENGTH) {
		this.sharedResource = sharedResource;
		this.MAX_LENGTH = MAX_LENGTH;
	}
	
	public void run() {
		
	}
}
