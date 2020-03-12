package ch.sbb.it.swe.itg.ws.flow;

import ch.sbb.it.swe.itg.ws.source.CUS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;

import java.util.concurrent.TimeUnit;

@Configuration
public class FlowConfig {

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(cus(), s -> s.poller(p -> p.fixedRate(10, TimeUnit.SECONDS)))
                .log(LoggingHandler.Level.INFO)
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

