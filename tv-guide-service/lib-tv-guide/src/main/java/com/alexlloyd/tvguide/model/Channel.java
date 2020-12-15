package com.alexlloyd.tvguide.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.NONE)
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    private String channelId;

    private String name;

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

        public Builder setChannelId(String id) {
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
