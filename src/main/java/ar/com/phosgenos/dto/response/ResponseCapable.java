package ar.com.phosgenos.dto.response;

import java.io.Serializable;

public interface ResponseCapable extends Serializable {
    boolean responseSucceeded();
}
