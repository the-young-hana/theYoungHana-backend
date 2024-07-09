package hana.event.domain;

import hana.event.dto.EventJoinResDto;
import hana.event.service.EventService;
import hana.member.domain.Student;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FcfsAdapter {

    private final StringRedisTemplate stringRedisTemplate;
    private final EventService eventService;

    public EventJoinResDto joinEvent(Student student, Event event) {
        String key = "event:" + event.getEventIdx() + ":count";
        Long eventWinnerCount = stringRedisTemplate.opsForValue().increment(key);

        if (eventWinnerCount > event.getEventLimit()) {
            stringRedisTemplate.opsForValue().decrement(key);
            throw new RuntimeException("이벤트 참여 가능 인원을 초과하였습니다.");
        }

        EventPrize eventPrize = eventService.readEventPrize(event.getEventIdx());

        eventService.createEventWinner(
                EventWinner.builder().eventPrize(eventPrize).student(student).build());

        return EventJoinResDto.builder()
                .data(
                        EventJoinResDto.Data.builder()
                                .eventCount(eventWinnerCount)
                                .eventLimit(event.getEventLimit())
                                .build())
                .build();
    }

    public FcfsAdapter(StringRedisTemplate stringRedisTemplate, EventService eventService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventService = eventService;
    }
}
