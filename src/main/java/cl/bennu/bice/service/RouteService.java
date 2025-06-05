package cl.bennu.bice.service;

import cl.bennu.bice.domain.OpenAPI;
import cl.bennu.bice.domain.Request;
import cl.bennu.bice.domain.Response;
import cl.bennu.commons.exception.AppException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.util.*;
import java.util.regex.Pattern;


@ApplicationScoped
public class RouteService {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public Response parse(Request request) throws AppException {
        this.validate(request);

        List<String> newRoutes = new ArrayList<>();
        List<String> routesFail = new ArrayList<>();
        List<String> oldRoutes = new ArrayList<>();

        // to handle error routes
        if (StringUtils.isNotBlank(request.getRoutes())) {
            oldRoutes = decodeBase64ToRoutes(request.getRoutes());
        }

        // case where you get the base64 of the string
        if (StringUtils.isNotBlank(request.getOpenApiSpec())) {
            byte[] openapi = java.util.Base64.getDecoder().decode(request.getOpenApiSpec());
            OpenAPI openAPIProcessed = yamlProcess(openapi);
            process(openAPIProcessed, newRoutes, oldRoutes, routesFail);
        }

        // case where you get the base64 from the list
        if (CollectionUtils.isNotEmpty(request.getOpenApiSpecs())) {
            for (String base64 : request.getOpenApiSpecs()) {
                byte[] openapi = java.util.Base64.getDecoder().decode(base64);
                OpenAPI openAPIProcessed = yamlProcess(openapi);
                process(openAPIProcessed, newRoutes, oldRoutes, routesFail);
            }
        }

        if (newRoutes.isEmpty() && routesFail.isEmpty()) {
            throw new AppException("No se pudo parsear el OpenApiSpec");
        }

        Response response = new Response();
        response.setUuid(UUID.randomUUID());
        response.setNewRoutes(newRoutes);
        response.setRoutesFail(routesFail);

        LOGGER.debug("Rutas parseadas");

        return response;
    }

    private void validate(Request request) throws AppException {
        if (request == null) throw new AppException("No hay parametros de entrada");
        if (request.getOpenApiSpecs() == null && request.getOpenApiSpec() == null)
            throw new AppException("No hay parametros de entrada");
    }

    public OpenAPI yamlProcess(byte[] openapi) throws AppException {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(openapi, OpenAPI.class);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    private static void process(OpenAPI openAPI, List<String> newRoutes, List<String> oldRoutes, List<String> routesFail) {
        Pattern pattern = Pattern.compile("\\{[^}]+}");

        openAPI.getPaths().forEach((k, v) -> {
            Map<String, Object> o = openAPI.getPaths().get(k);
            o.forEach((kk, vv) -> {
                String endpoint = StringUtils.upperCase(kk) + StringUtils.SPACE + pattern.matcher(k).replaceAll("{id}");
                if (oldRoutes.contains(endpoint)) {
                    routesFail.add(StringUtils.trim(endpoint));
                } else {
                    newRoutes.add(StringUtils.trim(endpoint));
                }
            });
        });
    }

    public List<String> decodeBase64ToRoutes(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        String decodedContent = new String(decodedBytes);
        return Arrays.asList(decodedContent.split("\n"));
    }

}