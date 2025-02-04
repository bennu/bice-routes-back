package cl.bennu.bice.service;

import cl.bennu.bice.domain.OpenAPI;
import cl.bennu.bice.domain.Request;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RouteService {

    public List<String> parse(Request request) throws IOException {
        byte[] openapi = java.util.Base64.getDecoder().decode(request.getBase64());

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        OpenAPI openAPI = mapper.readValue(openapi, OpenAPI.class);
        System.out.println(openAPI);

        List<String> routes = new ArrayList<>();
        routes.add("GET /v1/domain/example");
        routes.add("GET /v1/domain/example/{id}");
        routes.add("POST /v1/domain/example");
        routes.add("PUT /v1/domain/example/{id}");
        routes.add("DELETE /v1/domain/example/{id}");

        return routes;
    }


}
