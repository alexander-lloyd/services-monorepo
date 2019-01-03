package com.alexlloyd.tvguide.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "channel")
public class Channel {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "display-name")
    private String name;

    @JacksonXmlProperty(localName = "icon")
    private ChannelIcon icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChannelIcon getIcon() {
        return icon;
    }

    public void setIcon(ChannelIcon icon) {
        this.icon = icon;
    }

    public static class Builder {
        private Channel channel;

        public Builder() {
            this.channel = new Channel();
        }

        public Builder setId(String id) {
            this.channel.setId(id);
            return this;
        }

        public Builder setName(String name) {
            this.channel.setName(name);
            return this;
        }

        public Builder setIcon(ChannelIcon icon) {
            this.channel.setIcon(icon);
            return this;
        }

        public Channel build() {
            return this.channel;
        }
    }
}
