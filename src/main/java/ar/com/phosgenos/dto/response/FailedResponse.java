package ar.com.phosgenos.dto.response;

import lombok.Data;

import java.util.Objects;

@Data
public class FailedResponse implements ResponseCapable {

    private static final long serialVersionUID = 6873633717199976133L;

    final Exception exception;

    public FailedResponse(Exception _exception){
        this.exception = Objects.requireNonNull(_exception,"Exception can not be null");
    }

    public boolean responseSucceeded(){
        return false;
    }
}
