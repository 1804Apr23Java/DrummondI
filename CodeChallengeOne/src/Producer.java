import java.util.LinkedList;
import java.util.Random;

public class Producer implements Runnable {
	private LinkedList<String> sharedResource;
	private final int MAX_LENGTH;
	private Lock lock;
	
	public Producer(LinkedList<String> sharedResource, final int MAX_LENGTH, Lock lock) {
		this.sharedResource = sharedResource;
		this.MAX_LENGTH = MAX_LENGTH;
		this.lock = lock;
	}
	
	public void run() {
		Random r = new Random();
		
		while(true) {
			int amount = r.nextInt(MAX_LENGTH + 1);
			
			if(lock.getLock()) {
				int spaceLeft = (MAX_LENGTH - sharedResource.size());
				int amountToAdd = (amount < spaceLeft) ? amount : spaceLeft;
				
				for(int i = 0; i < amountToAdd; i++) {
					String s = new String(new Integer(i).toString());
					sharedResource.add(s);
				}
				
				System.out.println("Producer placed " + amountToAdd + " strings.");
				
				lock.releaseLock();
			}
			
			int sleepTime = r.nextInt(5000);
			try {
				Thread.sleep(sleepTime);
			} catch(InterruptedException e) {
				
			}
		}
	}
}
