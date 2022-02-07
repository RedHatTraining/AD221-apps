// TODO: Add Camel K imports

// TODO: Add Camel K resources

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ManuscriptsApi extends RouteBuilder {

    private final List<Object> inMemoryBooks = new ArrayList<>();

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.json);

        // TODO: Add a route to store all books in the local variable

        rest("/manuscripts")
            .get()
                .route()
                .routeId("rest-manuscripts")
                .setBody(exchange -> inMemoryBooks)
            .endRest();
    }
}
