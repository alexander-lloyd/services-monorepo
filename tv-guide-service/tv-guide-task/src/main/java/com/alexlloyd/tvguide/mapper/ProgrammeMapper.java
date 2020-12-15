package com.alexlloyd.tvguide.mapper;

import com.alexlloyd.tvguide.model.Programme;
import com.alexlloyd.tvguide.model.XmlTvProgramme;
import org.springframework.stereotype.Component;

@Component
public class ProgrammeMapper {
    public Programme mapToProgramme(XmlTvProgramme xmlTvProgramme) {
        if (xmlTvProgramme == null) {
            return null;
        }

        return new Programme.Builder()
                .setChannelId(xmlTvProgramme.getChannelId())
                .setTitle(xmlTvProgramme.getTitle())
                .setDescription(xmlTvProgramme.getDescription())
                .setStartTime(xmlTvProgramme.getStartTime())
                .setStopTime(xmlTvProgramme.getStopTime())
                .build();
    }
}
