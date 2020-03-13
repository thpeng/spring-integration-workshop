package ch.sbb.it.swe.itg.ws.flow.example2;

import ch.sbb.it.swe.itg.ws.domain.FahrtPunkt;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = { TransformSplitRouteFlowConfig.class, TransformSplitRouteFlowConfigTestIT.TestConfig.class })
@RunWith(SpringRunner.class)
public class TransformSplitRouteFlowConfigTestIT {
    @MockBean(name = "sbbOutputHandler")
    private GenericHandler<FahrtPunkt> sbbOutputHandler;

    @MockBean(name = "blsOutputHandler")
    private GenericHandler<FahrtPunkt> blsOutputHandler;


    @Test
    @SneakyThrows
    public void testFahrtPunktSplitByBetreiber() {
        //TODO countDownLatches?
        Thread.sleep(3000);
        verify(sbbOutputHandler, times(3)).handle(any(), any());
        verify(blsOutputHandler, times(3)).handle(any(), any());
    }

    @Configuration
    @EnableIntegration
    public static class TestConfig {

        @Bean
        public IntegrationFlow sbb(MessageChannel sbbChannel, GenericHandler<FahrtPunkt> sbbOutputHandler) {
            return IntegrationFlows.from(sbbChannel).log(LoggingHandler.Level.INFO).handle(sbbOutputHandler).get();
        }

        @Bean
        public IntegrationFlow bls(MessageChannel blsChannel, GenericHandler<FahrtPunkt> blsOutputHandler) {
            return IntegrationFlows.from(blsChannel).log(LoggingHandler.Level.INFO).handle(blsOutputHandler).get();
        }
    }

}
