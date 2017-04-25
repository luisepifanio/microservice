package application.controllers.impl

import ar.com.phosgenos.dto.response.json.JSONResponse
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.functional.ChoiceHandler
import ar.com.phosgenos.functional.Either
import ar.com.phosgenos.functional.Try
import ar.com.phosgenos.rest.Controller
import com.google.inject.Inject
import groovy.sql.Sql
import groovy.util.logging.Slf4j
import io.bootique.jdbc.DataSourceFactory
import org.apache.cxf.jaxrs.ext.search.SearchCondition
import org.apache.cxf.jaxrs.ext.search.SearchParseException
import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser
import org.apache.cxf.jaxrs.ext.search.sql.SQLPrinterVisitor

import javax.sql.DataSource
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Slf4j
@Path('categories/search')
@Produces(MediaType.APPLICATION_JSON)
class CategorySearchController implements Controller {

    @Inject
    DataSourceFactory dataSourceFactory

    private final FiqlParser<Category> parser

    CategorySearchController() {
        this.parser = new FiqlParser<>(CategoryMock.class)
    }

    DataSource getDataSource() {
        return dataSourceFactory.forName("appds");
    }

    @GET
    @Path("{expression}")
    Response getBookQueryContext(@PathParam("expression") String expression) {

        Either<String, SearchParseException> choice = Try.catching(SearchParseException) {
            SearchCondition<Category> filter = parser.parse(expression)
            SQLPrinterVisitor<Category> visitor = new SQLPrinterVisitor<>("categories")
            filter.accept(visitor.visitor())

            String query = visitor.getQuery()
            log.info("[expression:$expression][query:$query]")
            return query
        }

        ChoiceHandler.on(choice)
                .onExpected { String query ->

            Sql sql = Sql.newInstance(dataSource)
            List<Category> results = sql.rows(query).collect { result ->
                Category.builder()
                        .id(result.id as Long)
                        .name(result.cat_name as String)
                        .modified(result.last_mod as Date)
                        .build()
            }

            JSONResponse.builder()
                    .data(results)
                    .build()
        }.onAlternative { SearchParseException exc ->
            JSONResponse.builder()
                    .exception(exc)
                    .build()
        }.fold()

    }

    // Dirty hack!
    static class CategoryMock {
        Long id
        String cat_name
        Date modified
    }
}
