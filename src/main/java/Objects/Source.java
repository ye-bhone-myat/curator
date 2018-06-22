package Objects;

import Utils.HashTable;

import java.io.Serializable;
import java.time.LocalDate;

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

}
