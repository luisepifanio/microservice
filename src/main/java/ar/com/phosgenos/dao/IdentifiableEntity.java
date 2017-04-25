package ar.com.phosgenos.dao;

import java.io.Serializable;

public interface IdentifiableEntity<PK> extends Serializable {
    PK getId();
}
