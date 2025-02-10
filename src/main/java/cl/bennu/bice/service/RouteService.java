package cl.bennu.bice.service;

import cl.bennu.bice.domain.OpenAPI;
import cl.bennu.bice.domain.Request;
import cl.bennu.bice.domain.Response;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;



@ApplicationScoped
public class RouteService {

    public List<String> parse(Request request) throws IOException {

        List<String> newRoutes = new ArrayList<>();
        List<String> routesFail = new ArrayList<>();

        System.out.println("llega al servicio parse");

        //rutas antiguas del request
        List<String> oldRoutes = decodeBase64ToRoutes(request.getBase64());
        System.out.println("oldRoutes : "+oldRoutes);

        for(String base64: request.getBase64List()) {
           byte[] openapi = java.util.Base64.getDecoder().decode(base64);
           OpenAPI openAPIProcessed = yamlProcess(openapi);
           process(openAPIProcessed, newRoutes, oldRoutes, routesFail);
        }
        System.out.println("rutas fallidas en parse "+routesFail);


        return newRoutes;
    }

    public OpenAPI yamlProcess(byte[] openapi) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); //permite leer y deserializa archivos en formatos yaml
            mapper.findAndRegisterModules(); //indica al mapper que busque, registre y trabaje con tipos de datos especificos.
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //configura al mapper para que este no falle si es que encuentra propiedades  en el YAML que no estan definidas en openApi

            OpenAPI openAPI = mapper.readValue(openapi, OpenAPI.class);
            System.out.println(openAPI);

            return openAPI;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void process(OpenAPI openAPI, List<String> newRoutes, List<String> oldRoutes, List<String> routesFail) throws IOException {

        Pattern pattern = Pattern.compile("\\{[^}]+}");

        openAPI.getPaths().forEach((k, v) -> {
            Map<String, Object> o = openAPI.getPaths().get(k);
            o.forEach((kk, vv) -> {
                String endpoint = StringUtils.upperCase(kk) + StringUtils.SPACE + pattern.matcher(k).replaceAll("{id}");
                System.out.println("formato del endpoint :"+endpoint);
                if (oldRoutes.contains(endpoint)) {
                    System.out.println("dentro del if");
                    routesFail.add(StringUtils.trim(endpoint));
                } else {
                    System.out.println("dentro del else");
                    newRoutes.add(StringUtils.trim(endpoint));
                }

            });
        });
    }

    public List<String> decodeBase64ToRoutes(String base64) {

        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        String decodedContent = new String(decodedBytes);
        List<String> oldRoutes = Arrays.asList(decodedContent.split("\n"));

        return oldRoutes;
    }

}
