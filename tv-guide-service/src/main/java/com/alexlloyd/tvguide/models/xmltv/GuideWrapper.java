package com.alexlloyd.tvguide.models.xmltv;

import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "tv")
@SuppressWarnings("unused")
public class GuideWrapper {
    @JacksonXmlProperty(localName = "generator-info-name", isAttribute = true)
    private String generatorInfoName;

    @JacksonXmlProperty(localName = "source-info-name", isAttribute = true)
    private String sourceInfoName;

    private Collection<XmlTvChannel> channels;
    private Collection<XmlTvProgramme> programmes;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "channel")
    public Collection<XmlTvChannel> getChannels() {
        return this.channels;
    }

    public void setChannels(Collection<XmlTvChannel> channels) {
        this.channels = channels;
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "programme")
    public Collection<XmlTvProgramme> getProgrammes() {
        return this.programmes;
    }

    public void setProgrammes(Collection<XmlTvProgramme> programmes) {
        this.programmes = programmes;
    }
}
