package com.alexlloyd.tvguide.repository;

import com.alexlloyd.tvguide.model.Channel;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Integer> {
    Channel getChannelByChannelId(String channelId);
}
