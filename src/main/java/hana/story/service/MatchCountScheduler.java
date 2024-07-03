package hana.story.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MatchCountScheduler {

    @Autowired private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateStoryCounts() {
        logger.info("start MatchCountScheduler - update story counts");
        String sql =
                "UPDATE stories s "
                        + "SET s.story_like_amount = (SELECT COUNT(*) FROM story_likes l WHERE l.story_idx = s.story_idx), "
                        + "s.story_comment_amount = (SELECT COUNT(*) FROM story_comments c WHERE c.story_idx = s.story_idx) "
                        + "WHERE s.story_idx IN (SELECT DISTINCT story_idx FROM story_likes UNION SELECT DISTINCT story_idx FROM story_comments)";
        jdbcTemplate.update(sql);
        logger.info("end MatchCountScheduler - update story counts");
    }
}
