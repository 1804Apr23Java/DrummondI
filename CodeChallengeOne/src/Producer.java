import java.util.LinkedList;
import java.util.Random;

public class Producer<T> implements Runnable {
	private LinkedList<T> sharedResource;
	private final int MAX_LENGTH;
	private Lock lock;
	
	public Producer(LinkedList<T> sharedResource, final int MAX_LENGTH, Lock lock) {
		this.sharedResource = sharedResource;
		this.MAX_LENGTH = MAX_LENGTH;
		this.lock = lock;
	}
	
	public void run() {
		Random r = new Random();
		
		while(true) {
			
		}
	}
}
