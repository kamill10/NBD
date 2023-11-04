package p.lodz.Exceptions;

import com.mongodb.MongoException;

public class InvalidDataException extends MongoException {
    public InvalidDataException(String msg) {
        super(msg);
    }

    public InvalidDataException(String msg, Throwable t) {
        super(msg, t);
    }
}
