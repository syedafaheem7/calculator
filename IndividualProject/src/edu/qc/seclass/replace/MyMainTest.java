package edu.qc.seclass.replace;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MyMainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    // Some utilities

    private File createTmpFile() throws IOException {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    private File createInputFile1() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile2() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice");

        fileWriter.close();
        return file1;
    }

    private File createInputFile3() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123");

        fileWriter.close();
        return file1;
    }
    
    private File createInputFile4() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Hello, Bill. Bye, Bill.");

        fileWriter.close();
        return file1;
    }
    
    private File createEmptyInputFile() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("");

        fileWriter.close();
        return file1;
    }
    
    private File createSpecialInputFile() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Hello Bill! This line has that special characterthat will skip line,\n" +
                "hey \b what about this then? \t \n" +
                "This one \n and more \n" +
                "these \f are also cool look \n" +
                "Don't forget \r" +
                "" +
                "Ohhhhhhhhh!!!!!!!!!");

        fileWriter.close();
        return file1;
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    /////40 test case
    //Implementation of test frame #17 <error>
    //Presence of a file corresponding to the name: Not present
    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile1();
        inputFile1.delete();
        
        String args[] = {"Bill", "John", "--", inputFile1.getPath()};
        Main.main(args);

        assertEquals("File " + inputFile1.getName() + " not found", errStream.toString().trim());
    }
    
    //Implementation of test frame #7 <error>
    //Number of files provided to application: None
    @Test
    public void mainTest2() throws Exception {
        String args[] = {"Bill", "James", "--"};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }
    
    //Implementation of test frame #1 <error>
    //Length of word<from>: Empty
    @Test
    public void mainTest3() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        String args[] = {"-f", "", "What an error!", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }
    
    //Implementation of test frame #13
    //Length of word<from>: Longer than file
    @Test
    public void mainTest4() throws Exception {
        File inputFile4 = createInputFile4();

        String args[] = {"-i", "Bill this word <from> is actually bigger than the file. It looks like we have a bug here!", "Billy the kid", "--", inputFile4.getPath()};
        Main.main(args);

        String expected1 = "Hi, Bill. Bye, Bill.";

        String actual1 = getFileContent(inputFile4.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile4.getPath() + ".bck")));
    }
    
    //Implementation of test frame #2 <single>
    //Size of file: Empty
    @Test
    public void mainTest5() throws Exception {
        File emptyInputFile = createEmptyInputFile();

        String args[] = {"Bill", "James", "--", emptyInputFile.getPath()};
        Main.main(args);

        String expected1 = "";

        String actual1 = getFileContent(emptyInputFile.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(emptyInputFile.getPath() + ".bck")));
    }
    
    //Implementation of test frame #2 <single>
    //Number of occurrences of word<from> in a file: None
    @Test
    public void mainTest6() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"GoodMan", "BadMan", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Implementation of test frame #4 <single>
    //Number of occurrences of word<from> in a file: One
    @Test
    public void mainTest7() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"This", "That", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "That is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }
    
    //Implementation of test frame #5 <single>
    //Position of word<from> in a file: First line
    @Test
    public void mainTest8() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"Howdy", "Greetings", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Greetings Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }
    
    //Implementation of test frame #6 <single>
    //Position of word<from> in a file: Last line
    @Test
    public void mainTest9() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"again!", "once more!!!!!!!!", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" once more!!!!!!!!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }
    
    //Implementation of test frame #16 <single>
    //Length of word<to>: Zero
    @Test
    public void mainTest10() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"is", "", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "Th  a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }
    
    //Implementation of test frame #13 <error>
    //Length of word<to>: Longer than file
    @Test
    public void mainTest11() throws Exception {
        File inputFile4 = createInputFile4();

        String args[] = {"Good Morning", "Greeting, Bill! Let's make this file bigger than what is was before. See", "--", inputFile4.getPath()};
        Main.main(args);

        String expected1 = "Greeting, Bill! Let's make this file bigger than what is was before. See, Bill. Bye, Bill.";

        String actual1 = getFileContent(inputFile4.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile4.getPath() + ".bck")));
    }
    
    //Implementation of test frame #12 <single>
    //Length of word<from>: One
    @Test
    public void mainTest12() throws Exception {
        File inputFile1 = createInputFile1();

        String args[] = {"-i", "a", "LETTER!", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is LETTER! test file for the replLETTER!ce utility\n" +
                "Let's mLETTER!ke sure it hLETTER!s LETTER!t leLETTER!st LETTER! few lines\n" +
                "so thLETTER!t we cLETTER!n creLETTER!te some interesting test cLETTER!ses...\n" +
                "LETTER!nd let's sLETTER!y \"howdy bill\" LETTER!gLETTER!in!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }
    
    //Implementation of test frame #15 <single>
    //Length of word<to>: One
    @Test
    public void mainTest13() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-i", "li", "w", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utiwty\n" +
                "Let's make sure it has at least a few wnes\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utiwty\n" +
                "that contains a wst:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Implementation of test frame #32
    @Test
    public void mainTest14() throws Exception {
        File inputFile1 = createSpecialInputFile();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-l", "This", "That", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill! This line has that special character skip line,\n" +
                "hey \b what about this then? \t \n" +
                "THAT one \n and more \n" +
                "these \f are also cool look \n" +
                "Don't forget \r" +
                "" +
                "aSHOOOOOOOOOOORIUKEN!!!!!! FaTaLiTy!!!";
        String expected2 = "Howdy Bill,\n" +
                "THAT is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b parameter work when used alone
    //Using 3 files
    @Test
    public void mainTest15() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "Bill", "Myan", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Myan,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Myan,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Myan\" twice";
        String expected3 = "Howdy Myan, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -f parameter work when used alone
    //Using 3 files
    @Test
    public void mainTest16() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -l parameter work when used alone
    //Using 3 files
    @Test
    public void mainTest17() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-l", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -i parameter work when used alone
    //Using 3 files
    @Test
    public void mainTest18() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy Gates\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -f parameters work when used together
    //Using 3 files
    @Test
    public void mainTest19() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-f", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -l parameters work when used together
    //Using 3 files
    @Test
    public void mainTest20() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-l", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest21() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-i", "Bill", "Bill Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy Gates\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    //Newly created test case. Purpose: Testing that the -f -l parameters work when used together
    //Using 3 files
    @Test
    public void mainTest22() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "-l", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -f -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest23() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -l -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest24() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-l", "-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy Gates\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -f -l parameters work when used together
    //Using 3 files
    @Test
    public void mainTest25() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-f", "-l", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -f -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest26() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-f", "-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -l -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest27() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-l", "-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy Gates\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -f -l -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest28() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "-l", "-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy Gates\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -f -l -i parameters work when used together
    //Using 3 files
    @Test
    public void mainTest29() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-f", "-l", "-i", "Bill", "Gates", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Gates,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy Gates\" again!";
        String expected2 = "Howdy Gates,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Gates\" twice";
        String expected3 = "Howdy Gates, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    //Newly created test case. Purpose: Testing that the -b parameter work when used alone
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest30() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "ThisOiisOiaOitestOifileOiforOitheOireplaceOiutility\n" +
                "Let'sOimakeOisureOiitOihasOiatOileastOiaOifewOilines\n" +
                "soOithatOiweOicanOicreateOisomeOiinterestingOitestOicases...\n" +
                "AndOilet'sOisayOi\"howdyOibill\"Oiagain!";
        String expected2 = "HowdyOiBill,\n" +
                "ThisOiisOianotherOitestOifileOiforOitheOireplaceOiutility\n" +
                "thatOicontainsOiaOilist:\n" +
                "-a)OiItemOi1\n" +
                "-b)OiItemOi2\n" +
                "...\n" +
                "andOisaysOi\"howdyOiBill\"Oitwice";
        String expected3 = "HowdyOiBill,OihaveOiyouOilearnedOiyourOiabcOiandOi123?\n" +
                "ItOiisOiimportantOitoOiknowOiyourOiabcOiandOi123," +
                "soOiyouOishouldOistudyOiit\n" +
                "andOithenOirepeatOiwithOime:OiabcOiandOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -f parameter work when used alone
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest31() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "HowdyOiBill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "HowdyOiBill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -l parameter work when used alone
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest32() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-l", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\"Oiagain!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\"Oitwice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc andOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -i parameter work when used alone
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest33() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-i", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "ThisOiisOiaOitestOifileOiforOitheOireplaceOiutility\n" +
                "Let'sOimakeOisureOiitOihasOiatOileastOiaOifewOilines\n" +
                "soOithatOiweOicanOicreateOisomeOiinterestingOitestOicases...\n" +
                "AndOilet'sOisayOi\"howdyOibill\"Oiagain!";
        String expected2 = "HowdyOiBill,\n" +
                "ThisOiisOianotherOitestOifileOiforOitheOireplaceOiutility\n" +
                "thatOicontainsOiaOilist:\n" +
                "-a)OiItemOi1\n" +
                "-b)OiItemOi2\n" +
                "...\n" +
                "andOisaysOi\"howdyOiBill\"Oitwice";
        String expected3 = "HowdyOiBill,OihaveOiyouOilearnedOiyourOiabcOiandOi123?\n" +
                "ItOiisOiimportantOitoOiknowOiyourOiabcOiandOi123," +
                "soOiyouOishouldOistudyOiit\n" +
                "andOithenOirepeatOiwithOime:OiabcOiandOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -f parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest34() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-f", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "HowdyOiBill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "HowdyOiBill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -l parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest35() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-l", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\"Oiagain!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\"Oitwice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc andOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -i parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest36() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-i", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "ThisOiisOiaOitestOifileOiforOitheOireplaceOiutility\n" +
                "Let'sOimakeOisureOiitOihasOiatOileastOiaOifewOilines\n" +
                "soOithatOiweOicanOicreateOisomeOiinterestingOitestOicases...\n" +
                "AndOilet'sOisayOi\"howdyOibill\"Oiagain!";
        String expected2 = "HowdyOiBill,\n" +
                "ThisOiisOianotherOitestOifileOiforOitheOireplaceOiutility\n" +
                "thatOicontainsOiaOilist:\n" +
                "-a)OiItemOi1\n" +
                "-b)OiItemOi2\n" +
                "...\n" +
                "andOisaysOi\"howdyOiBill\"Oitwice";
        String expected3 = "HowdyOiBill,OihaveOiyouOilearnedOiyourOiabcOiandOi123?\n" +
                "ItOiisOiimportantOitoOiknowOiyourOiabcOiandOi123," +
                "soOiyouOishouldOistudyOiit\n" +
                "andOithenOirepeatOiwithOime:OiabcOiandOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -f -l parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest37() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "-l", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\"Oiagain!";
        String expected2 = "HowdyOiBill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\"Oitwice";
        String expected3 = "HowdyOiBill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc andOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -f -i parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest38() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-f", "-i", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "HowdyOiBill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "HowdyOiBill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -l -i parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest39() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-l", "-i", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\"Oiagain!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\"Oitwice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc andOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }
    
    //Newly created test case. Purpose: Testing that the -b -f -l parameters work when used together
    //Using 3 files AND using blank space for the word<from>
    @Test
    public void mainTest40() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "-f", "-l", " ", "Oi", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "HowdyOiBill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\"Oiagain!";
        String expected2 = "HowdyOiBill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\"Oitwice";
        String expected3 = "HowdyOiBill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc andOi123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }


}