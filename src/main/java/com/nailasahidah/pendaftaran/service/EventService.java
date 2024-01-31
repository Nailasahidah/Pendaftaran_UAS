package com.nailasahidah.pendaftaran.service;

import com.nailasahidah.pendaftaran.domain.UserEvent;
import com.nailasahidah.pendaftaran.enumeration.EventType;

import java.util.Collection;

public interface EventService {
    Collection<UserEvent> getEventsByUserId(Long userId);

    void addUserEvent(String email, EventType eventType, String device, String ipAddress);

    void addUserEvent(Long userId, EventType eventType, String device, String ipAddress);

}
