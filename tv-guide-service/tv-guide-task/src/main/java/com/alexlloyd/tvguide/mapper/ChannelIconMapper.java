package com.alexlloyd.tvguide.mapper;

import com.alexlloyd.tvguide.model.ChannelIcon;
import com.alexlloyd.tvguide.model.XmlTvChannelIcon;
import org.springframework.stereotype.Component;

@Component
public class ChannelIconMapper {
    public ChannelIcon mapToChannelIcon(XmlTvChannelIcon xmlTvChannelIcon) {
        if (xmlTvChannelIcon == null) {
            return null;
        }
        return new ChannelIcon.Builder()
                .setSrc(xmlTvChannelIcon.getSrc())
                .build();
    }
}
