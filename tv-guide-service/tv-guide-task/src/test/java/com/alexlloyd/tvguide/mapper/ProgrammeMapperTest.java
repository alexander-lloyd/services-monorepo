package com.alexlloyd.tvguide.mapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.alexlloyd.tvguide.model.Programme;
import com.alexlloyd.tvguide.model.XmlTvProgramme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProgrammeMapperTest {
    @Test
    public void testProgrammeMapperNull() {
        ProgrammeMapper mapper = new ProgrammeMapper();

        XmlTvProgramme xmlProgramme = new XmlTvProgramme.Builder()
                .build();

        Programme programme = mapper.mapToProgramme(xmlProgramme);
        assertNull(programme.getChannelId());
        assertNull(programme.getTitle());
        assertNull(programme.getDescription());
        assertNull(programme.getStartTime());
        assertNull(programme.getStopTime());
    }

    @Test
    public void testProgrammeMapper() {
        String channelId = "channelId";
        String title = "title";
        String desc = "desc";
        ZonedDateTime startTime = ZonedDateTime.of(2018, 2, 3, 12, 0, 0, 0, ZoneId.of("GMT"));
        ZonedDateTime stopTime = ZonedDateTime.of(2018, 2, 3, 12, 0, 0, 0, ZoneId.of("GMT"));

        ProgrammeMapper mapper = new ProgrammeMapper();

        XmlTvProgramme xmlProgramme = new XmlTvProgramme.Builder()
                .setChannelId(channelId)
                .setTitle(title)
                .setDescription(desc)
                .setStartTime(startTime)
                .setStopTime(stopTime)
                .build();

        Programme programme = mapper.mapToProgramme(xmlProgramme);
        assertEquals(channelId, programme.getChannelId());
        assertEquals(title, programme.getTitle());
        assertEquals(desc, programme.getDescription());
        assertEquals(startTime, programme.getStartTime());
        assertEquals(stopTime, programme.getStopTime());
    }
}
