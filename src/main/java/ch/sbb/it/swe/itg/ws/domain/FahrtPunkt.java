package ch.sbb.it.swe.itg.ws.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.LocalTime;

@Data
@Builder
public class FahrtPunkt {
    private LocalTime anKursbuch;
    private LocalTime anErwartet;
    private Betriebspunkt betriebspunkt;
}
