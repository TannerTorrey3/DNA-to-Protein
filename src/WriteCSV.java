import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WriteCSV {
    public static void main(String[] args) {

        //  Grading program needs hardcoded filename. Oh, well. "
        String inputFilename = "coords.txt";
        String outputFilename = changeFileExtToCsv(inputFilename);

        // Open files
        Scanner input = openInput(inputFilename);//TODO:  call method to open input file
        PrintWriter output = openOutput(outputFilename);//TODO:  call method to open output file

        // TODO:  Read input line, replace all spaces with commas,
        //               and write output line
        while (input.hasNextLine()) {
            output.append(input.nextLine().replaceAll(" ", ",")+"\n");
        }

        // TODO: close streams
        input.close();
        output.close();

    }
    /**
     * Changes file extension to ".csv"
     * @param filename
     * @return
     */
    public static String changeFileExtToCsv(String filename) {
        return filename.substring(0,filename.lastIndexOf('.')) + ".csv";
    }
    /**
     * Open input for reading
     * @param filename
     * @return
     */
    public static Scanner openInput(String filename) {
        Scanner in = null;
        try {
            File infile = new File(filename);
            in = new Scanner(infile);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println(filename + " could not be found");
            System.exit(0);
        }
        return in;
    }
    /**
     * Open output for writing
     * @param filename
     * @return PrintWrite pw with file @path filename
     */
    public static PrintWriter openOutput(String filename) {
        PrintWriter pw = null;
        try {
            File infile = new File(filename);
            pw = new PrintWriter(infile);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println(filename + " could not be found");
            System.exit(0);
        }
        return pw;
    }
    /**
     * @return String = Program#, Author
     * */
    public String getID(){
        return "Program 4a, Tanner Kocher";
    }
}
