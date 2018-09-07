package Crawler;

import Objects.Source;
import Utils.BPlusTree.Tree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static Utils.Constants.CRAWLER_CHUNK_SIZE;

public class Crawler {

	static final int NTHREADS = Runtime.getRuntime().availableProcessors();
	Tree tree;
	ArrayList<String> urlPool;

	public Crawler() {
		urlPool = new ArrayList<>();
		tree = new Tree();
	}

	public void add(File seed) throws FileNotFoundException {
		Scanner scanner = new Scanner(seed);
		scanner.useDelimiter(Pattern.compile("[\\r\\s]"));
		while (scanner.hasNext()) {
			String s = scanner.next();
			if (!urlPool.contains(s) && !s.isEmpty()) urlPool.add(s);
		}
	}

	/**
	 * Add url's from an array of strings
	 *
	 * @param urls array containing url's as strings
	 */
	public void add(String[] urls) {
		for (String s : urls) {
			if (!urlPool.contains(s)) urlPool.add(s);
		}
	}

	public void crawl(int cap) throws InterruptedException {



		int NTHREADS = Runtime.getRuntime().availableProcessors() - 1;

		ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

		while ((tree.getSize() < cap) && !urlPool.isEmpty()) {

			int chunkSize = (cap - tree.getSize() > CRAWLER_CHUNK_SIZE) ? CRAWLER_CHUNK_SIZE : cap - tree.getSize();
			List<Future<Source>> futures = new ArrayList<>();

			Object[] workingStrings = Arrays.copyOfRange(urlPool.toArray(), 0, chunkSize);
			urlPool.removeAll(Arrays.asList(workingStrings));

			ConcurrentLinkedDeque<Document> workingDocs = Arrays.stream(workingStrings).parallel()
					.filter(Objects::nonNull)
					.map(s -> {
						Document d;
						try {
							d = Jsoup.connect((String) s).timeout(2000).get();
						} catch (IOException e) {
							d = null;
						}
						return d;
					}
			).filter(Objects::nonNull).
							collect(Collectors.toCollection(ConcurrentLinkedDeque::new));

			if (workingDocs == null){
				System.out.println();
			}

			for (Document doc : workingDocs) {
				Callable<Source> callable = new CrawlerCallable(doc);
				Future<Source> submit = executor.submit(callable);
				futures.add(submit);
			}


//		while ((tree.getSize() < cap) || !urlPool.isEmpty()) {
//			Callable<Source> callable = new CrawlerCallable(this);
//			Future<Source> submit = executor.submit(callable);
//			futures.add(submit);
//		}
//		for (int i = 0; i < NTHREADS; ++i){
//			executor.execute(new Crawler.CrawlerCallable(this));
//		}
			Source source;


			for (Future<Source> f : futures) {
				try {
					source = f.get();
					tree.add(source.getUrl(), source);
					urlPool.addAll(source.getLinks());
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
	}


}
