package ar.com.phosgenos.dto.response.json;

import ar.com.phosgenos.dto.response.Response;
import ar.com.phosgenos.dto.response.status.ServiceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que se retornara en las respuestas de servicios JSON.
 * 
 * 
 * Los atributos errorMessage y errrorDescription son opcionales y aparecerán en
 * la respuesta siempre cuando esten definidos.
 * 
 * 
 * @author luis.epifanio
 * 
 * @param <T>
 *            Tipo de dato que se usara para definir que tipo de dato se espera
 *            en la respuesta del servicio
 */
@Data
@EqualsAndHashCode(callSuper=true)
@XmlRootElement
public class JSONResponse<T> extends Response<T> {
	/**
	 * Atributo de tipo ServiceStatus que contendra el status del servicio
	 */	
	ServiceStatus status;

	public JSONResponse() {
		this(null,null);
	}

	public JSONResponse(ServiceStatus status){
		this(status,null);
	}

	public JSONResponse(ServiceStatus status, T data) {
		super(data);
		this.status = status;
	}

    public static <R> JSONResponseBuilder<R> builder(){
	    return new JSONResponseBuilder<>();
    }

}
