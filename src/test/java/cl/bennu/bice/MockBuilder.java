package cl.bennu.bice;

import cl.bennu.bice.domain.Request;

import java.util.ArrayList;
import java.util.List;

public class MockBuilder {

    private static final String OPENAPI_SPEC_1 = "LS0tDQpvcGVuYXBpOiAzLjEuMA0KY29tcG9uZW50czoNCiAgc2NoZW1hczoNCiAgICBSZXF1ZXN0Og0KICAgICAgdHlwZTogb2JqZWN0DQogICAgICBwcm9wZXJ0aWVzOg0KICAgICAgICByb3V0ZXM6DQogICAgICAgICAgdHlwZTogc3RyaW5nDQogICAgICAgIG9wZW5BcGlTcGVjczoNCiAgICAgICAgICB0eXBlOiBhcnJheQ0KICAgICAgICAgIGl0ZW1zOg0KICAgICAgICAgICAgdHlwZTogc3RyaW5nDQpwYXRoczoNCiAgL3YxL3JvdXRlczoNCiAgICBwYXRjaDoNCiAgICAgIHJlcXVlc3RCb2R5Og0KICAgICAgICBjb250ZW50Og0KICAgICAgICAgIGFwcGxpY2F0aW9uL2pzb246DQogICAgICAgICAgICBzY2hlbWE6DQogICAgICAgICAgICAgICRyZWY6ICIjL2NvbXBvbmVudHMvc2NoZW1hcy9SZXF1ZXN0Ig0KICAgICAgICByZXF1aXJlZDogdHJ1ZQ0KICAgICAgcmVzcG9uc2VzOg0KICAgICAgICAiMjAwIjoNCiAgICAgICAgICBkZXNjcmlwdGlvbjogT0sNCiAgICAgIHN1bW1hcnk6IFBhcnNlDQogICAgICB0YWdzOg0KICAgICAgICAtIFJvdXRlIFJlc291cmNlDQppbmZvOg0KICB0aXRsZTogcm91dGVzIEFQSQ0KICB2ZXJzaW9uOiAwLjAuMQ0K";
    private static final String ROUTES = "UEFUQ0ggL3YxL3JvdXRlcw==";
    //private static final String OPENAPI_SPEC_2 = "";
    private static final List<String> OPEN_API_SPECS = new ArrayList<>();

    static {
        OPEN_API_SPECS.add(OPENAPI_SPEC_1);
        //OPEN_API_SPECS.add(OPENAPI_SPEC_2);
    }

    public static Request buildSingleRequest() {
        Request request = new Request();
        request.setOpenApiSpecs(OPEN_API_SPECS);
        return request;
    }

    public static Request buildFullRequest() {
        Request request = buildSingleRequest();
        request.setRoutes(ROUTES);
        return request;
    }

}
