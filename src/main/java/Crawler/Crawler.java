package Crawler;

import Objects.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Crawler {

	public void add(File seed) {

	}

	public void add(String url) {

	}

	public void add() {

	}

	private Source getSource(String url) throws IOException {
		Document ooi;
		Source source;

		ooi = Jsoup.connect(url).timeout(1500).get();

		if (ooi.body() != null) {
			source = new Source(ooi.title().length() > 0 ? ooi.title() : "Title Unavailable",
					ooi.location(), LocalDate.now());
			source.process(ooi);
		} else {
			source = null;
		}

		return source;
	}

}
