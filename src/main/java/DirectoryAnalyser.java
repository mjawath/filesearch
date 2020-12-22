import exceptions.IORuntimeException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

/**
 * Created by jawa on 12/22/2020.
 */
public class DirectoryAnalyser {


    public static void analyseDirectory(Path path, List todos) {
        analyseDirectory(path,null,null,todos);
    }

    public static void analyseDirectory(Path path, Phaser phaser, ExecutorService es, List todos) {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {

                directoryStream.forEach(item ->{
                    if (Files.isRegularFile(item) && Files.isReadable(item)) {
                        FileAnalyser.executeFileAnalyser(item, phaser, es, todos);
                    } else {
                        analyseDirectory(item, phaser, es, todos);
                    }
                });
            } catch (IOException ex) {
                throw new IORuntimeException(ex);
            }
        } else {
            FileAnalyser.executeFileAnalyser(path, phaser, es, todos);
        }
    }

}
