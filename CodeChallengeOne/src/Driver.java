import java.util.List;
import java.util.LinkedList;

public class Driver {

	public static void main(String[] args) {
		LinkedList<String> sharedResource = new LinkedList<>();
		Lock lock = new Lock();
		final int MAX_LEN = 100;
		
		Producer p = new Producer(sharedResource, MAX_LEN, lock);
		Consumer c = new Consumer(sharedResource, MAX_LEN, lock);
		System.out.println("Done");
	}
}
