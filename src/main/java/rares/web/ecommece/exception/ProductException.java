package rares.web.ecommece.exception;

public final class ProductException extends RuntimeException{

    public ProductException(){
        super();
    }

    public ProductException(String message, Throwable cause){
        super(message, cause);
    }

    public ProductException(String message){
        super(message);
    }

    public ProductException(Throwable cause){
        super(cause);
    }
}
