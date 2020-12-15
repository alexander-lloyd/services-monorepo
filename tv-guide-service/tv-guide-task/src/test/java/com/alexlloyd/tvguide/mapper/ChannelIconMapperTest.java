package com.alexlloyd.tvguide.mapper;

import com.alexlloyd.tvguide.model.ChannelIcon;
import com.alexlloyd.tvguide.model.XmlTvChannelIcon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ChannelIconMapperTest {
    @Test
    public void testChannelIconMapperEmpty() {
        ChannelIconMapper mapper = new ChannelIconMapper();

        XmlTvChannelIcon xmlChannelIcon = new XmlTvChannelIcon();

        ChannelIcon icon = mapper.mapToChannelIcon(xmlChannelIcon);
        assertNull(icon.getId());
        assertNull(icon.getSrc());
    }

    @Test
    public void testChannelIconMapper() {
        String src = "src";
        ChannelIconMapper mapper = new ChannelIconMapper();

        XmlTvChannelIcon xmlChannelIcon = new XmlTvChannelIcon();
        xmlChannelIcon.setSrc(src);

        ChannelIcon icon = mapper.mapToChannelIcon(xmlChannelIcon);
        assertEquals(src, icon.getSrc());
    }

    @Test
    public void testProgrammerMapperNull() {
        ChannelIconMapper mapper = new ChannelIconMapper();
        XmlTvChannelIcon xmlTvChannelIcon = null;

        ChannelIcon channelIcon = mapper.mapToChannelIcon(xmlTvChannelIcon);
        assertNull(channelIcon);
    }
}
