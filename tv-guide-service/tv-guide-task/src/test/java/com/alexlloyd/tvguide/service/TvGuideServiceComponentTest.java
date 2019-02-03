package com.alexlloyd.tvguide.service;

import java.util.Set;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.mapper.ChannelIconMapper;
import com.alexlloyd.tvguide.mapper.ChannelMapper;
import com.alexlloyd.tvguide.mapper.ProgrammeMapperTest;
import com.alexlloyd.tvguide.model.Channel;
import com.alexlloyd.tvguide.model.GuideWrapper;
import com.alexlloyd.tvguide.model.XmlTvChannel;
import com.alexlloyd.tvguide.model.XmlTvProgramme;
import com.alexlloyd.tvguide.task.TvGuideTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
public class TvGuideServiceComponentTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TvGuideService tvGuideService;

    @Autowired
    private TvGuideTask tvGuideTask;

    @Test
    public void testGetTvGuide() {
        XmlTvProgramme xmlProgramme = new XmlTvProgramme.Builder()
                .setChannelId("def")
                .setTitle("Programme Name")
                .setDescription("Programme Description")
                .build();
        XmlTvChannel xmlChannel = new XmlTvChannel.Builder()
                .setId("abc")
                .setName("Channel Name")
                .build();

        GuideWrapper wrapper = new GuideWrapper();
        wrapper.setProgrammes(Set.of(xmlProgramme));
        wrapper.setChannels(Set.of(xmlChannel));

        doReturn(ResponseEntity.status(200).body(wrapper)).when(restTemplate).getForEntity(anyString(), any(), anyString());

        // Method call
        this.tvGuideTask.getAll();
        verify(tvGuideService).saveChannel(any(Channel.class));

    }

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public RestTemplate restTemplate() {
            return mock(RestTemplate.class);
        }

        @Bean
        public TvGuideService tvGuideService() {
            return mock(TvGuideService.class);
        }

        @Bean
        public ProgrammeMapperTest programmeMapper() {
            return new ProgrammeMapperTest();
        }

        @Autowired
        @Bean
        public ChannelMapper channelMapper(ChannelIconMapper channelIconMapper) {
            return new ChannelMapper(channelIconMapper);
        }

        @Bean
        public ChannelIconMapper channelIconMapper() {
            return new ChannelIconMapper();
        }

        @Autowired
        @Bean
        public TvGuideTask tvGuideTask(RestTemplate restTemplate, TvGuideService tvGuideService, ChannelMapper channelMapper) {
            return new TvGuideTask(restTemplate, tvGuideService, channelMapper);
        }
    }
}
