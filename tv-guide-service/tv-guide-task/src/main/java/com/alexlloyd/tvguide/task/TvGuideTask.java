package com.alexlloyd.tvguide.task;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.mapper.ChannelMapper;
import com.alexlloyd.tvguide.model.GuideWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TvGuideTask {
    private static final String URL = "http://www.xmltv.co.uk/feed/{id}";

    private final ChannelMapper channelMapper;
    private final RestTemplate restTemplate;
    private final TvGuideService guideService;

    @Autowired
    public TvGuideTask(RestTemplate restTemplate, TvGuideService guideService, ChannelMapper channelMapper) {
        this.restTemplate = restTemplate;
        this.guideService = guideService;
        this.channelMapper = channelMapper;
    }

    private GuideWrapper getGuide(String id) {
        ResponseEntity<GuideWrapper> responseEntity = restTemplate.getForEntity(URL, GuideWrapper.class, id);
        return responseEntity.getBody();
    }

    @Scheduled(fixedRate = 100000) // 100 Seconds
    public void getAll() {
        GuideWrapper guideWrapper = this.getGuide("6128");

        guideWrapper
                .getChannels()
                .stream()
                .map(channelMapper::mapToChannel)
                .forEach(guideService::saveChannel);
    }
}
