import java.util.List;
import java.util.LinkedList;

public class Driver {

	public static void main(String[] args) {
		LinkedList<String> sharedResource = new LinkedList<>();
		Lock lock = new Lock();
		final int MAX_LEN = 5000;
		
		Producer p = new Producer(sharedResource, MAX_LEN, lock);
		Consumer c = new Consumer(sharedResource, MAX_LEN, lock);
	
		(new Thread(new Producer(sharedResource, MAX_LEN, lock))).start();
		(new Thread(new Consumer(sharedResource, MAX_LEN, lock))).start();
	}
}
