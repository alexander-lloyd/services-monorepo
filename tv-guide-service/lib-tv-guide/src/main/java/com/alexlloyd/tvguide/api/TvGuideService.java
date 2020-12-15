package com.alexlloyd.tvguide.api;

import java.util.List;

import com.alexlloyd.tvguide.model.Channel;
import com.alexlloyd.tvguide.model.ChannelIcon;
import com.alexlloyd.tvguide.model.Programme;

public interface TvGuideService {
    List<Channel> getChannels();

    void saveChannel(Channel channel);
    void saveProgramme(Programme programme);

    void saveChannelIcon(ChannelIcon icon);
}
