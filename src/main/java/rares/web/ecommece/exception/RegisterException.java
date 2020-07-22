package rares.web.ecommece.exception;

public final class RegisterException extends RuntimeException{

    public RegisterException(){
        super();
    }

    public RegisterException(String message, Throwable cause){
        super(message, cause);
    }

    public RegisterException(String message){
        super(message);
    }

    public RegisterException(Throwable cause){
        super(cause);
    }
}
