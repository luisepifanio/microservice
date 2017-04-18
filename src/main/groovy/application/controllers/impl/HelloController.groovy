package application.controllers.impl

import ar.com.phosgenos.dto.ErrorDetail
import ar.com.phosgenos.dto.response.json.JSONResponseBuilder
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import ar.com.phosgenos.rest.Controller
import com.google.inject.Inject
import io.bootique.annotation.Args
import io.swagger.annotations.Api

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Api(value = "hello", description = "Simples hello response")
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class HelloController implements Controller {

    @Inject
    @Args
    private String[] args;


    @GET
    @Path("args")
    Collection<String> hello() {
        return Arrays.asList(args);
    }

    @GET
    @Path("ok")
    Response ok() {
        JSONResponseBuilder<Category> builder = JSONResponseBuilder.builder();
        Response response = builder.data(new Category(id: 1, name: 'Demo Category', modified: new Date()))
                .build()

        return response
    }

    @GET
    @Path("error")
    Response error() {
        Response response = JSONResponseBuilder.builder()
                .exception(
                ApplicationException.builder()
                        .catalogEntry(ErrorCatalog.VALIDATION_FAILED)
                        .causes([
                            ErrorDetail.builder().code('demo_error').message('Please correct the query').build()
                        ]).build()
                )
                .build()
        return response
    }

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    String ping() {
        return "pong"
    }
}
