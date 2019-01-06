package com.alexlloyd.tvguide.api;

import java.util.List;

import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.GuideWrapper;

public interface TvGuideService {
    List<Channel> getChannels();

    void saveTvGuide(GuideWrapper guideWrapper);
}
