package com.alexlloyd.tvguide.mapper;

import com.alexlloyd.tvguide.model.Channel;
import com.alexlloyd.tvguide.model.XmlTvChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Map between an XmlTvChannel and Channel
 */
@Component
public class ChannelMapper {
    private final ChannelIconMapper channelIconMapper;

    @Autowired
    public ChannelMapper(ChannelIconMapper channelIconMapper) {
        this.channelIconMapper = channelIconMapper;
    }

    public Channel mapToChannel(XmlTvChannel xmlTvChannel) {
        if (xmlTvChannel == null) {
            return null;
        }

        return new Channel.Builder()
                .setName(xmlTvChannel.getName())
                .setChannelId(xmlTvChannel.getChannelId())
                .setIcon(channelIconMapper.mapToChannelIcon(xmlTvChannel.getIcon()))
                .build();
    }
}
