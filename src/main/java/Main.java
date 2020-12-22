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
        DirectoryAnalyser.analyseDirectory(Paths.get(dirctory), phaser, executorService, todos);
        System.out.println("main is waiting");
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();


        executorService.shutdown();
        printTodos(todos);

    }

    private static void printTodos(List<Todo> todos){
        System.out.println("Number of Todos"+todos.size());

        todos.forEach(e->{
            System.out.println("File - "+ e.getFilePath()
                +"-  Line -"+e.getLineNo() +" - Todo content - "+e.getLine());
        });

    }


}
