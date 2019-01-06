package com.alexlloyd.tvguide.repository;

import com.alexlloyd.tvguide.models.Channel;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRepository extends CrudRepository<Channel, Integer> {
    Channel getChannelByChannelId(String channelId);


}
