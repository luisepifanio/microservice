package ar.com.phosgenos

import ar.com.phosgenos.dto.ErrorDetail
import ar.com.phosgenos.dto.response.json.JSONResponseBuilder
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import com.google.inject.Inject
import io.bootique.annotation.Args
import io.swagger.annotations.Api

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Api(value = "hello", description = "Simples hello response")
@Path("/")
class HelloResource {

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
        JSONResponseBuilder<Category> builder = JSONResponseBuilder.builder()
                .data(new Category(id: 1, name: 'Demo Category', modified: new Date()))
                .build()
    }

    @GET
    @Path("error")
    Response error() {
        JSONResponseBuilder<Category> builder = JSONResponseBuilder.builder()
                .exception(ApplicationException.builder()
                        .catalogEntry(ErrorCatalog.VALIDATION_FAILED)
                        .causes([
                            ErrorDetail.builder().code('demo_error').message('Please correct the query').build()
                        ]).build())
                .build()
    }

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    String ping() {
        return "pong"
    }
}
