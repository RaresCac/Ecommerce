package rares.web.ecommece.exception;

public final class QuantityException extends RuntimeException{

    public QuantityException(){
        super();
    }

    public QuantityException(String message, Throwable cause){
        super(message, cause);
    }

    public QuantityException(String message){
        super(message);
    }

    public QuantityException(Throwable cause){
        super(cause);
    }
}
