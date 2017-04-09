package ar.com.phosgenos.dao.memory

import ar.com.phosgenos.dao.AbstractDAO
import ar.com.phosgenos.entities.Category

class CategoryDaoImpl extends AbstractDAO<Category,Long> {
    CategoryDaoImpl() {
        super()
    }

    @Override
    Category create(Category category) {
        return null
    }

    @Override
    Category read(Long id) {
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
}
