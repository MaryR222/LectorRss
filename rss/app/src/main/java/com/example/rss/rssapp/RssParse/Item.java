package com.example.rss.rssapp.RssParse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by mrezapue on 3/7/17.
 *
 *  Class representing the <Item> tag of the feed
 */
@Root (name = "item", strict = false)
public class Item {


    @Element(name = "title")
    private String title;

    @Element (name = "description")
    private String description;

    @Element(name="link")
    private String link;

    @Element (name = "enclosure" ,required = false)
    private Content content;


    public Item() {
    }

    public Item(String title, String descripcion, String link, Content content) {
        this.title = title;
        this.description = descripcion;
        this.link = link;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String Description() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public Content getContent() {
        return content;
    }

}
