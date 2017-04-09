package ar.com.phosgenos.dto.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public interface ResponseCapable extends Serializable {
    boolean responseSucceeded();
}
