package ar.com.phosgenos.dto.response.json;

import ar.com.phosgenos.dto.response.Response;
import ar.com.phosgenos.dto.response.status.ServiceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Clase que se retornara en las respuestas de servicios JSON.
 * <p>
 * <p>
 * Los atributos errorMessage y errrorDescription son opcionales y aparecerán en
 * la respuesta siempre cuando esten definidos.
 *
 * @param <T> Tipo de dato que se usara para definir que tipo de dato se espera
 *            en la respuesta del servicio
 * @author luis.epifanio
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JSONResponse<T> extends Response<T> {

    private static final long serialVersionUID = 6473295860085709755L;
    /**
     * Atributo de tipo ServiceStatus que contendra el status del servicio
     */
    ServiceStatus status;

    public JSONResponse() {
        this(null, null);
    }

    public JSONResponse(ServiceStatus status) {
        this(status, null);
    }

    public JSONResponse(ServiceStatus status, T data) {
        super(data);
        this.status = status;
    }

    public static <R> JSONResponseBuilder<R> builder() {
        return new JSONResponseBuilder<>();
    }

}
