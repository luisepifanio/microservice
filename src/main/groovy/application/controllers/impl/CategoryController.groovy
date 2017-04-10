package application.controllers.impl

import application.services.definitions.CategoryServiceDefinition
import ar.com.phosgenos.dto.ErrorDetail
import ar.com.phosgenos.dto.response.json.JSONResponse
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import ar.com.phosgenos.rest.EntityController
import com.google.inject.Inject
import com.google.inject.name.Named
import groovy.util.logging.Slf4j
import org.eclipse.jetty.http.HttpStatus

import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Slf4j
@Path('categories')
class CategoryController implements EntityController<Long,Category>{

    public static final PATH = 'categories'

    @Inject @Named('categoryService')
    CategoryServiceDefinition categoryService

    @Override
    Response create(Category category) {
        try{
            Category result = categoryService.create(category)
            Response.status(HttpStatus.CREATED_201)
                    .header('Location',"$PATH/${result.id}")
                    .build()
        }catch(ApplicationException exception){
            JSONResponse.builder().exception(exception).build()
        }
    }

    @Override
    Response read(@PathParam("id") Long id) {
        try{
            Category result = categoryService.findById(id)
            JSONResponse.builder().data(result).build()
        }catch(ApplicationException exception){
            JSONResponse.builder().exception(exception).build()
        }
    }

    @Override
    Response update(@PathParam("id") Long id, Category category) {
        try{
            category.setId(id)
            Category result = categoryService.update(category)
            JSONResponse.builder().data(result).build()
        }catch(ApplicationException exception){
            JSONResponse.builder().exception(exception).build()
        }
    }

    @Override
    Response delete(@PathParam("id") Long id) {

        try{
            boolean succeeded = categoryService.remove(id)
            if(succeeded){
                return Response.status(HttpStatus.NO_CONTENT_204)
                        .build()
            }
            throw new ApplicationException(
                    "Resource $id was found",
                    null,
                    ErrorCatalog.VALIDATION_FAILED,
                    [ErrorDetail.builder().code('not_found').message("Resource $id was found").build()]
            )
        }catch(ApplicationException exception){
            JSONResponse.builder().exception(exception).build()
        }
    }
}
