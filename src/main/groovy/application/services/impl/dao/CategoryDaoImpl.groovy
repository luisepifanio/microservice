package application.services.impl.dao

import ar.com.phosgenos.dao.AbstractDAO
import ar.com.phosgenos.entities.Category
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
        return null
    }

    @Override
    Category read(Long id) {
        Sql sql = Sql.newInstance(dataSource)
        //ExecutionContext.readOnWrite(AppConstants.CURRENT_TX, Sql.newInstance(dataSource))
        List results = sql.rows("""
            SELECT  id as "id",
                    cat_name as "name",
                    last_mod as "modified"
            FROM categories
            WHERE id = :category
            """, [category: id])

        if (results) {
            return results.collect {
                Category.builder()
                        .id(it.id as Long)
                        .name(it.name as String)
                        .modified(it.modified as Date)
                        .build()
            }.get(0)
        }

        return null
    }

    @Override
    Category update(Category category) {
        return null
    }

    @Override
    boolean delete(Category category) {
        return false
    }

    @Override
    boolean delete(Long id) {
        return false
    }
}
