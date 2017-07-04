package com.example.rss.rssapp.RssParse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by mrezapue on 3/7/17.
 */

@Root(name="enclosure", strict = false)
public class Content {

    @Attribute(name="url", required = false)
    private String url;

    public Content() {
    }

    public Content(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
