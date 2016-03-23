package hello.service;

import hello.models.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LongRunServiceImpl implements ILongRunService {
    private static final String template = "Hello async, %s!";
    private SimpMessagingTemplate messageTemplate;

    @Autowired
    public LongRunServiceImpl(SimpMessagingTemplate template) {
        this.messageTemplate = template;
    }

    @Async("workExecutor")
    @Override
    public void doStuff(String name) {
        try {
            Thread.sleep(10 * 1000);

            String greetingResponse = String.format(template, name);
            this.messageTemplate.convertAndSend("/topic/greetings", new Greeting(greetingResponse));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
