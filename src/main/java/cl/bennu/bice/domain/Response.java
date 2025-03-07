package cl.bennu.bice.domain;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Response {

    private UUID uuid;
    private List<String> newRoutes;
    private List<String> routesFail;

}