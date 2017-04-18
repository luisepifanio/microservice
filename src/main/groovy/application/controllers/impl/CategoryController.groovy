package application.controllers.impl

import application.services.definitions.CategoryService
import ar.com.phosgenos.dto.ErrorDetail
import ar.com.phosgenos.dto.response.json.JSONResponse
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import ar.com.phosgenos.functional.ChoiceHandler
import ar.com.phosgenos.functional.Either
import ar.com.phosgenos.rest.EntityController
import com.google.inject.Inject
import groovy.util.logging.Slf4j
import org.eclipse.jetty.http.HttpStatus

import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Slf4j
@Path('categories')
class CategoryController implements EntityController<Long, Category> {

    public static final PATH = 'categories'

    @Inject
    CategoryService categoryService

    @POST
    @Override
    Response create(Category category) {

        Either<Category, ApplicationException> choice = categoryService.create(category)

        ChoiceHandler.on(choice)
                .onExpected { Category cat ->
            Response.status(HttpStatus.CREATED_201)
                    .header('Location', "$PATH/${cat.id}")
                    .build()
        }.onAlternative { ApplicationException exc ->
            JSONResponse.builder()
                    .exception(exc)
                    .build()
        }.fold()

    }

    @GET
    @Path("{id}")
    @Override
    Response read(@PathParam("id") Long id) {
        log.debug("${getClass().name}:read:$id")
        Either<Category, ApplicationException> choice = categoryService.findById(id)

        ChoiceHandler.on(choice)
            .onExpected { Category cat ->
                JSONResponse.builder().data(cat).build()
            }.onAlternative { ApplicationException exc ->
                JSONResponse.builder().exception(exc).build()
            }.fold()

    }

    @PUT
    @Path("{id}")
    @Override
    Response update(@PathParam("id") Long id, Category category) {

        Category local = Category.builder()
            .id(id)
            .name(category.name)
            .modified(category.modified)
        .build()

        Either<Category, ApplicationException> choice = categoryService.update(local)
        ChoiceHandler.on(choice)
            .onExpected { Category result ->
                JSONResponse.builder().data(result).build()
            }.onAlternative { ApplicationException exc ->
                JSONResponse.builder().exception(exc).build()
            }.fold()

    }

    @DELETE
    @Path("{id}")
    @Override
    Response delete(@PathParam("id") Long id) {

        Either<Boolean,ApplicationException> choice = categoryService.remove(id)

        ChoiceHandler.on(choice)
            .onExpected { Boolean result ->
                if (result) {
                    return Response.status(HttpStatus.NO_CONTENT_204).build()
                }
                Exception th = new ApplicationException(
                        "Resource $id was found",
                        null,
                        ErrorCatalog.VALIDATION_FAILED,
                        [ErrorDetail.builder().code('not_found').message("Resource $id was found").build()]
                )
                return JSONResponse.builder().exception(th).build()
            }.onAlternative { ApplicationException exc ->
                JSONResponse.builder().exception(exc).build()
            }.fold()

    }
}
