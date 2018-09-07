package Crawler;

import Objects.Source;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.util.concurrent.Callable;

class CrawlerCallable implements Callable<Source> {

	Document document;

	CrawlerCallable(Document doc) {
		this.document = doc;
	}

	@Override
	public Source call() {

		Source source = new Source(document.title().length() > 0 ? document.title() : "Title Unavailable",
				document.location(), LocalDate.now());
		source.process(document);
		return source;

//		Document ooi;
//		Source source;
//
//		try {
//			if (!parent.urlPool.isEmpty()) {
//				ooi = Jsoup.connect().
//			} else {
//				return null;
//			}
//		} catch (SocketTimeoutException e){
//			//TODO: log this too
//			e.printStackTrace();
//			return null;
//		} catch (IOException e) {
//			//TODO: log url and cause of exception to external file
//			e.printStackTrace();
//			return null;
//		}
//
//		if (ooi.body() != null) {
//			//TODO: make pattern matcher to extract "*.com" from ooi url for use when title is unavailable
//			source = new Source(ooi.title().length() > 0 ? ooi.title() : "Title Unavailable",
//					ooi.location(), LocalDate.now());
//			source.process(ooi);
//			return source;
//		}
//		return null;
	}
}
