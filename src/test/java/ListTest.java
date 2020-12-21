import exceptions.FileNotFound;
import model.Todo;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jawa on 12/21/2020.
 */


public class ListTest {


    @Test()
    public void testPatternContains(){

        boolean pat = Main.checkPattern("loadfasdfj kjejkeja askdfasfj // TODO");
        Assert.assertTrue("input contains the pattern",pat);

    }



    @Test
    public void testPatternNotContains(){
        boolean pat = Main.checkPattern("loadfasdfj kjejkeja askdfasfj // TsODO");
        Assert.assertFalse("input does not contain the pattern",pat);

    }


    @Test
    public void directoryContainsTwoFiles(){

        String dirctory ="C:\\dev\\findstringinfiles\\files\\dir3";
        List<Todo> todos = new ArrayList<>();
        Main.analyseDirectory(Paths.get(dirctory),null,null,todos);
//        boolean pat = (boolean) todos;
        Assert.assertTrue("directory  contains TODO",todos.size()==2);
//        Assert.assertEquals("directory  contains TODO",todos.get(0).getFilePath(),
//                Paths.get("C:\\dev\\findstringinfiles\\files\\dir3\\file1").toString());

    }

    @Test
    public void directoryContainsFiles(){

        String dirctory ="C:\\dev\\findstringinfiles\\files";
        List<Todo> todos = new ArrayList<>();
        Main.analyseDirectory(Paths.get(dirctory),todos);
//        boolean pat = (boolean) todos;
        Assert.assertTrue("directory  contains TODO",todos.size()>0);
//        Assert.assertEquals("directory  contains TODO",todos.get(0).getFilePath(),
//                Paths.get("C:\\dev\\findstringinfiles\\files\\dir1\\file1.txt").toString());

    }


    @Test
    public void directoryDoesNotContainsFiles(){

        String dirctory ="C:\\dev\\findstringinfiles\\files\\empty";
        List<Todo> todos = new ArrayList<>();
        Main.analyseDirectory(Paths.get(dirctory),todos);
//        boolean pat = (boolean) todos;
        Assert.assertTrue("dircotry does not contains TODO",todos.size()==0);

    }

    @Test
    public void directoryDoesNotContainsTodox(){

        String dirctory ="C:\\dev\\findstringinfiles\\files\\dir1";
        List<Todo> todos = new ArrayList<>();
        Main.analyseDirectory(Paths.get(dirctory),todos);
//        boolean pat = (boolean) todos;
        Assert.assertTrue("dircotry does not contains TODO",todos.size()==0);
    }


    @Test
    public void directoryDoesNotContainsTodo(){

        String dirctory ="C:\\dev\\findstringinfiles\\files\\dir2";
        List<Todo> todos = new ArrayList<>();
        Main.analyseDirectory(Paths.get(dirctory),todos);
//        boolean pat = (boolean) todos;
        Assert.assertTrue("dircotry does not contains TODO",todos.size()==0);
    }

    @Test
    public void fileContainsTodo(){

        String dirctory ="C:\\dev\\findstringinfiles\\files\\dir1\\file1.txt";
        List<Todo> todos = Main.analyseFile(Paths.get(dirctory));
//        boolean pat = (boolean) todos;
        Assert.assertEquals("file contains TODO",Paths.get("C:\\dev\\findstringinfiles\\files\\dir1\\file1.txt"),
                Paths.get(todos.get(0).getFilePath()));

    }

    @Test(expected = FileNotFound.class)
    public void fileNotFound(){
//        Files.walkFileTree()

        String dirctory ="C:\\dev\\findstringinfiles\\files\\file1.txt";
        List<Todo> todos = Main.analyseFile(Paths.get(dirctory));
//        boolean pat = (boolean) todos;
//        Assert.assertEquals("file contains TODO",Paths.get("C:\\dev\\findstringinfiles\\files\\file1.txt"),
//                Paths.get(todos.get(0).getFilePath()));

    }



}
