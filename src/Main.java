import java.io.IOException;

import com.sprogroup.filequeue.FileQueue;

public class Main {
	
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		System.out.println("Start: " + start);
		FileQueue q = new FileQueue("./data.txt");
		for (int i = 0; i < 100; ++i) {
			q.enqueue(i + "");
		}
		while (!q.isEmpty()) {
			System.out.println("top: " + q.top());
			q.dequeue();
		}
		System.out.println("Total: " + (System.currentTimeMillis() - start));
	}
}
