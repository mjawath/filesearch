package exceptions;

/**
 * Created by jawa on 12/21/2020.
 */
public class FilePathNotAFile extends RuntimeException {
    public FilePathNotAFile(Throwable cause) {
        super(cause);
    }
}
