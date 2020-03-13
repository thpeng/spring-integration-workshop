package ch.sbb.it.swe.itg.ws.flow.example1;

import ch.sbb.it.swe.itg.ws.domain.EisenbahnVerkehrsUnternehmen;
import ch.sbb.it.swe.itg.ws.domain.FahrtLauf;
import ch.sbb.it.swe.itg.ws.source.CUS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;

import java.util.concurrent.TimeUnit;

@Configuration
public class FilterFlowConfig {

    @Bean
    public IntegrationFlow integrationFlow(GenericHandler<FahrtLauf> fahrtLaufHandler) {
        return IntegrationFlows.from(cus(), s -> s.poller(p -> p.fixedRate(1, TimeUnit.SECONDS)))
                // This should be added in the workshop
                .<FahrtLauf>filter(fahrtLauf -> fahrtLauf.getEisenbahnVerkehrsUnternehmen() == EisenbahnVerkehrsUnternehmen.SBB)
                .log(LoggingHandler.Level.INFO)
                .handle(fahrtLaufHandler)
                .get();
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

