package hana.notification.domain;

import hana.common.annotation.TypeInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Notification", description = "알림 엔티티")
@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_idx", nullable = false)
    private Long notificationIdx;

    @Column(name = "notification_title", nullable = false)
    private String notificationTitle;

    @Column(name = "notification_body", nullable = false)
    private String notificationBody;
}
