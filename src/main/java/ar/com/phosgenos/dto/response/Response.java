package ar.com.phosgenos.dto.response;

import lombok.Data;

import java.util.Objects;

@Data
public class Response<T> implements ResponseCapable {

    T data;

    public Response() { }

    public Response(T _data) {
        this.data = _data;
    }

    @Override
    public boolean responseSucceeded() {
        return true;
    }

}
