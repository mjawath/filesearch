import exceptions.FileNotFound;
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

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Phaser phaser = new Phaser(1);
        analyseDirectory(Paths.get(dirctory), phaser, executorService, todos);
        System.out.println("main is waiting");
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();


        executorService.shutdown();
        System.out.println(todos.size());
        System.out.println("this is the last");

    }


    public static List<Todo> analyseFile(Path filePath) {
        if (filePath.toFile().isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
                int ln = 0;
                List<Todo> todos = new ArrayList<>();
                String line = null;
                while ((line = br.readLine()) != null) {
                    boolean contains = checkPattern(line);
                    if (contains) {
                        Todo todo = new Todo(filePath.toString(), ln, line);
                        todos.add(todo);
                    }
                    ln++;
                }
                return todos;


            } catch (Exception e) {
                if (e instanceof FileNotFoundException) {
                    throw new FileNotFound(e);
                } else if (e instanceof IOException) {
                    throw new IORuntimeException(e);
                }
            }
        }
        throw new FileNotFound();

    }

    public static boolean checkPattern(String line) {
        String pattern = "TODO";
        return line.contains(pattern);
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
            es.submit(() -> {
                System.out.println("execution of file " + phaser.getPhase());
                phaser.register();
                List<Todo> tt = analyseFile(path);
                todos.addAll(tt);
                phaser.arriveAndAwaitAdvance();
//            phaser.arriveAndDeregister();
                System.out.println("completed execution of file " + phaser.getPhase() + " " + path);
            });
        }else {
            todos.addAll(analyseFile(path));
        }
    }

}
