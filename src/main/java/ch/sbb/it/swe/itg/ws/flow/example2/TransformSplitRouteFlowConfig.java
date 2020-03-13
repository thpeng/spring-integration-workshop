package ch.sbb.it.swe.itg.ws.flow.example2;

import ch.sbb.it.swe.itg.ws.domain.EisenbahnVerkehrsUnternehmen;
import ch.sbb.it.swe.itg.ws.domain.FahrtLauf;
import ch.sbb.it.swe.itg.ws.domain.FahrtPunkt;
import ch.sbb.it.swe.itg.ws.source.CUS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.TimeUnit;

@Configuration
public class TransformSplitRouteFlowConfig {

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(cus(), s -> s.poller(p -> p.fixedRate(1, TimeUnit.SECONDS)))
                .<FahrtLauf>filter(fahrtLauf -> fahrtLauf.getEisenbahnVerkehrsUnternehmen() == EisenbahnVerkehrsUnternehmen.SBB)
                .log(LoggingHandler.Level.INFO)
                .transform(FahrtLauf::getFahrtPunkte)
                .split()
                .<FahrtPunkt, String>route(fahrtPunkt -> fahrtPunkt.getBetriebspunkt().getBetreiber().toString().toLowerCase() + "Channel")
                .get();
    }

    @Bean
    public MessageChannel sbbChannel() {
        return MessageChannels.queue().datatype(FahrtPunkt.class).get();
    }

    @Bean
    public MessageChannel blsChannel() {
        return MessageChannels.queue().datatype(FahrtPunkt.class).get();
    }


    @Bean
    public CUS cus() {
        return new CUS();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(500);
    }
}

