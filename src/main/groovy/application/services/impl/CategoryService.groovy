package application.services.impl

import application.services.definitions.CategoryServiceDefinition
import ar.com.phosgenos.dao.ExtendedGenericDAO
import ar.com.phosgenos.dao.IGenericDao
import ar.com.phosgenos.entities.Category
import ar.com.phosgenos.exception.ApplicationException
import ar.com.phosgenos.exception.ErrorCatalog
import com.google.inject.Inject
import com.google.inject.name.Named

class CategoryService implements CategoryServiceDefinition{

    @Inject @Named('categoryDao')
    IGenericDao<Category, Long>  categoryDao

    @Override
    Category create(Category category) {
        return ExtendedGenericDAO.fromInstance(categoryDao).create(category)
    }

    @Override
    Category findById(Long identifier) {
         return ExtendedGenericDAO.fromInstance(categoryDao).read(identifier)
    }

    @Override
    Category update(Category category) {
        boolean exists = ExtendedGenericDAO.fromInstance(categoryDao).exists(category.id)
        if(!exists){
            throw new ApplicationException(
                    "Resource ${category.id} does not exists",
                    null,
                    ErrorCatalog.NOT_FOUND,
                    []
            )
        }

        return ExtendedGenericDAO.fromInstance(categoryDao).update(category)
    }

    @Override
    boolean remove(Long identifier) {
        Category category = ExtendedGenericDAO.fromInstance(categoryDao).read(identifier)
        if(!category){
            return false
        }
        return ExtendedGenericDAO.fromInstance(categoryDao).delete(identifier)
    }


}
