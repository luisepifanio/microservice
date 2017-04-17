package ar.com.phosgenos.rest

import ar.com.phosgenos.dao.IdentifiableEntity

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
interface EntityController<PK,T extends IdentifiableEntity<PK>> extends Controller{
    @POST
    Response create(final T t)
    @GET
    @Path("{id}")
    Response read(@PathParam("id") final PK id)
    @PUT
    @Path("{id}")
    Response update(@PathParam("id") final PK id, final T t)
    @DELETE
    @Path("{id}")
    Response delete(@PathParam("id") final PK id)
}
