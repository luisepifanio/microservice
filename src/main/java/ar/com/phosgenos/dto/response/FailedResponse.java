package ar.com.phosgenos.dto.response;

import lombok.Data;

import java.util.Objects;

@Data
public class FailedResponse implements ResponseCapable {

    final Exception exception;

    public FailedResponse(Exception _exception){
        this.exception = Objects.requireNonNull(_exception,"Exception can not be null");
    }

    public boolean responseSucceeded(){
        return false;
    }
}
