package ar.com.phosgenos.dto.response.json;

import ar.com.phosgenos.dto.response.FailedResponse;
import ar.com.phosgenos.dto.response.status.ServiceStatus;
import ar.com.phosgenos.exception.ApplicationException;
import ar.com.phosgenos.exception.ErrorCatalog;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.ws.rs.core.Response;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class JSONResponseBuilder<T> {

    @Getter
    ServiceStatus status;
    @Getter
    T data;
    @Getter
    protected Exception exception;

    public JSONResponseBuilder(){}

    public JSONResponseBuilder<T> status(ServiceStatus _status){
        this.status = _status;
        return this;
    }

    public JSONResponseBuilder<T> data(T _data){
        this.data = _data;
        return this;
    }

    public JSONResponseBuilder<T> exception(Exception _exception){
        this.exception = _exception;
        return this;
    }

    public Response build(){
        boolean consistentState = Stream.of(exceptionAvailable(),payloadAvailable())
                .filter( result -> result )
                .collect(Collectors.toSet())
                .size() > 0;
        if(!consistentState){
            throw new IllegalStateException("To build a Response yo need an Exception or payload at least");
        }

        if ( null != getException()){
            return handle(new FailedResponse(this.getException()));
        } else if( null != getData() ){
            return handle(new ar.com.phosgenos.dto.response.Response<>(this.getData()));
        }
        // This should be unreachable code due to preconditions
        throw new IllegalStateException("To build a Response yo need an Exception or payload at least");
    }

    private boolean exceptionAvailable(){ return null != exception; }
    private boolean payloadAvailable(){ return null != data; }

    protected Response handle(FailedResponse response){
        ServiceStatus serviceStatus = null;
        if (response.getException() instanceof ApplicationException) {
            ApplicationException applicationException = (ApplicationException) response.getException();
            ErrorCatalog catalogEntry = applicationException.getCatalogEntry();

            serviceStatus = ServiceStatus.builder()
                    .error(catalogEntry.error)
                    .status(catalogEntry.status)
                    .message(catalogEntry.defaultMessage)
                    .causes(applicationException.getCauses())
                    .build();

        } else {
            ErrorCatalog catalogEntry =ErrorCatalog.UNKNOWN;
            serviceStatus = ServiceStatus.builder()
                    .error(catalogEntry.error)
                    .status(catalogEntry.status)
                    .message(catalogEntry.defaultMessage)
                    .causes(new LinkedHashSet<>())
                    .build();
        }

        JSONResponseBuilder<T> builder =JSONResponse.builder();
        builder.status(serviceStatus)
                .data(null);

        return Response
                .status(serviceStatus.getStatus())
                .entity(builder.build())
                .build();
    }

    protected Response handle(ar.com.phosgenos.dto.response.Response<T> response){
        JSONResponseBuilder<T> builder =JSONResponse.builder();

        builder.status(
                Optional.ofNullable(status)
                .orElse(ServiceStatus.builder().status(200).build()) )
                .data(response.getData());

        return Response
                .status(status.getStatus())
                .entity(builder.build())
                .build();
    }
}
