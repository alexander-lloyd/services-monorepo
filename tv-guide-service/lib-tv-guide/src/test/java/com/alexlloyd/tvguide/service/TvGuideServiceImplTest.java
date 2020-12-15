package com.alexlloyd.tvguide.service;

import java.util.Collection;
import java.util.Collections;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.model.Channel;
import com.alexlloyd.tvguide.model.ChannelIcon;
import com.alexlloyd.tvguide.repository.ChannelIconRepository;
import com.alexlloyd.tvguide.repository.ChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
public class TvGuideServiceImplTest {
    private static final int CHANNEL_ID = 123;
    private static final String CHANNEL_CHANNEL_ID = "abc";
    private static final String CHANNEL_NAME = "Top Gear";
    private static final String CHANNEL_ICON_SRC = "https://www.bbc.co.uk";
    private static final ChannelIcon CHANNEL_ICON = new ChannelIcon();
    private static final Channel CHANNEL = new Channel();

    static {
        CHANNEL_ICON.setSrc(CHANNEL_ICON_SRC);
        CHANNEL.setId(CHANNEL_ID);
        CHANNEL.setChannelId(CHANNEL_CHANNEL_ID);
        CHANNEL.setName(CHANNEL_NAME);
        CHANNEL.setIcon(CHANNEL_ICON);
    }

    @Autowired
    private TvGuideService tvGuideService;

    @MockBean
    private ChannelRepository channelRepository;

    @MockBean
    private ChannelIconRepository channelIconRepository;

    @BeforeEach
    public void beforeEach() {
        Mockito.clearInvocations(channelRepository, channelIconRepository);
    }

    @Test
    public void testGetChannels() {
        doReturn(Collections.singleton(CHANNEL)).when(channelRepository).findAll();

        Collection<Channel> channels = tvGuideService.getChannels();
        assertEquals(1, channels.size());
    }

    @Test
    public void testSaveChannelIcon() {
        doReturn(null).when(channelIconRepository).getChannelIconBySrc(eq(CHANNEL_ICON_SRC));

        tvGuideService.saveChannelIcon(CHANNEL_ICON);

        verify(channelIconRepository).save(eq(CHANNEL_ICON));
    }

    @Test
    public void testSaveChannelIconExists() {
        doReturn(CHANNEL_ICON).when(channelIconRepository).getChannelIconBySrc(eq(CHANNEL_ICON_SRC));

        tvGuideService.saveChannelIcon(CHANNEL_ICON);

        verify(channelIconRepository, never()).save(eq(CHANNEL_ICON));
    }

    @Test
    public void testSaveChannel() {
        doReturn(null).when(channelRepository).getChannelByChannelId(eq(CHANNEL_CHANNEL_ID));

        tvGuideService.saveChannel(CHANNEL);

        verify(channelRepository).save(eq(CHANNEL));
    }

    @Test
    public void testSaveChannelExists() {
        doReturn(CHANNEL).when(channelRepository).getChannelByChannelId(eq(CHANNEL_CHANNEL_ID));

        tvGuideService.saveChannel(CHANNEL);

        verify(channelRepository, never()).save(eq(CHANNEL));
    }

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ChannelRepository channelRepository() {
            return mock(ChannelRepository.class);
        }

        @Bean
        public ChannelIconRepository channelIconRepository() {
            return mock(ChannelIconRepository.class);
        }

        @Autowired
        @Bean
        public TvGuideService tvGuideService(ChannelRepository channelRepository, ChannelIconRepository channelIconRepository) {
            return new TvGuideServiceImpl(channelRepository, channelIconRepository);
        }
    }
}