package ru.tickets.settings.converters;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import ru.gb.ticket.api.tracker.TrackerDto;
import ru.gb.ticket.api.tracker.TrackerDtoStandart;
import ru.gb.ticket.api.tracker.fabrica.TrackerDtoFactory;
import ru.gb.ticket.api.tracker.fabrica.TrackerDtoType;
import ru.tickets.settings.data.Tracker;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TrackerConverter {
    public Tracker dtoToEntity(TrackerDto trackerDto) {
        return new Tracker(trackerDto.getId(), trackerDto.getTitle(), LocalDateTime.now(), LocalDateTime.now());
    }

    public TrackerDto entityToDto(Tracker tracker) {
        return new TrackerDtoStandart(tracker.getId(), tracker.getTitle(),tracker.getCreatedAt(),tracker.getUpdatedAt());
    }

    public TrackerDto entityToDto(Optional<Tracker> tracker) {
        return new TrackerDtoStandart(tracker.get().getId(), tracker.get().getTitle(),
                tracker.get().getCreatedAt(),tracker.get().getUpdatedAt());
    }

    public TrackerDto JsonToDto (TrackerDtoType typeClient,String jsonTracker) throws JSONException {
        JSONObject json = new JSONObject(jsonTracker);
        TrackerDto trackerDto = TrackerDtoFactory.createTrackerDto(typeClient, json.get("title").toString(), Long.parseLong( json.get("id").toString()));
        return trackerDto;
    }
}
