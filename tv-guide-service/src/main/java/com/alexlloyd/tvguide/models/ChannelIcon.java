package com.alexlloyd.tvguide.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@Entity
@JacksonXmlRootElement(localName = "icon")
public class ChannelIcon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JacksonXmlProperty
    private String src;

    public String getSrc() {
        return src;
    }
}
