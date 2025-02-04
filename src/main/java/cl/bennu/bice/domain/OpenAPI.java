package cl.bennu.bice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class OpenAPI {

    private String openapi;
    private Info info;
    private Map<String, Map<String, Object>> paths;

}
