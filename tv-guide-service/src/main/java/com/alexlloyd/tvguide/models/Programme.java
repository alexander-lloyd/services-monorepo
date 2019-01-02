package com.alexlloyd.tvguide.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Programme {
    private String title;

    @JacksonXmlProperty(localName = "desc")
    private String description;

    @JacksonXmlProperty(localName = "start", isAttribute = true)
    private ZonedDateTime startTime;

    @JacksonXmlProperty(localName = "stop", isAttribute = true)
    private ZonedDateTime stopTime;

    @JacksonXmlProperty(localName = "channel", isAttribute = true)
    private String channelId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(ZonedDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
