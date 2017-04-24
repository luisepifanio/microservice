package ar.com.phosgenos.exception;

import org.eclipse.jetty.http.HttpStatus;

public enum ErrorCatalog {

    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR_500, ErrorCodes.UNKNOWN),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR_500, ErrorCodes.INTERNAL_ERROR),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST_400,ErrorCodes.VALIDATION_ERROR),
    OPERATION_FAILED(HttpStatus.BAD_REQUEST_400,ErrorCodes.OPERATION_FAILED),
    NOT_FOUND(HttpStatus.NOT_FOUND_404,ErrorCodes.NOT_FOUND)
    ;

    public final int status;
    public final String defaultMessage;
    public final String error;

    ErrorCatalog(int _status, ErrorCodes errorCode) {
        status = _status;
        defaultMessage = errorCode.message;
        error = errorCode.error;
    }

    public static ErrorCatalog from(String id) {
        for (ErrorCatalog anEnum : values()) {
            if (anEnum.name().equalsIgnoreCase(id)) {
                return anEnum;
            }
        }
        return null;
    }
}