import exceptions.IORuntimeException;
import model.Todo;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Created by jawa on 12/21/2020.
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {


        List todos = Collections.synchronizedList(new ArrayList<>());
        String dirctory = "C:\\dev\\findstringinfiles\\files";

        ExecutorService executorService = Executors.newCachedThreadPool();

        Phaser phaser = new Phaser(1);
        analyseDirectory(Paths.get(dirctory), phaser, executorService, todos);
        System.out.println("main is waiting");
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();


        executorService.shutdown();
        System.out.println(todos.size());
        System.out.println("this is the last");

    }


    public static void analyseDirectory(Path path, List todos) {
        analyseDirectory(path,null,null,todos);
    }

    public static void analyseDirectory(Path path, Phaser phaser, ExecutorService es, List todos) {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {

                directoryStream.forEach(item ->{
                    if (Files.isRegularFile(item) && Files.isReadable(item)) {
                        executeFileAnalyser(item, phaser, es, todos);
                    } else {
                        analyseDirectory(item, phaser, es, todos);
                    }
                });
            } catch (IOException ex) {
                throw new IORuntimeException(ex);
            }
        } else {
            executeFileAnalyser(path, phaser, es, todos);
        }
    }

    private static void executeFileAnalyser(Path path, Phaser phaser, ExecutorService es,final List<Todo> todos) {
        if(es!=null && phaser!=null) {
            es.submit(new FileAnalyser(path,phaser,todos));

        }else {
            todos.addAll(FileAnalyser.analyseFile(path));
        }
    }

}
