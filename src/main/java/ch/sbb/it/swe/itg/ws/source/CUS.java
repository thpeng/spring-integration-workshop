package ch.sbb.it.swe.itg.ws.source;

import ch.sbb.it.swe.itg.ws.domain.Betriebspunkt;
import ch.sbb.it.swe.itg.ws.domain.EisenbahnVerkehrsUnternehmen;
import ch.sbb.it.swe.itg.ws.domain.FahrtLauf;
import ch.sbb.it.swe.itg.ws.domain.FahrtPunkt;
import com.google.common.collect.Lists;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.time.LocalTime;
import java.util.List;

public class CUS implements MessageSource<FahrtLauf> {

    List<FahrtLauf> lauefe = Lists.newArrayList(
            ic61Template(), cargoTemplate());

    int index = 0;

    @Override
    public Message<FahrtLauf> receive() {
        if(index < lauefe.size()){
            return new GenericMessage<>(lauefe.get(index++));
        }
        return null;
    }


    private FahrtLauf cargoTemplate() {
        return FahrtLauf.builder()
                .schluessel("cargo")
                .fahrtPunkte(Lists.newArrayList(FahrtPunkt.builder().betriebspunkt(Betriebspunkt.INTERLAKEN_WEST)
                                .build(),
                        FahrtPunkt.builder().betriebspunkt(Betriebspunkt.BASEL_SBB)
                                .build()))
                .build();
    }

    private FahrtLauf ic61Template() {
        return FahrtLauf.builder()
                .schluessel("IC61")
                .eisenbahnVerkehrsUnternehmen(EisenbahnVerkehrsUnternehmen.SBB)
                .fahrtPunkte(Lists.newArrayList(FahrtPunkt.builder()
                                .betriebspunkt(Betriebspunkt.INTERLAKEN_WEST)
                                .anKursbuch(LocalTime.of(10, 33)).build(),
                        FahrtPunkt.builder()
                                .betriebspunkt(Betriebspunkt.SPIEZ)
                                .anKursbuch(LocalTime.of(10, 52)).build(),
                        FahrtPunkt.builder()
                                .betriebspunkt(Betriebspunkt.THUN)
                                .anKursbuch(LocalTime.of(11, 3)).build(),
                        FahrtPunkt.builder()
                                .betriebspunkt(Betriebspunkt.BERN)
                                .anKursbuch(LocalTime.of(11, 24)).build(),
                        FahrtPunkt.builder()
                                .betriebspunkt(Betriebspunkt.OLTEN)
                                .anKursbuch(LocalTime.of(12, 3)).build(),
                        FahrtPunkt.builder()
                                .betriebspunkt(Betriebspunkt.BASEL_SBB)
                                .anKursbuch(LocalTime.of(12, 32)).build()))
                .build();
    }
}
