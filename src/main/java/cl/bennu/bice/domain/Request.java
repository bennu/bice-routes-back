package cl.bennu.bice.domain;

import lombok.Data;

import java.util.List;

@Data
public class Request {

    private String base64;
    private List<String> base64List;

}
