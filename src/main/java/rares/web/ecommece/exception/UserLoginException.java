package rares.web.ecommece.exception;

public final class UserLoginException extends Exception {

    public UserLoginException(){
        super();
    }

    public UserLoginException(String message, Throwable cause){
        super(message, cause);
    }

    public UserLoginException(String message){
        super(message);
    }

    public UserLoginException(Throwable cause){
        super(cause);
    }
}
