package ch.sbb.it.swe.itg.ws.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FahrtLauf {
    private List<FahrtPunkt> fahrtPunkte;
    private String schluessel;
    private Betriebspunkt halt;
    private EisenbahnVerkehrsUnternehmen eisenbahnVerkehrsUnternehmen;
}
