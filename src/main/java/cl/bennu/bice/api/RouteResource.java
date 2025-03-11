package cl.bennu.bice.api;

import cl.bennu.bice.domain.Request;
import cl.bennu.bice.service.RouteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;

@Path("/v1/routes")
@Consumes(MediaType.APPLICATION_JSON)
public class RouteResource {

    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private @Inject RouteService routeService;

    @SneakyThrows
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response parse(Request request) {
        LOGGER.info("Solicitud de parseo recibida.");
        cl.bennu.bice.domain.Response response = routeService.parse(request);
        return Response.ok(response).build();
    }

}
