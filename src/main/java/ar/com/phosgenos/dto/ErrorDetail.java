package ar.com.phosgenos.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@EqualsAndHashCode
@XmlRootElement
public class ErrorDetail {
    String code;
    String message;
    String field;
}
