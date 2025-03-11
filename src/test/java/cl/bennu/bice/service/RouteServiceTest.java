package cl.bennu.bice.service;

import cl.bennu.bice.MockBuilder;
import cl.bennu.bice.domain.Request;
import cl.bennu.bice.domain.Response;
import cl.bennu.commons.exception.AppException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RouteServiceTest {

    private @InjectMocks RouteService routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void parse_ShouldWork() throws AppException {
        Request request = MockBuilder.buildSingleRequest();
        System.out.println(request);
        Response response = routeService.parse(request);

        System.out.println(response);

        assertNotNull(response);
    }

    @Test
    void parse_ShouldWork2() throws AppException {
        Request request = MockBuilder.buildFullRequest();
        System.out.println(request);
        Response response = routeService.parse(request);

        System.out.println(response);

        assertNotNull(response);
    }

    @Test
    void parse_ShouldWorkThrowExceptionOnException() throws AppException {
        Request request = new Request();
        Assertions.assertThrows(AppException.class, () -> {
            routeService.parse(request);
        });
    }

}
