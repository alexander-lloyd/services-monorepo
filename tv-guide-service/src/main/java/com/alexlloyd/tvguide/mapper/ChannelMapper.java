package com.alexlloyd.tvguide.mapper;

import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.xmltv.XmlTvChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Map between an XmlTvChannel and Channel
 */
@Component
public class ChannelMapper {
    @Autowired
    private ChannelIconMapper channelIconMapper;

    public Channel mapToChannel(XmlTvChannel xmlTvChannel) {
        return new Channel.Builder()
                .setName(xmlTvChannel.getName())
                .setChannelId(xmlTvChannel.getChannelId())
                .setIcon(channelIconMapper.mapToChannelIcon(xmlTvChannel.getIcon()))
                .build();
    }
}
