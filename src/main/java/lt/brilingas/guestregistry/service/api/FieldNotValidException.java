package lt.brilingas.guestregistry.service.api;

public class FieldNotValidException extends Exception {
    public FieldNotValidException() {
        super();
    }

    public FieldNotValidException(String message) {
        super(message);
    }

    public FieldNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldNotValidException(Throwable cause) {
        super(cause);
    }
}
