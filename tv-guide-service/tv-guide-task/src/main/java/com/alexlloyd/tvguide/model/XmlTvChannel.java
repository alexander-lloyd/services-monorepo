package com.alexlloyd.tvguide.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "channel")
public class XmlTvChannel {
    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private String channelId;

    @JacksonXmlProperty(localName = "display-name")
    private String name;

    @JacksonXmlProperty(localName = "icon")
    private XmlTvChannelIcon icon;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XmlTvChannelIcon getIcon() {
        return icon;
    }

    public void setIcon(XmlTvChannelIcon icon) {
        this.icon = icon;
    }

    public static class Builder {
        private XmlTvChannel channel;

        public Builder() {
            this.channel = new XmlTvChannel();
        }

        public Builder setId(String id) {
            this.channel.setChannelId(id);
            return this;
        }

        public Builder setName(String name) {
            this.channel.setName(name);
            return this;
        }

        public Builder setIcon(XmlTvChannelIcon icon) {
            this.channel.setIcon(icon);
            return this;
        }

        public XmlTvChannel build() {
            return this.channel;
        }
    }
}
