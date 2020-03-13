package ch.sbb.it.swe.itg.ws.flow.example1;

import ch.sbb.it.swe.itg.ws.domain.EisenbahnVerkehrsUnternehmen;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = { FilterFlowConfig.class, FilterFlowConfigTestIT.TestConfig.class })
@RunWith(SpringRunner.class)
public class FilterFlowConfigTestIT {

    @MockBean
    private GenericHandler<FahrtLauf> outputHandler;

    @Captor
    private ArgumentCaptor<FahrtLauf> outputCaptor;

    @Test
    @SneakyThrows
    public void testFilterCargo() {
        //TODO countDownLatches?
        Thread.sleep(3000);
        verify(outputHandler, times(1)).handle(outputCaptor.capture(), any());
        assertThat(outputCaptor.getValue().getEisenbahnVerkehrsUnternehmen()).isEqualTo(EisenbahnVerkehrsUnternehmen.SBB);
        assertThat(outputCaptor.getValue().getSchluessel()).isEqualTo("IC61");

    }

    @Configuration
    @EnableIntegration
    public static class TestConfig{

    }

}
