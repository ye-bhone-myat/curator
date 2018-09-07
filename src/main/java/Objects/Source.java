package Objects;

import Utils.HashTable;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Source implements Serializable{
    private HashTable<String> words, links;
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
        words = new HashTable<>();
        links = new HashTable<>();
		Arrays.asList((document.text().split("[\\s\\p{Punct}\\r\\n]+"))).forEach( s -> {
			if (s.matches("[a-zA-Z0-9]{4,}")) {
				if (s.matches(".*[0-9]+.*")) {
					s = "generic number";
				}
				s = s.toLowerCase();
				words.add(s);
			}
		});
		document.body().getElementsByAttribute("href").eachAttr("href").forEach(l ->{
			if (l.startsWith("http")){
				links.add(l);
			}
		});
	}

    public String getUrl() {
                return url;
    }

    public String getTitle(){
    	return title;
	}

	public List<String> getLinks(){
    	return links.getList();
	}
}
