package com.alexlloyd.tvguide.models;

import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "tv")
public class GuideWrapper {
    private Collection<Channel> channels;
    private Collection<Programme> programmes;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "channel")
    public Collection<Channel> getChannels() {
        return this.channels;
    }

    public void setChannels(Collection<Channel> channels) {
        this.channels = channels;
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "programme")
    public Collection<Programme> getProgrammes() {
        return this.programmes;
    }

    public void setProgrammes(Collection<Programme> programmes) {
        this.programmes = programmes;
    }
}
