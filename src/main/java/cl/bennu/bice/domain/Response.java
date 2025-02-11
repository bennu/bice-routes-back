package cl.bennu.bice.domain;
import lombok.Data;
import java.util.List;
@Data
public class Response {
    private List<String> newRoutes;
    private List<String> routesFail;
}