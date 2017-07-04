package com.example.rss.rssapp.RssParse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by mrezapue on 3/7/17.
 *
 * Class represent element rss
 */


@Root (name = "rss", strict = false)
public class Rss {

    @Element
    private Channel channel;

    public Rss(){

    }

    public  Rss (Channel channel){
        this.channel = channel;
    }

    public Channel getChannel(){
        return channel;
    }
}
