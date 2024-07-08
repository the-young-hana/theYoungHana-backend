package hana.event.task;

import hana.common.dto.notification.FcmSendReqDto;
import hana.common.service.FCMService;
import hana.event.domain.*;
import hana.event.service.EventService;
import hana.member.domain.MemberToken;
import hana.member.service.MemberTokenService;
import java.io.IOException;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// @TypeInfo(name = "ScheduledEventTasks", description = "스케줄 이벤트 태스크")
@Component
public class ScheduledEventTasks {
    private final EventService eventService;
    private final MemberTokenService memberTokenService;
    private final FCMService fcmService;

    // @MethodInfo(name = "executeScheduledEventTasks", description = "스케줄된 이벤트 태스크를 실행합니다.")
    @Scheduled(cron = "0 * * * * ?")
    public void scheduledEventTasks() throws IOException {
        List<ScheduledEvent> scheduledEvents = eventService.readScheduledEvents();

        for (ScheduledEvent scheduledEvent : scheduledEvents) {
            List<MemberToken> memberTokens =
                    memberTokenService.findAllByDeptIdx(scheduledEvent.getDeptIdx());
            List<EventToken> eventTokens =
                    eventService.findAllEventTokensByDeptIdx(scheduledEvent.getEventIdx());

            if (scheduledEvent.getScheduledEventType().equals(ScheduledEventEnumType.신청_시작)) {
                for (MemberToken memberToken : memberTokens) {
                    fcmService.sendMessageTo(
                            FcmSendReqDto.builder()
                                    .memberIdx(memberToken.getMemberIdx())
                                    .token(memberToken.getFcmToken())
                                    .title("이벤트 신청 시작")
                                    .category("더영하나")
                                    .body(
                                            "["
                                                    + scheduledEvent.getEventTitle()
                                                    + "] 이벤트 신청이 시작되었습니다. "
                                                    + scheduledEvent.getEventEndDatetime().getYear()
                                                    + "년 "
                                                    + scheduledEvent
                                                            .getEventEndDatetime()
                                                            .getMonthValue()
                                                    + "월 "
                                                    + scheduledEvent
                                                            .getEventEndDatetime()
                                                            .getDayOfMonth()
                                                    + "일 "
                                                    + scheduledEvent.getEventEndDatetime().getHour()
                                                    + "시 "
                                                    + scheduledEvent
                                                            .getEventEndDatetime()
                                                            .getMinute()
                                                    + "분 "
                                                    + scheduledEvent
                                                            .getEventEndDatetime()
                                                            .getSecond()
                                                    + "초까지 신청 가능합니다.")
                                    .build());
                }
            } else if (scheduledEvent
                    .getScheduledEventType()
                    .equals(ScheduledEventEnumType.신청_마감)) {
                for (MemberToken memberToken : memberTokens) {
                    fcmService.sendMessageTo(
                            FcmSendReqDto.builder()
                                    .memberIdx(memberToken.getMemberIdx())
                                    .token(memberToken.getFcmToken())
                                    .title("이벤트 신청 마감")
                                    .category("더영하나")
                                    .body(
                                            "["
                                                    + scheduledEvent.getEventTitle()
                                                    + "] 이벤트 신청이 마감되었습니다.")
                                    .build());
                }
            } else if (scheduledEvent
                    .getScheduledEventType()
                    .equals(ScheduledEventEnumType.입금_시작)) {
                for (EventToken eventToken : eventTokens) {
                    fcmService.sendMessageTo(
                            FcmSendReqDto.builder()
                                    .memberIdx(eventToken.getMemberIdx())
                                    .token(eventToken.getFcmToken())
                                    .title("이벤트 입금 시작")
                                    .category("더영하나")
                                    .body(
                                            "["
                                                    + scheduledEvent.getEventTitle()
                                                    + "] 이벤트 입금이 시작되었습니다. "
                                                    + scheduledEvent
                                                            .getEventFeeEndDatetime()
                                                            .getYear()
                                                    + "년 "
                                                    + scheduledEvent
                                                            .getEventFeeEndDatetime()
                                                            .getMonthValue()
                                                    + "월 "
                                                    + scheduledEvent
                                                            .getEventFeeEndDatetime()
                                                            .getDayOfMonth()
                                                    + "일 "
                                                    + scheduledEvent
                                                            .getEventFeeEndDatetime()
                                                            .getHour()
                                                    + "시 "
                                                    + scheduledEvent
                                                            .getEventFeeEndDatetime()
                                                            .getMinute()
                                                    + "분 "
                                                    + scheduledEvent
                                                            .getEventFeeEndDatetime()
                                                            .getSecond()
                                                    + "초까지 "
                                                    + scheduledEvent.getEventFee()
                                                    + "원을 입금해주세요.")
                                    .build());
                }
            } else if (scheduledEvent
                    .getScheduledEventType()
                    .equals(ScheduledEventEnumType.입금_마감)) {
                for (EventToken eventToken : eventTokens) {
                    fcmService.sendMessageTo(
                            FcmSendReqDto.builder()
                                    .memberIdx(eventToken.getMemberIdx())
                                    .token(eventToken.getFcmToken())
                                    .title("이벤트 입금 마감")
                                    .category("더영하나")
                                    .body(
                                            "["
                                                    + scheduledEvent.getEventTitle()
                                                    + "] 이벤트 입금이 마감되었습니다.")
                                    .build());
                }
            } else if (scheduledEvent
                    .getScheduledEventType()
                    .equals(ScheduledEventEnumType.응모_시작)) {

                eventService.createEventWinners(
                        eventService.drawEventWinners(
                                eventService.readEventPrizes(scheduledEvent.getEventIdx()),
                                eventService.readEventArrivals(scheduledEvent.getEventIdx())));

                for (MemberToken memberToken : memberTokens) {
                    fcmService.sendMessageTo(
                            FcmSendReqDto.builder()
                                    .memberIdx(memberToken.getMemberIdx())
                                    .token(memberToken.getFcmToken())
                                    .title("이벤트 응모 시작")
                                    .category("더영하나")
                                    .body(
                                            "["
                                                    + scheduledEvent.getEventTitle()
                                                    + "] 이벤트 응모가 마감되었습니다. 결과 페이지에서 응모 결과를 확인해보세요.")
                                    .build());
                }
            } else if (scheduledEvent
                    .getScheduledEventType()
                    .equals(ScheduledEventEnumType.선착_시작)) {
                for (MemberToken memberToken : memberTokens) {
                    fcmService.sendMessageTo(
                            FcmSendReqDto.builder()
                                    .memberIdx(memberToken.getMemberIdx())
                                    .token(memberToken.getFcmToken())
                                    .title("이벤트 선착순 신청 시작")
                                    .category("더영하나")
                                    .body(
                                            "["
                                                    + scheduledEvent.getEventTitle()
                                                    + "] 이벤트 선착순 신청이 시작되었습니다. "
                                                    + scheduledEvent.getEventDatetime().getYear()
                                                    + "년 "
                                                    + scheduledEvent
                                                            .getEventDatetime()
                                                            .getMonthValue()
                                                    + "월 "
                                                    + scheduledEvent
                                                            .getEventDatetime()
                                                            .getDayOfMonth()
                                                    + "일 "
                                                    + scheduledEvent.getEventDatetime().getHour()
                                                    + "시 "
                                                    + scheduledEvent.getEventDatetime().getMinute()
                                                    + "분 "
                                                    + scheduledEvent.getEventDatetime().getSecond()
                                                    + "초까지 신청 가능합니다.")
                                    .build());
                }
            }
        }
    }

    public ScheduledEventTasks(
            EventService eventService,
            MemberTokenService memberTokenService,
            FCMService fcmService) {
        this.eventService = eventService;
        this.memberTokenService = memberTokenService;
        this.fcmService = fcmService;
    }
}
