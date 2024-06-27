package hana.event.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.event.domain.Event;
import hana.event.domain.EventRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@TypeInfo(name = "EventService", description = "이벤트 서비스")
@Service
public class EventService {
    private final EventRepository eventRepository;

    @MethodInfo(name = "countEvent", description = "이벤트 개수를 조회합니다.")
    public Long countEvent() {
        return eventRepository.count();
    }

    @MethodInfo(name = "readEvent", description = "이벤트를 상세 조회합니다.")
    public Event readEvent(Long eventIdx) {
        return eventRepository.findByEventIdx(eventIdx);
    }

    @MethodInfo(name = "searchEvents", description = "이벤트 목록을 검색합니다.")
    public List<Event> searchEvents(String value, Boolean isEnd, Integer page) {
        Pageable pageable = PageRequest.of(page, 15);

        if (isEnd) {
            return eventRepository.searchEventsAfterIsEnd(value, pageable);
        } else {
            return eventRepository.searchEventsBeforeIsEnd(value, pageable);
        }
    }

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
