import Crawler.Crawler;

import java.io.File;
import java.io.FileNotFoundException;


public class Main {
	public static void main(String args[]) {
		Crawler crawler = new Crawler();
		File seed = new File("seed.txt");
		try {
			crawler.add(seed);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			crawler.crawl(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
	}
}
