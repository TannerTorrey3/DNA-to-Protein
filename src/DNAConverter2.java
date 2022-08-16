import java.io.*;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Program 4 - DNAConverter
 * Program Description Line 1
 * Program Description Line 2
 * @author Tanner Kocher
 * @date
 * CS 160 Summer 2022
 */

//import necessary libraries

public class DNAConverter2 {
    private HashMap<Character,Character> DNAtoRNA = new HashMap<>(){
        {
        put('T', 'A');
        put('A', 'U');
        put('C', 'G');
        put('G', 'C');
        }
    };
    private HashMap<String,String> RNAtoProtein = new HashMap<>();
    private FileOutputStream out;
    //METHODS
    public static void main(String [] args){
        DNAConverter2 dc = new DNAConverter2();
        String rna =dc.DNAtoRNA("../IOFiles/DNA.txt","../IOFiles/RNA.txt");
        dc.RNAtoProtein(rna,"../IOFiles/protein.txt","../IOFiles/proteintable.txt");

    }
    public String DNAtoRNA(String DNAFile, String RNAFile){
        FileInputStream in = openInput(DNAFile);
        out = openOutput(RNAFile);

        //For each byte in DNA || exception
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            //Get Stream with lines from BufferedReader
            reader.lines()
            //Gives each line as string to the changeDNAtoRNA method of this.
                .map(this::changeDNAtoRNA)
            //Calls for each line the print method of this.
                .forEach(this::writeOutput);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return RNAFile;
    }


    public void RNAtoProtein(String RNAFile, String ProteinFile, String RNAtoProteinTable) {
        FileInputStream proteinTable = openInput(RNAtoProteinTable);
        conversionHashMap(proteinTable);
        FileInputStream in = openInput(RNAFile);
        out = openOutput(ProteinFile);
        RNAtoProtein.forEach((k,v) -> System.out.println(k+" "+v));

         try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
                    reader.lines()
                            .map(this::packStream)
                            .forEach(this::writeOutput);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private Stream<String> packStream(String s) {
        Stream<String> sPlus;
        if(s.length() % 3 != 0){throw new RuntimeException("Invalid RNA length");}
        else{
            String t = s;
            int j =2;
            for(int i =0; i < t.length(); ++i){
                System.out.println("start:"+i);
                String key;
                key = t.substring(i,j+1);
                System.out.println("key:"+key);
                String value = "";
                if(null != RNAtoProtein.get(key)){
                    value = RNAtoProtein.get(key);
                    t = t.replaceAll(key,value);
                    System.out.println(t);
                }
                j++;
                System.out.println("finish:"+j);
            }
             sPlus = t.codePoints()
                    .mapToObj(c -> String.valueOf((char) c));
            j = 2;
        }
        System.out.println("ret");
        return sPlus;
    }

    //private Stream<String> RNAseq(String s) {s.cod}


    //HELPER METHODS
    public FileInputStream openInput(String filename) {
        FileInputStream in = null;
        try {
            File infile = new File(filename);
            in = new FileInputStream(infile);
        } catch (FileNotFoundException e) {
        //e.printStackTrace();
        System.out.println(filename + " could not be found");
        System.exit(0);
        }
        return in;
    }
    public FileOutputStream openOutput(String filename){
        FileOutputStream out = null;
        try{
            File outfile = new File(filename);
            out = new FileOutputStream(outfile);
        }catch (FileNotFoundException e){
            System.out.println(filename+ " could not be found");
            System.exit(0);
        }
        return out;
    }
    private Stream<String> changeDNAtoRNA(String line){
        Stream<String> returnStream = line.codePoints()
                .mapToObj(c -> getRNA(c));
        return returnStream;
    }

    private String getRNA(int c) {
        char charFromC = (char)c;
        try{
            if(null != DNAtoRNA.get((Character)charFromC)){
                return String.valueOf((char)DNAtoRNA.get((Character) charFromC));
            }else{
                throw new NoSuchElementException("Not a DNA character");
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
            System.exit(0);
        }
        return "";
    }

    private void writeOutput(Stream<String> stringStream) {
        stringStream.forEach(c -> {
            byte[] character = c.getBytes();
            try {
                out.write(character);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try{
            out.close();
        }catch(IOException e){}
    }
    private void conversionHashMap(FileInputStream proteinTable) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proteinTable))) {
            //Get Stream with lines from BufferedReader
            reader.lines()
            //Calls for each line the print method of this.
            .forEach(this::RNASeqToProtein);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void RNASeqToProtein(String s) {
        String [] pairs = s.split(" ");
        RNAtoProtein.put(pairs[0], pairs[1]);
    }

}