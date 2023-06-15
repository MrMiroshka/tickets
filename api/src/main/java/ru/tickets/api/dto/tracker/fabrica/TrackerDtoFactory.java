package ru.tickets.api.dto.tracker.fabrica;

import ru.tickets.api.dto.tracker.TrackerDto;
import ru.tickets.api.dto.tracker.TrackerDtoStandart;

public class TrackerDtoFactory {

    public static TrackerDto createTrackerDto(TrackerDtoType type,String title,Long id) {
        TrackerDto trackerDto = null;

        switch (type) {
            case WEB:
                trackerDto =  new TrackerDtoStandart(title,id);
                break;
        }

        return trackerDto;
    }
}
