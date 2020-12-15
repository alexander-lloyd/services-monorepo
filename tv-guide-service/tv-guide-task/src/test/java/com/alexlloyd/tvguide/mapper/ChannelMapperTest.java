package com.alexlloyd.tvguide.mapper;

import com.alexlloyd.tvguide.model.Channel;
import com.alexlloyd.tvguide.model.XmlTvChannel;
import com.alexlloyd.tvguide.model.XmlTvChannelIcon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChannelMapperTest {
    @Test
    public void testChannelMapperEmpty() {
        ChannelIconMapper channelIconMapper = mock(ChannelIconMapper.class);
        ChannelMapper mapper = new ChannelMapper(channelIconMapper);

        XmlTvChannel xmlChannel = new XmlTvChannel.Builder()
                .build();

        Channel channel = mapper.mapToChannel(xmlChannel);
        assertNull(channel.getChannelId());
        assertNull(channel.getName());
        assertNull(channel.getChannelId());
        assertNull(channel.getIcon());
    }

    @Test
    public void testChannelMapper() {
        String channelId = "id";
        String name = "name";
        XmlTvChannelIcon channelIcon = new XmlTvChannelIcon();

        ChannelIconMapper channelIconMapper = mock(ChannelIconMapper.class);
        ChannelMapper mapper = new ChannelMapper(channelIconMapper);

        XmlTvChannel xmlChannel = new XmlTvChannel.Builder()
                .setId(channelId)
                .setName(name)
                .setIcon(channelIcon)
                .build();

        Channel channel = mapper.mapToChannel(xmlChannel);
        assertEquals(channelId, channel.getChannelId());
        assertEquals(name, channel.getName());
        assertNull(channel.getIcon());

        verify(channelIconMapper).mapToChannelIcon(eq(channelIcon));
    }

    @Test
    public void testChannelMapperNull() {
        ChannelIconMapper channelIconMapper = mock(ChannelIconMapper.class);
        ChannelMapper mapper = new ChannelMapper(channelIconMapper);

        XmlTvChannel xmlChannel = null;

        Channel channel = mapper.mapToChannel(xmlChannel);
        assertNull(channel);
    }
}