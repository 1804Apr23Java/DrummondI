import java.util.LinkedList;
import java.util.Random;

public class Consumer<T> implements Runnable {
	private LinkedList<T> sharedResource;
	private final int MAX_LENGTH;
	private Lock lock;
	
	public Consumer(LinkedList<T> sharedResource, final int MAX_LENGTH, Lock lock) {
		this.sharedResource = sharedResource;
		this.MAX_LENGTH = MAX_LENGTH;
		this.lock = lock;
	}
	
	public void run() {
		Random r = new Random();
		
		while(true) {
			int amount = r.nextInt(this.MAX_LENGTH + 1);
			
			if(lock.getLock()) {
				int amountAdded = amount < sharedResource.size() ? amount : sharedResource.size();
				
				for(int i = 0; i < amountAdded; i++ ) {
					sharedResource.pop();
				}
				
				lock.releaseLock();
			}
		}
	}
}
