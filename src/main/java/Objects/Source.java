package Objects;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Source implements Serializable{
    private HashSet<String> words, links;
    private String title, url;
    private LocalDate timeStamp;
    private transient boolean isVisited;

    public Source(String title, String url, LocalDate update){
        this.title = title;
        this.url = url;
        this.timeStamp = update;
    }

    public boolean needsUpdate(){
        return timeStamp.compareTo(LocalDate.now()) < 0;
    }

    public void process(Document document){
        words = new HashSet<>();
        links = new HashSet<>();
		words.addAll(Arrays.asList((document.text().split("[\\s\\p{Punct}\\r\\n]+"))));
		links.addAll(document.body().getElementsByAttribute("href").eachAttr("href"));
	}

}
