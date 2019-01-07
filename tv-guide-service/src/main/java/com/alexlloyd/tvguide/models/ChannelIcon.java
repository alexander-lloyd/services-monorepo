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
    private Integer id;

    @JacksonXmlProperty
    private String src;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public static class Builder {
        private final ChannelIcon channelIcon;

        public Builder() {
            channelIcon = new ChannelIcon();
        }

        public Builder setId(Integer id) {
            this.channelIcon.setId(id);
            return this;
        }

        public Builder setSrc(String src) {
            this.channelIcon.setSrc(src);
            return this;
        }

        public ChannelIcon build() {
            return this.channelIcon;
        }
    }
}
