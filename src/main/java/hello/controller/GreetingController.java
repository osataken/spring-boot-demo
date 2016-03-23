package hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import hello.models.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    private SimpMessagingTemplate messageTemplate;

    @Autowired
    public GreetingController(SimpMessagingTemplate template) {
        this.messageTemplate = template;
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        String greetingResponse = String.format(template, name);
        this.messageTemplate.convertAndSend("/topic/greetings", new Greeting(greetingResponse));
        return new Greeting(greetingResponse);
    }
}
