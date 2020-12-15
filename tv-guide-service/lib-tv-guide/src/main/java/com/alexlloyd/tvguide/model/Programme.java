package com.alexlloyd.tvguide.model;

import java.time.ZonedDateTime;

public class Programme {
    private String title;

    private String description;

    private ZonedDateTime startTime;

    private ZonedDateTime stopTime;

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

    public static class Builder {
        private Programme programme;

        public Builder() {
            this.programme = new Programme();
        }

        public Builder setTitle(String title) {
            this.programme.setTitle(title);
            return this;
        }

        public Builder setDescription(String description) {
            this.programme.setDescription(description);
            return this;
        }

        public Builder setStartTime(ZonedDateTime startTime) {
            this.programme.setStartTime(startTime);
            return this;
        }

        public Builder setStopTime(ZonedDateTime stopTime) {
            this.programme.setStopTime(stopTime);
            return this;
        }

        public Builder setChannelId(String channelId) {
            this.programme.setChannelId(channelId);
            return this;
        }

        public Programme build() {
            return this.programme;
        }
    }
}
