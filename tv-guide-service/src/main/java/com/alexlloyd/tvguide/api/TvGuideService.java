package com.alexlloyd.tvguide.api;

import java.util.stream.Stream;

import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.GuideWrapper;

public interface TvGuideService {
    Stream<Channel> getChannels();

    void saveTvGuide(GuideWrapper guideWrapper);
}
