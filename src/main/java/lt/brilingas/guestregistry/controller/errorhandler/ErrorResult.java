package lt.brilingas.guestregistry.controller.errorhandler;

import lombok.Getter;
import lombok.Setter;

public class ErrorResult {
    private static int id;
    @Getter
    @Setter
    private int errorId;
    @Getter
    @Setter
    private String message;

    @Override
    public String toString() {
        return "errorId = " + errorId + " : " + message;
    }

    public static synchronized int getNextId() {
        return id++;
    }
}
