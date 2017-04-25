package application.services.impl.dao

import ar.com.phosgenos.dao.AbstractDAO
import ar.com.phosgenos.dto.ErrorDetail
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.util.logging.Slf4j

@Slf4j
class CategoryDaoImpl extends AbstractDAO<Category, Long> {

    // Never forget no args constructor
    CategoryDaoImpl() {
        super()
    }

    @Override
    Category create(Category category) {
        Sql sql = Sql.newInstance(dataSource)

        List<List<Object>> generatedKeys = sql.executeInsert(
        category.asMap(),
        '''
        INSERT INTO categories ( cat_name, last_mod )
        VALUES ( :name , CURRENT_TIMESTAMP() )
        ''',
        ['id']
        )

        int updateCount = sql.updateCount

        //One of next conditions should be always true
        Map<Closure<Boolean>, Closure<Category>> chooseYourDestiny = [:]
        chooseYourDestiny << [{ int rowsUpdated -> rowsUpdated == 1 }: { ->
            int id = generatedKeys.first().find { it.key in ['SCOPE_IDENTITY()'] }.value

            // return category.asBuilder()
            //         .id(id as Long)
            //         .build() // Not the same object
            return read(id)
        }]
        chooseYourDestiny << [{ int rowsUpdated -> true }: { ->
            throw ApplicationException.builder()
                    .catalogEntry(ErrorCatalog.OPERATION_FAILED)
                    .causes([
                    ErrorDetail.builder()
                            .field('id')
                            .code('not.created.entity')
                            .build()
            ]).build()
        }]

        return chooseYourDestiny.find { condition, value -> condition(updateCount) }
                .value() // Read as value.call() if goes well we have a category otherwise an error
    }

    @Override
    Category read(Long id) {
        Sql sql = Sql.newInstance(dataSource)
        //ExecutionContext.readOnWrite(AppConstants.CURRENT_TX, Sql.newInstance(dataSource))
        GroovyRowResult result = sql.firstRow("""
            SELECT  id as "id",
                    cat_name as "name",
                    last_mod as "modified"
            FROM categories
            WHERE id = :category
            """, [category: id])

        if (result) {
            return Category.builder()
                    .id(result.id as Long)
                    .name(result.name as String)
                    .modified(result.modified as Date)
                .build()
        }

        throw ApplicationException.builder()
                .catalogEntry(ErrorCatalog.NOT_FOUND)
                .causes([
                    ErrorDetail.builder()
                        .field('id')
                        .code('not.found.entity')
                        .build()
                ]).build()
    }

    @Override
    Category update(final Category category) {
        Sql sql = Sql.newInstance(dataSource)

        // Groovy's Sql seems not to support named parameters
        int updateCount = sql.executeUpdate('''
        UPDATE categories
        SET 
            cat_name = ?,
            last_mod = CURRENT_TIMESTAMP()
        WHERE id = ?
        ''', [ category.name, category.id ])

        //One of next conditions should be always true
        Map<Closure<Boolean>, Closure<Category>> chooseYourDestiny = [:]
        chooseYourDestiny << [{ int rowsUpdated -> rowsUpdated == 1 }: { ->
            // return category
            return read(category.id)
        }]
        chooseYourDestiny << [{ int rowsUpdated -> rowsUpdated < 1 }: { ->
            throw ApplicationException.builder()
                    .catalogEntry(ErrorCatalog.OPERATION_FAILED)
                    .causes([
                    ErrorDetail.builder()
                            .field('id')
                            .code('not.found.entity')
                            .build()
            ])
                    .build()
        }]
        chooseYourDestiny << [{ int rowsUpdated -> rowsUpdated > 1 }: { ->
            throw ApplicationException.builder()
                    .catalogEntry(ErrorCatalog.OPERATION_FAILED)
                    .causes([
                    ErrorDetail.builder()
                            .field('id')
                            .code('multiple.update.entity')
                            .build()
            ])
                    .build()
        }]

        return chooseYourDestiny.find { condition, value -> condition(updateCount) }
                .value() // Read as value.call() if goes well we have a category otherwise an error
    }

    @Override
    boolean delete(Category category) {
        this.delete(category.id)
    }

    @Override
    boolean delete(Long id) {
        Sql sql = Sql.newInstance(dataSource)

        sql.execute("""
            DELETE FROM categories
            WHERE id = :id
        """, [id: id])
        int updateCount = sql.updateCount

        //One of next conditions should be always true
        Map<Closure<Boolean>, Closure<Boolean>> chooseYourDestiny = [:]
        chooseYourDestiny << [{ int rowsUpdated -> rowsUpdated == 1 }: { -> return true }]
        chooseYourDestiny << [{ int rowsUpdated -> rowsUpdated == 0 }: { -> return false }]
        chooseYourDestiny << [{ int rowsUpdated -> true }: { ->
            throw ApplicationException.builder()
                    .catalogEntry(ErrorCatalog.NOT_FOUND)
                    .causes([
                    ErrorDetail.builder()
                            .field('id')
                            .code('multiple.deleted.entity')
                            .build()
            ])
                    .build()
        }]

        return chooseYourDestiny.find { condition, value -> condition(updateCount) }
                .value() // Read as value.call() if goes well we have a category otherwise an error
    }
}
