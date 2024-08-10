package com.nailasahidah.cashier.service;

import com.nailasahidah.cashier.domain.UserEvent;
import com.nailasahidah.cashier.enumeration.EventType;

import java.util.Collection;

public interface EventService {
    Collection<UserEvent> getEventsByUserId(Long userId);

    void addUserEvent(String email, EventType eventType, String device, String ipAddress);

    void addUserEvent(Long userId, EventType eventType, String device, String ipAddress);

}
