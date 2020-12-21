package exceptions;

/**
 * Created by jawa on 12/21/2020.
 */
public class FileNotFound extends RuntimeException {

    public FileNotFound() {
        super();
    }
    public FileNotFound(Throwable cause) {
        super(cause);
    }
}
