package cl.bennu.bice.api;

import cl.bennu.bice.domain.Request;
import cl.bennu.bice.service.RouteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Path("/v1/routes")
@Consumes(MediaType.APPLICATION_JSON)
public class RouteResource {

    private static final Logger logger = LoggerFactory.getLogger(RouteResource.class);
    private @Inject RouteService routeService;

    @SneakyThrows
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response parse(Request request) {
        try {
            logger.info("Solicitud de parseo recibida.");
            List<String> routes = routeService.parse(request);
            return Response.ok(routes).build();
        } catch (Exception e) {
            logger.error("Error al parsear las rutas: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al parsear las rutas: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile() {
        try {
            logger.info("Solicitud de descarga recibida.");

            ByteArrayOutputStream fileContent = routeService.saveFile();
            InputStream fileInputStream = new ByteArrayInputStream(fileContent.toByteArray());

            logger.info("Archivo generado correctamente y enviado al cliente.");

            return Response.ok(fileInputStream)
                    .header("Content-Disposition", "attachment; filename=\"new_routes.txt\"")
                    .build();
        } catch (Exception e) {
            logger.error("Error al generar el archivo de descarga: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar el archivo: " + e.getMessage())
                    .build();
        }
    }

}
