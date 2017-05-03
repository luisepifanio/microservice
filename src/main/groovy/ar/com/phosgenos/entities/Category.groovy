package ar.com.phosgenos.entities

import ar.com.phosgenos.dao.IdentifiableEntity
import ar.com.phosgenos.util.Mappable
import groovy.transform.builder.Builder
import lombok.EqualsAndHashCode
import lombok.ToString

import javax.xml.bind.annotation.XmlRootElement

@Builder
@EqualsAndHashCode(of = ['id'])
@ToString
class Category implements IdentifiableEntity<Long>, Mappable {

    Long id
    String name
    Date modified

    @Override
    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    def asBuilder() {
        builder()
                .id(id)
                .name(name)
                .modified(modified)
    }
}
