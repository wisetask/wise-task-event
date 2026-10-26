package ru.leti.wise.task.event.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.leti.wise.task.event.Statistic;
import ru.leti.wise.task.event.entity.StatisticEntity;
import java.util.UUID;

@Repository
public interface StatisticRepository extends R2dbcRepository<StatisticEntity, UUID> {
    String FIND_LATEST = """
            SELECT * FROM statistic
            WHERE task_id IS NOT DISTINCT FROM :taskId
              AND user_id IS NOT DISTINCT FROM :userId
              AND type = :type
              AND scope = :scope
              AND event_type = :eventType
            ORDER BY updated_at DESC
            LIMIT 1
            """;

    @Query(FIND_LATEST)
    Mono<StatisticEntity> findByTaskIdAndUserIdAndTypeAndScopeAndEventType(UUID taskId, UUID userId, Statistic.StatisticType type,
                                                                          Statistic.StatisticScope scope, String eventType);

    @Query("""
            SELECT * FROM statistic
            WHERE task_id IS NOT DISTINCT FROM :taskId
              AND user_id IS NULL
              AND type = :type
              AND scope = :scope
              AND event_type = :eventType
            ORDER BY updated_at DESC
            LIMIT 1
            """)
    Mono<StatisticEntity> findByTaskIdAndTypeAndScopeAndEventType(UUID taskId, Statistic.StatisticType type,
                                                                  Statistic.StatisticScope scope, String eventType);

    @Query("""
            SELECT * FROM statistic
            WHERE user_id IS NOT DISTINCT FROM :userId
              AND task_id IS NULL
              AND type = :type
              AND scope = :scope
              AND event_type = :eventType
            ORDER BY updated_at DESC
            LIMIT 1
            """)
    Mono<StatisticEntity> findByUserIdAndTypeAndScopeAndEventType(UUID userId, Statistic.StatisticType type,
                                                                  Statistic.StatisticScope scope, String eventType);

    @Query("""
            SELECT * FROM statistic
            WHERE task_id IS NULL
              AND user_id IS NULL
              AND type = :type
              AND scope = :scope
              AND event_type = :eventType
            ORDER BY updated_at DESC
            LIMIT 1
            """)
    Mono<StatisticEntity> findByTypeAndScopeAndEventType(Statistic.StatisticType type,
                                                         Statistic.StatisticScope scope, String eventType);
}
