package ar.com.phosgenos.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode
public class ErrorDetail implements Serializable {

    private static final long serialVersionUID = -8848395860221533385L;

    String code;
    String message;
    String field;
}
