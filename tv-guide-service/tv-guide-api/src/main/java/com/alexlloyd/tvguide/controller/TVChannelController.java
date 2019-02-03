package com.alexlloyd.tvguide.controller;

import java.util.Collection;

import com.alexlloyd.response.model.DataWrapper;
import com.alexlloyd.response.model.Response;
import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.model.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tv/channel")
public class TVChannelController {

    private final TvGuideService tvGuideService;

    @Autowired
    public TVChannelController(TvGuideService tvGuideService) {
        this.tvGuideService = tvGuideService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<DataWrapper<Collection<Channel>>> channelList() {
        return Response.success(new DataWrapper<>("channels", this.tvGuideService.getChannels()));
    }
}
