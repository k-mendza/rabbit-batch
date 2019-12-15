package copy.base.fetcher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientItemWriteListener implements ItemWriteListener<Client> {
    private static final Logger log = LoggerFactory.getLogger(ClientItemWriteListener.class);


    @Override
    public void beforeWrite(List<? extends Client> items) {
        log.info("Before write. List size: " + items.size());
        log.info("First client: " + items.get(0));
    }

    @Override
    public void afterWrite(List<? extends Client> items) {

    }

    @Override
    public void onWriteError(Exception exception, List<? extends Client> items) {

    }
}
