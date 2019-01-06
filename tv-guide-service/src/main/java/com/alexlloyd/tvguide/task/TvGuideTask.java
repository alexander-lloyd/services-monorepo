package com.alexlloyd.tvguide.task;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.models.GuideWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TvGuideTask {
    private static final String URL = "http://www.xmltv.co.uk/feed/{id}";

    private final RestTemplate restTemplate;
    private TvGuideService guideService;

    @Autowired
    public TvGuideTask(RestTemplate restTemplate, TvGuideService guideService) {
        this.restTemplate = restTemplate;
        this.guideService = guideService;
    }

    private GuideWrapper getGuide(String id) {
        ResponseEntity<GuideWrapper> responseEntity = restTemplate.getForEntity(URL, GuideWrapper.class, id);
        return responseEntity.getBody();
    }

    @Scheduled(fixedRate = 100000) // 100 Seconds
    public void getAll() {
        this.guideService.saveTvGuide(this.getGuide("6128"));
    }
}
