package com.example.rss.rssapp.RssParse;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by mrezapue on 3/7/17.
 */

@Root(name = "channel", strict = false)
public class Channel {


    @ElementList(inline = true  ,required=false)
    private List<Item> items;

    public Channel() {
    }

    public Channel(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
