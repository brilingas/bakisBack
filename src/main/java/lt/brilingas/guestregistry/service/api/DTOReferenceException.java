package lt.brilingas.guestregistry.service.api;

public class DTOReferenceException extends Exception {
    public DTOReferenceException() {
        super();
    }

    public DTOReferenceException(String message) {
        super(message);
    }

    public DTOReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DTOReferenceException(Throwable cause) {
        super(cause);
    }
}
