import java.util.List;

public class Producer<T> implements Runnable {
	private List<T> sharedResource;
	
	public Producer(List<T> sharedResource) {
		this.sharedResource = sharedResource;
	}
	
	public void run() {
		
	}
}
