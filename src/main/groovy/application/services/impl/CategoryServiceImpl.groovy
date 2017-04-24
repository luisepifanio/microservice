package application.services.impl

import application.services.definitions.CategoryService
import ar.com.phosgenos.dao.ExtendedGenericDAO
import ar.com.phosgenos.dao.IGenericDao
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import ar.com.phosgenos.functional.Either
import ar.com.phosgenos.functional.Try
import com.google.inject.Inject
import groovy.util.logging.Slf4j

@Slf4j
class CategoryServiceImpl implements CategoryService {

    @Inject
    IGenericDao<Category, Long> categoryDao

    private IGenericDao<Category, Long> extended

    CategoryServiceImpl() {
        log.info('Instantiated')
    }

    private ExtendedGenericDAO<Category, Long> getCategoryDao() {
        if (!extended) {
            extended = ExtendedGenericDAO.fromInstance(categoryDao)
        }
        extended
    }

    @Override
    Either<Category,ApplicationException> create(Category category) {
        Either<Category, ApplicationException> choice = Try.catching(ApplicationException) {
            getCategoryDao().create(category)
        }

        return choice
    }

    @Override
    Either<Category,ApplicationException> findById(Long identifier) {
        Either<Category, ApplicationException> choice = Try.catching(ApplicationException) {
            getCategoryDao().read(identifier)
        }

        return choice
    }

    @Override
    Either<Category, ApplicationException> update(Category category) {
        boolean exists = getCategoryDao().exists(category.id)
        if (!exists) {
            return Either.alternative(
                    new ApplicationException(
                            "Resource ${category.id} does not exists",
                            null,
                            ErrorCatalog.NOT_FOUND,
                            []
                    )
            )
        }
        return Try.catching(ApplicationException) {
            getCategoryDao().update(category)
        }
    }

    @Override
    Either<Boolean,ApplicationException> remove(Long identifier) {
        Try.catching(ApplicationException) {
            getCategoryDao().delete(identifier)
        }
    }
}
