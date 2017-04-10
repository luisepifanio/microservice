package ar.com.phosgenos.dto.response.status;

import ar.com.phosgenos.dto.ErrorDetail;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Data
@Builder
@EqualsAndHashCode
@XmlRootElement
public class ServiceStatus implements Serializable {

    String error;
    String message;
    @XmlElement(required = true)
    Integer status;
    Collection<ErrorDetail> causes;

    ServiceStatus.ServiceStatusBuilder asBuilder(){
        return builder()
                .error(this.getError())
                .message(this.getMessage())
                .status(this.getStatus())
                .causes(this.getCauses());
    }

}