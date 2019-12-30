package copy.base.pusher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClientMessageListener {
    private static final Logger log = LoggerFactory.getLogger(ClientMessageListener.class);

    public void receiveMessage(Object message) {
        log.info("Received <" + message +">");
    }
}
