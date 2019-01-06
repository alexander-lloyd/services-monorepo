package com.alexlloyd.tvguide.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@Entity
@JacksonXmlRootElement(localName = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private String channelId;

    @JacksonXmlProperty(localName = "display-name")
    private String name;

    @JacksonXmlProperty(localName = "icon")
    @OneToOne(cascade = CascadeType.ALL)
    private ChannelIcon icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
            this.channel.setChannelId(id);
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
