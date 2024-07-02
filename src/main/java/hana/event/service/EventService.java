package hana.event.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.event.domain.*;
import hana.member.domain.MemberToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@TypeInfo(name = "EventService", description = "이벤트 서비스")
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventPrizeRepository eventPrizeRepository;
    private final EventWinnerRepository eventWinnerRepository;
    private final EventArrivalRepository eventArrivalRepository;
    private final ScheduledEventRepository scheduledEventRepository;
    private final EventTokenRepository eventTokenRepository;
    private final Random random;

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

    @MethodInfo(name = "readEventPrizes", description = "이벤트 상품들을 조회합니다.")
    public List<EventPrize> readEventPrizes(Long eventIdx) {
        return eventPrizeRepository.findAllByEventIdx(eventIdx);
    }

    @MethodInfo(name = "createEventWinners", description = "이벤트 당첨자들을 생성합니다.")
    public void createEventWinners(List<EventWinner> eventWinners) {
        eventWinnerRepository.saveAll(eventWinners);
    }

    @MethodInfo(name = "createEventArrival", description = "이벤트 신청자를 생성합니다.")
    public void createEventArrival(EventArrival eventArrival) {
        eventArrivalRepository.save(eventArrival);
    }

    @MethodInfo(name = "scheduleEvent", description = "이벤트를 예약합니다.")
    public ScheduledEvent scheduleEvent(ScheduledEvent scheduledEvent) {
        return scheduledEventRepository.save(scheduledEvent);
    }

    @MethodInfo(name = "drawEventWinners", description = "이벤트 당첨자를 추첨합니다.")
    public List<EventWinner> drawEventWinners(
            List<EventPrize> eventPrizes, List<EventArrival> eventArrivals) {
        int totalPrizeCount = 0;
        for (EventPrize eventPrize : eventPrizes) {
            totalPrizeCount += eventPrize.getEventPrizeLimit();
        }
        ArrayList<EventWinner> eventWinners = new ArrayList<>();

        while (totalPrizeCount > 0) {
            int randomIndex = random.nextInt(eventArrivals.size());

            EventArrival eventArrival = eventArrivals.get(randomIndex);
            if (eventArrival.getIsWinner()) {
                continue;
            }
            eventArrival.setIsWinner(true);

            EventPrize eventPrize = eventPrizes.get(random.nextInt(eventPrizes.size()));

            if (eventPrize.getEventPrizeLimit() == 0) {
                eventArrival.setIsWinner(false);
                continue;
            }
            eventPrize.setEventPrizeLimit(eventPrize.getEventPrizeLimit() - 1);

            eventWinners.add(
                    EventWinner.builder()
                            .student(eventArrival.getStudent())
                            .eventPrize(eventPrize)
                            .build());

            totalPrizeCount--;
        }

        return eventWinners;
    }

    @MethodInfo(name = "updateEventPush", description = "이벤트 푸시를 업데이트합니다.")
    public void updateEventPush(Long eventIdx, MemberToken memberToken) {
        eventTokenRepository.save(
                EventToken.builder()
                        .memberIdx(memberToken.getMemberIdx())
                        .studentIdx(memberToken.getStudentIdx())
                        .eventIdx(eventIdx)
                        .deptIdx(memberToken.getDeptIdx())
                        .fcmToken(memberToken.getFcmToken())
                        .ttl(memberToken.getTtl())
                        .build());
    }

    @MethodInfo(name = "findAllEventTokensByDeptIdx", description = "학과 식별자로 이벤트 토큰 목록을 조회합니다.")
    public List<EventToken> findAllEventTokensByDeptIdx(Long deptIdx) {
        return eventTokenRepository.findAllByDeptIdx(deptIdx);
    }

    public EventService(
            EventRepository eventRepository,
            EventPrizeRepository eventPrizeRepository,
            EventWinnerRepository eventWinnerRepository,
            EventArrivalRepository eventArrivalRepository,
            ScheduledEventRepository scheduledEventRepository,
            EventTokenRepository eventTokenRepository) {
        this.eventRepository = eventRepository;
        this.eventPrizeRepository = eventPrizeRepository;
        this.eventWinnerRepository = eventWinnerRepository;
        this.eventArrivalRepository = eventArrivalRepository;
        this.scheduledEventRepository = scheduledEventRepository;
        this.eventTokenRepository = eventTokenRepository;
        this.random = new Random();
    }
}
