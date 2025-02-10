package cl.bennu.bice.api;

import cl.bennu.bice.domain.Request;
import cl.bennu.bice.service.RouteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

import java.util.List;

@Path("/v1/routes")
@Consumes(MediaType.APPLICATION_JSON)
public class RouteResource {

    private @Inject RouteService routeService;

    @SneakyThrows
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response parse(Request request) {
        System.out.println("llegue al endpoint");
        List<String> routes = routeService.parse(request);

        return Response.ok(routes).build();
    }

}
