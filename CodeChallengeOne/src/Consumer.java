import java.util.List;

public class Consumer<T> implements Runnable {
	private List<T> sharedResource;
	
	public Consumer(List<T> sharedResource) {
		this.sharedResource = sharedResource;
	}
	
	public void run() {
		
	}
}
