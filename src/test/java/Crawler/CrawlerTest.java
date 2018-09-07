package Crawler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CrawlerTest {

	Crawler crawler;

	@AfterEach
	void breakdown() {
		crawler = new Crawler();
	}

	@Test
	void CrawlerShouldAddFromFile() {
		crawler = new Crawler();
		Crawler spy = spy(crawler);
		File file = new File("test.txt");
		try {
			spy.add(file);
		} catch (FileNotFoundException e) {
			//ignored
		}
//		verify(spy, times(3)).add(anyString());
//		assertAll(
//				() -> assertTrue(spy.urlPool.contains("a")),
//				() -> assertTrue(spy.urlPool.contains("b")),
//				() -> assertTrue(spy.urlPool.contains("c"))
//		);
	}

	@Test
	void CrawlerShouldAddFromArray() {
		crawler = new Crawler();
		Crawler spy = spy(crawler);
		String[] test = {"a", "b", "c"};
		spy.add(test);
//		verify(spy, times(3)).add(anyString());
//		assertAll(
//				() -> assertTrue(spy.urlPool.contains("a")),
//				() -> assertTrue(spy.urlPool.contains("b")),
//				() -> assertTrue(spy.urlPool.contains("c"))
//		);
	}


}
