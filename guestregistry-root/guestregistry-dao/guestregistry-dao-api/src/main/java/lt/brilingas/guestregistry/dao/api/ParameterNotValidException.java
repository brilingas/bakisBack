package lt.brilingas.guestregistry.dao.api;

public class ParameterNotValidException extends Exception {
    public ParameterNotValidException() {
        super();
    }

    public ParameterNotValidException(String message) {
        super(message);
    }

    public ParameterNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterNotValidException(Throwable cause) {
        super(cause);
    }
}
