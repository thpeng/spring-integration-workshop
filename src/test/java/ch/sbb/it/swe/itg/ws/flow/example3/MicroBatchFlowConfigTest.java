package ch.sbb.it.swe.itg.ws.flow.example3;

import ch.sbb.it.swe.itg.ws.domain.FahrtLauf;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = { MicroBatchFlowConfig.class, MicroBatchFlowConfigTest.TestConfig.class })
@RunWith(SpringRunner.class)
public class MicroBatchFlowConfigTest {
    @Configuration
    @EnableIntegration
    public static class TestConfig {
    }

    @MockBean
    private GenericHandler<FahrtLauf> outputHandler;

    @Captor
    private ArgumentCaptor<FahrtLauf> outputCaptor;


    @Test
    @SneakyThrows
    public void testMicroBatch() {
        Thread.sleep(3500);
        verify(outputHandler, times(2)).handle(outputCaptor.capture(), any());

        assertThat(outputCaptor.getAllValues()
                .stream()
                .map(FahrtLauf::getSchluessel)
                .collect(Collectors.toList())
        ).containsExactlyInAnyOrder("IC61", "IC16");
    }

}
