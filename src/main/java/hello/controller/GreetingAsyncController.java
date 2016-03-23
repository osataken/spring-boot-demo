package hello.controller;

import hello.models.Greeting;
import hello.service.ILongRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingAsyncController {
    @Autowired
    private ILongRunService longRunService;

    @RequestMapping("/greeting-async")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        longRunService.doStuff(name);
        return new Greeting("Your request has been queued");
    }
}
