package com.alexlloyd.tvguide.repository;

import com.alexlloyd.tvguide.models.ChannelIcon;
import org.springframework.data.repository.CrudRepository;

public interface ChannelIconRepository extends CrudRepository<ChannelIcon, Integer> {
    ChannelIcon getChannelIconBySrc(String src);
}
