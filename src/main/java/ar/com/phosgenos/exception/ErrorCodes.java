package ar.com.phosgenos.exception;

public enum ErrorCodes {

    UNKWON("unknown", "An unhandled error occurred"),
    INTERNAL_ERROR("error", "An internal error occurred"),
    VALIDATION_ERROR("validation_error", "A constraint validation was violated"),
    ;

    final String error;
    final String message;

    ErrorCodes(String _code, String _message) {
        this.error = _code;
        this.message = _message;
    }
}
