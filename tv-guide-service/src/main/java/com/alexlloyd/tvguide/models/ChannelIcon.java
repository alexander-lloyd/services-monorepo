package com.alexlloyd.tvguide.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "icon")
public class ChannelIcon {
    @JacksonXmlProperty
    private String src;

    public String getSrc() {
        return src;
    }
}
