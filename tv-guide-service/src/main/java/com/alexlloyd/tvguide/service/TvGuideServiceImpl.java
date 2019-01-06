package com.alexlloyd.tvguide.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.ChannelIcon;
import com.alexlloyd.tvguide.models.GuideWrapper;
import com.alexlloyd.tvguide.repository.ChannelIconRepository;
import com.alexlloyd.tvguide.repository.ChannelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TvGuideServiceImpl implements TvGuideService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TvGuideServiceImpl.class);

    private final ChannelRepository channelRepository;
    private final ChannelIconRepository channelIconRepository;

    @Autowired
    public TvGuideServiceImpl(ChannelRepository channelRepository, ChannelIconRepository channelIconRepository) {
        this.channelRepository = channelRepository;
        this.channelIconRepository = channelIconRepository;
    }

    @Override
    public List<Channel> getChannels() {
        Iterable<Channel> channelIterable = this.channelRepository.findAll();

        return StreamSupport.stream(channelIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTvGuide(GuideWrapper guideWrapper) {
        guideWrapper
                .getChannels()
                .forEach(this::saveChannel);
    }

    private void saveChannel(Channel channel) {
        String channelId = channel.getChannelId();
        String channelName = channel.getName();

        this.saveChannelIcon(channel.getIcon());

        if (this.channelRepository.getChannelByChannelId(channelId) == null) {
            LOGGER.info("Adding new channel \"{}\"", channelName);
            this.channelRepository.save(channel);
        } else {
            LOGGER.debug("Channel \"{}\" already exists in Database", channelName);
        }


    }

    private void saveChannelIcon(ChannelIcon icon) {
        if (icon != null) {
            LOGGER.info("Adding Channel icon: {}", icon.getSrc());
            this.channelIconRepository.save(icon);
        }
    }
}
