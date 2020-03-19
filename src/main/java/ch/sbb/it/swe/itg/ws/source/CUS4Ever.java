package ch.sbb.it.swe.itg.ws.source;

import ch.sbb.it.swe.itg.ws.domain.FahrtLauf;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import static ch.sbb.it.swe.itg.ws.source.CUS.ic61Template;

public class CUS4Ever implements MessageSource<FahrtLauf> {
    private int index = 0;

    @Override
    public Message<FahrtLauf> receive() {
        final FahrtLauf fahrtLauf = ic61Template();
        if (index % 2 == 0) {
            fahrtLauf.setSchluessel("IC16");
        }
        index++;
        return new GenericMessage<>(fahrtLauf);
    }
}
