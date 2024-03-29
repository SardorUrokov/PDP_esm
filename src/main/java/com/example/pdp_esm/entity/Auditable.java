package com.example.pdp_esm.entity;

import java.util.Date;
import java.text.SimpleDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrePersist;

import lombok.Data;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import com.example.pdp_esm.entity.abstract_entity.AbstractEntity;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Auditable {

    @Column(name = "created_by")
    String name;

    @PrePersist
    public void prePersist(Object entity) {
        name = getCurrentUsername();
        log.info(
                "|" + getClassOfObject((AbstractEntity) entity)
                        + "|CREATE|" + "name:" + getObjectName((AbstractEntity) entity)
                        + "|BY:" + name + "|at:" + getDateNow()
        );
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        name = getCurrentUsername();
        log.info(
                "|" + getClassOfObject((AbstractEntity) entity)
                        + "|UPDATE|id:" + getObjectId((AbstractEntity) entity) + " name:"
                        + getObjectName((AbstractEntity) entity) + "|BY:" + name + "|at:"
                        + getDateNow());
    }

    @PreRemove
    public void preRemove(Object entity) {
        name = getCurrentUsername();
        log.info(
                "|" + getClassOfObject((AbstractEntity) entity) + "|DELETE|id:"
                        + getObjectId((AbstractEntity) entity) + " name:"
                        + getObjectName((AbstractEntity) entity) + "|BY:" + name + "|AT:" + getName()
        );
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "SYSTEM";
    }

    private String getClassOfObject(AbstractEntity abstractEntity) {
        return abstractEntity.getClassName();
    }

    private Long getObjectId(AbstractEntity abstractEntity) {
        return abstractEntity.getId();
    }

    private String getObjectName(AbstractEntity abstractEntity) {
        return abstractEntity.getName();
    }

    private String getDateNow() {
        return new SimpleDateFormat("yyyy-MM-dd hh:ss").format(new Date());
    }
}