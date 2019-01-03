package com.alexlloyd.tvguide.service;

import com.alexlloyd.tvguide.api.XmlTvGuideService;
import com.alexlloyd.tvguide.models.GuideWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class XmlTvGuideServiceImpl implements XmlTvGuideService {
    private static final String URL = "http://www.xmltv.co.uk/feed/{id}";

    private final RestTemplate restTemplate;

    @Autowired
    public XmlTvGuideServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GuideWrapper getGuide(String id) {
        ResponseEntity<GuideWrapper> responseEntity = restTemplate.getForEntity(URL, GuideWrapper.class, id);
        return responseEntity.getBody();
    }
}
