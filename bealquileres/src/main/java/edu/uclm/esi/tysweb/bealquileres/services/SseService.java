package edu.uclm.esi.tysweb.bealquileres.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseService {
    private final Map<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String key) {
        SseEmitter emitter = new SseEmitter(0L);
        emitters
                .computeIfAbsent(key, k -> new CopyOnWriteArrayList<>())
                .add(emitter);
        emitter.onCompletion(() -> remove(key, emitter));
        emitter.onTimeout(() -> remove(key, emitter));
        emitter.onError(e -> remove(key, emitter));
        return emitter;
    }

    public void send(String key, String eventName, Object data) {
        List<SseEmitter> clients = emitters.get(key);
        if (clients == null) 
            return;

        for (SseEmitter emitter : clients) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name(eventName)
                                .data(data));
            } catch (IOException e) {
                remove(key, emitter);
            }
        }
    }

    public void complete(String key) {
        List<SseEmitter> clients = emitters.remove(key);
        if (clients == null) 
            return;

        for (SseEmitter emitter : clients) 
            emitter.complete();
    }

    private void remove(String key, SseEmitter emitter) {
        List<SseEmitter> clients = emitters.get(key);
        if (clients == null) 
            return;

        clients.remove(emitter);

        if (clients.isEmpty()) 
            emitters.remove(key);
    }
}