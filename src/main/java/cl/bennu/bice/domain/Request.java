package cl.bennu.bice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Request {

    private String routes;
    private List<String> openApiSpecs;

}
