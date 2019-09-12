package editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageEditor {

    public static void main(String args[]){
        try{
            File f = new File(args[0]);     //read in file
            reader r = new reader();
            ppmImage og = r.processFile(f); //process file into a ppmImage
            String option = args[2];        //which change to apply to image
            FileWriter output = new FileWriter(args[1]);   //create output file
            switch(option){
                case "invert":
                    newImage i = new newImage(og, "invert");
                    i.invert();
                    output.append(i.toString());
                    break;
                case "grayscale":
                    newImage g = new newImage(og, "grayscale");
                    g.grayscale();
                    output.append(g.toString());
                    break;
                case "emboss":
                    newImage e = new newImage(og, "emboss");
                    e.emboss();
                    output.append(e.toString());
                    break;
                case "motionblur":
                    newImage m = new newImage(og, "motion");
                    System.out.println(args[3]);
                    m.motionBlur(Integer.parseInt(args[3]));
                    output.append(m.toString());
                    break;
            }
            output.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return;
    }
}
