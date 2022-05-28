package at.ac.fhcampuswien.aabcnews;

public class NewsApiException extends Exception{
    public enum EXCEPTION_CODE {none, badResponse, noConnection};
    EXCEPTION_CODE exceptionCode = EXCEPTION_CODE.none;
    public NewsApiException(String message) {
        super(message);
    }

    public NewsApiException(String message, EXCEPTION_CODE exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
