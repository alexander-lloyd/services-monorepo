package com.alexlloyd.tvguide.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.alexlloyd.tvguide.api.TvGuideService;
import com.alexlloyd.tvguide.mapper.ChannelMapper;
import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.ChannelIcon;
import com.alexlloyd.tvguide.models.xmltv.GuideWrapper;
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
    private final ChannelMapper channelMapper;

    @Autowired
    public TvGuideServiceImpl(ChannelRepository channelRepository, ChannelIconRepository channelIconRepository, ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.channelIconRepository = channelIconRepository;
        this.channelMapper = channelMapper;
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
                .stream()
                .map(this.channelMapper::mapToChannel)
                .forEach(this::saveChannel);
    }

    private void saveChannel(Channel channel) {
        String channelId = channel.getChannelId();
        String channelName = channel.getName();

        if (this.channelRepository.getChannelByChannelId(channelId) == null) {
            LOGGER.info("Adding new channel \"{}\"", channelName);
            this.channelRepository.save(channel);
        } else {
            LOGGER.debug("Channel \"{}\" already exists in Database", channelName);
        }

        ChannelIcon channelIcon = channel.getIcon();
        if (channelIcon != null) {
            this.saveChannelIcon(channelIcon);
        }
    }

    private void saveChannelIcon(ChannelIcon icon) {
        if (this.channelIconRepository.getChannelIconBySrc(icon.getSrc()) == null) {
            LOGGER.info("Adding Channel icon: {}", icon.getSrc());
            this.channelIconRepository.save(icon);
        }
    }
}
