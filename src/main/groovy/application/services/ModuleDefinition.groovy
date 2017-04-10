package application.services

import application.services.definitions.CategoryServiceDefinition
import application.services.definitions.dao.CategoryDaoImpl
import application.services.impl.CategoryService
import ar.com.phosgenos.dao.IGenericDao
import ar.com.phosgenos.entities.Category
import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Scopes
import com.google.inject.TypeLiteral
import com.google.inject.name.Names

class ModuleDefinition implements Module {
    @Override
    void configure(Binder binder) {
        //Persistence Services
        binder.bind(new TypeLiteral<IGenericDao<Category,Long>>(){})
                .annotatedWith(Names.named('categoryDao'))
                .to(CategoryDaoImpl)
                .in(Scopes.SINGLETON)

        //Bussiness Logic Services
        binder.bind(CategoryServiceDefinition)
                .annotatedWith(Names.named('categoryService'))
                .to(CategoryService)
                .in(Scopes.SINGLETON)
    }
}
