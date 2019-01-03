package com.alexlloyd.tvguide.api;

import com.alexlloyd.tvguide.models.GuideWrapper;

public interface XmlTvGuideService {
    GuideWrapper getGuide(String id);
}
