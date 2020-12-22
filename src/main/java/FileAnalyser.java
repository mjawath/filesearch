import exceptions.FileNotFound;
import exceptions.IORuntimeException;
import model.Todo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

/**
 * Created by jawa on 12/22/2020.
 */
public class FileAnalyser implements Runnable {

    private Phaser phaser;
    private List<Todo> todos;
    private Path path;

    public FileAnalyser(Path path, Phaser phaser, List<Todo> todos){
        this.phaser = phaser;
        this.todos = todos;
        this.path = path;
        phaser.register();
    }

    @Override
    public void run() {


            phaser.arriveAndAwaitAdvance();
            System.out.println("execution on file " + path);

            List<Todo> tt = analyseFile(path);
            todos.addAll(tt);
            System.out.println("completed execution of file " + path+ " phase " + phaser.getPhase() );
            System.out.println(todos.size());
            System.out.println("-------------------------------------------------------------------------");
            phaser.arriveAndDeregister();
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


    public static void executeFileAnalyser(Path path, Phaser phaser, ExecutorService es, final List<Todo> todos) {
        if(es!=null && phaser!=null) {
            es.submit(new FileAnalyser(path,phaser,todos));
        }else {
            todos.addAll(FileAnalyser.analyseFile(path));
        }
    }


}

