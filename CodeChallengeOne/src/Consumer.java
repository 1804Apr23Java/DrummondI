import java.util.LinkedList;
import java.util.Random;

public class Consumer implements Runnable {
	private LinkedList<String> sharedResource;
	private final int MAX_LENGTH;
	private Lock lock;
	
	public Consumer(LinkedList<String> sharedResource, final int MAX_LENGTH, Lock lock) {
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
				System.out.println("Comsumer consumed " + amountAdded + " strings.");
				
				lock.releaseLock();
			}
			
			int sleepTime = r.nextInt(500);
			try {
				Thread.sleep(sleepTime);
			} catch(InterruptedException e) {
				
			}
		}
	}
}
