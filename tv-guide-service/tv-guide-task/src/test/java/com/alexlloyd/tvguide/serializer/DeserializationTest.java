package com.alexlloyd.tvguide.serializer;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.alexlloyd.tvguide.config.JacksonConfig;
import com.alexlloyd.tvguide.model.GuideWrapper;
import com.alexlloyd.tvguide.model.XmlTvChannel;
import com.alexlloyd.tvguide.model.XmlTvChannelIcon;
import com.alexlloyd.tvguide.model.XmlTvProgramme;
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
        XmlTvChannelIcon bbc1icon = new XmlTvChannelIcon();
        bbc1icon.setSrc("abc");

        XmlTvChannel bbc1 = new XmlTvChannel.Builder()
                .setIcon(bbc1icon)
                .setName("BBC1")
                .build();

        XmlTvProgramme sherlock = new XmlTvProgramme.Builder()
                .setStartTime(ZonedDateTime.of(2019, 1, 1, 20, 30, 0, 0, ZoneId.of("UTC")))
                .setStopTime(ZonedDateTime.of(2019, 1, 1, 22, 0, 0, 0, ZoneId.of("UTC")))
                .setTitle("Sherlock")
                .setDescription("Benedict Cumberbatch")
                .setChannelId("abc")
                .build();

        GuideWrapper guideWrapper = new GuideWrapper();
        guideWrapper.setProgrammes(Set.of(sherlock));
        guideWrapper.setChannels(Set.of(bbc1));

        String serialized = xmlMapper.writeValueAsString(guideWrapper);

        GuideWrapper wrapper = xmlMapper.readValue(serialized, GuideWrapper.class);

        List<XmlTvChannel> channels = new LinkedList<>(wrapper.getChannels());
        List<XmlTvProgramme> programmes = new LinkedList<>(wrapper.getProgrammes());

        assertEquals(1, channels.size());
        assertEquals(1, programmes.size());

        assertEquals("BBC1", channels.get(0).getName());

        assertEquals("Sherlock", programmes.get(0).getTitle());
        assertEquals("Benedict Cumberbatch", programmes.get(0).getDescription());
    }
}
