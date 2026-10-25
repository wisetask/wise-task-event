package ru.leti.wise.task.event.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;


@Value
@AllArgsConstructor
@Builder
@Table("event")
public class Event implements Persistable<UUID> {
    @Id
    UUID id;
    @Column("created_at")
    Instant time;
    @Column("session_id")
    UUID sessionId;
    @Column("event_type_id")
    Integer eventTypeId;
    @Column("event_entity_id")
    Integer eventEntityId;
    @Column("event_value")
    String eventValue;

    @Override
    public boolean isNew() {
        return true;
    }
}
