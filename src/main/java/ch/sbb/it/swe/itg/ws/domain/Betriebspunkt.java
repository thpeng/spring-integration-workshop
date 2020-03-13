package ch.sbb.it.swe.itg.ws.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ch.sbb.it.swe.itg.ws.domain.EisenbahnVerkehrsUnternehmen.BLS;
import static ch.sbb.it.swe.itg.ws.domain.EisenbahnVerkehrsUnternehmen.SBB;

@RequiredArgsConstructor
@Getter
public enum Betriebspunkt {
    BERN(SBB),
    THUN(BLS),
    SPIEZ(BLS),
    INTERLAKEN_WEST(BLS),
    OLTEN(SBB),
    BASEL_SBB(SBB);

    private final EisenbahnVerkehrsUnternehmen betreiber;

}
