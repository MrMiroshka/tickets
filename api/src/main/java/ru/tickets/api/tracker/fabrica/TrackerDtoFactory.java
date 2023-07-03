package ru.tickets.api.tracker.fabrica;

import ru.tickets.api.tracker.TrackerDto;
import ru.tickets.api.tracker.TrackerDtoStandart;

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
