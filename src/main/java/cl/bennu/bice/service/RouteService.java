package cl.bennu.bice.service;

import cl.bennu.bice.domain.OpenAPI;
import cl.bennu.bice.domain.Request;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@ApplicationScoped
public class RouteService {

    public List<String> parse(Request request) throws IOException {

        //List<String> routesTest = new ArrayList<>();
        List<String> routesFinal = new ArrayList<>();
        System.out.println("llega al servicio parse");

        for(String base64: request.getBase64List()) {
           byte[] openapi = java.util.Base64.getDecoder().decode(base64);
           OpenAPI openAPIProcessed = yamlProcess(openapi);
            process(openAPIProcessed, routesFinal);
        }


        return routesFinal; //devuelve la lista de rutas construidas del Base64
    }

    public OpenAPI yamlProcess(byte[] openapi) throws IOException {
        //CONFIG DEL OBJECTMAPPER PARA YAML
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); //permite leer y deserializa archivos en formatos yaml
        mapper.findAndRegisterModules(); //indica al mapper que busque, registre y trabaje con tipos de datos especificos.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //configura al mapper para que este no falle si es que encuentra propiedades  en el YAML que no estan definidas en openApi

        //DESERIALIZACION DEL YAML
        OpenAPI openAPI = mapper.readValue(openapi, OpenAPI.class);
        System.out.println(openAPI);

        return openAPI;
    }

    private static void process(OpenAPI openAPI, List<String> routesFinal) {
        Pattern pattern = Pattern.compile("\\{[^}]+}");

        openAPI.getPaths().forEach((k, v) -> {
            Map<String, Object> o = openAPI.getPaths().get(k);
            o.forEach((kk, vv) -> {
                String endpoint = StringUtils.upperCase(kk) + StringUtils.SPACE + pattern.matcher(k).replaceAll("{id}");
                routesFinal.add(StringUtils.trim(endpoint));

                //CONDICION SE QUITO YA QUE NO SE ESTA HACIENDO LA COMPARACION CON LAS RUTAS ANTIGUAS

            });
        });
    }


}
