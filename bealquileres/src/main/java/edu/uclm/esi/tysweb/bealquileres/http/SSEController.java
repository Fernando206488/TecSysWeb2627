package edu.uclm.esi.tysweb.bealquileres.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import edu.uclm.esi.tysweb.bealquileres.services.SseService;

@RestController
@RequestMapping("/api/sse")
public class SSEController {

    @Autowired 
    private SseService service;

    @GetMapping(value = "/events/{municipio}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter events(@PathVariable String municipio) {
        return service.subscribe(municipio);
    }
}