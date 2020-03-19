package ch.sbb.it.swe.itg.ws.flow.example3;

import ch.sbb.it.swe.itg.ws.domain.FahrtLauf;
import ch.sbb.it.swe.itg.ws.source.CUS4Ever;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.TimeoutCountSequenceSizeReleaseStrategy;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class MicroBatchFlowConfig {

    @Bean
    public IntegrationFlow integrationFlow(GenericHandler<FahrtLauf> outputHandler) {
        return IntegrationFlows.from(cus(), s -> s.poller(p -> p.fixedRate(1, TimeUnit.SECONDS)))
                .log(LoggingHandler.Level.DEBUG, getClass().getName() + ".preAggregate")
                .aggregate(aggregatorSpec -> aggregatorSpec
                        .correlationStrategy( message -> ((FahrtLauf)message.getPayload()).getSchluessel())
                        .releaseStrategy(new TimeoutCountSequenceSizeReleaseStrategy(2, 10_000))
                )
                .log(LoggingHandler.Level.DEBUG, getClass().getName() + ".postAggregate")
                .<List<FahrtLauf>, Set<FahrtLauf>>transform(source -> Sets.newHashSet(source))
                .log(LoggingHandler.Level.INFO)
                .split()
                .log(LoggingHandler.Level.INFO)
                .handle(outputHandler)
                .get();
    }


    @Bean
    public CUS4Ever cus() {
        return new CUS4Ever();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(500);
    }
}

