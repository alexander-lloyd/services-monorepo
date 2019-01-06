package com.alexlloyd.tvguide.service;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.alexlloyd.tvguide.config.JacksonConfig;
import com.alexlloyd.tvguide.models.Channel;
import com.alexlloyd.tvguide.models.GuideWrapper;
import com.alexlloyd.tvguide.models.Programme;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = JacksonConfig.class)
@ExtendWith(SpringExtension.class)
public class DeserializationTest {

    @Autowired
    private XmlMapper xmlMapper;

    @Test
    public void testSerializationDeserialization() throws IOException {
        Channel bbc1 = new Channel();
        bbc1.setChannelId("abc");
        bbc1.setName("BBC1");
        Channel itv1 = new Channel();
        itv1.setChannelId("cde");
        itv1.setName("ITV1");

        Programme sherlock = new Programme();
        sherlock.setStartTime(ZonedDateTime.of(2019, 1, 1, 20, 30, 0, 0, ZoneId.of("UTC")));
        sherlock.setStopTime(ZonedDateTime.of(2019, 1, 1, 22, 0, 0, 0, ZoneId.of("UTC")));
        sherlock.setDescription("Benedict Cumberbatch");
        sherlock.setTitle("Sherlock");
        sherlock.setChannelId("abc");

        GuideWrapper guideWrapper = new GuideWrapper();
        guideWrapper.setProgrammes(Set.of(sherlock));
        guideWrapper.setChannels(Set.of(bbc1));

        String serialized = xmlMapper.writeValueAsString(guideWrapper);

        GuideWrapper wrapper = xmlMapper.readValue(serialized, GuideWrapper.class);

        List<Channel> channels = new LinkedList<>(wrapper.getChannels());
        List<Programme> programmes = new LinkedList<>(wrapper.getProgrammes());

        assertEquals(1, channels.size());
        assertEquals(1, programmes.size());

        assertEquals("abc", channels.get(0).getChannelId());
        assertEquals("BBC1", channels.get(0).getName());

        assertEquals("Sherlock", programmes.get(0).getTitle());
        assertEquals("Benedict Cumberbatch", programmes.get(0).getDescription());
    }
}
