package ar.com.phosgenos.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class ErrorDetail {
    String code;
    String message;
    String field;
}
