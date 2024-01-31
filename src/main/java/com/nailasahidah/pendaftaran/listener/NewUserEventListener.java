package com.nailasahidah.pendaftaran.listener;

import com.nailasahidah.pendaftaran.event.NewUserEvent;
import com.nailasahidah.pendaftaran.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import static com.nailasahidah.pendaftaran.utils.RequestUtils.getDevice;
import static com.nailasahidah.pendaftaran.utils.RequestUtils.getIpAddress;
@Component
@RequiredArgsConstructor
public class NewUserEventListener {
    private final EventService eventService;
    private final HttpServletRequest request;

    @EventListener
    public void onNewUserEvent(NewUserEvent event) {
        eventService.addUserEvent(event.getEmail(), event.getType(), getDevice(request), getIpAddress(request));
    }
}
