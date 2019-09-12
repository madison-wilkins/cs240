package editor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class reader {
    public ppmImage processFile(File f) throws IOException {
        Scanner scanner = new Scanner(f);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        String str;
        str = scanner.next();               //skip over "P3" image
        str = scanner.next();               //read in width
        int width =  Integer.parseInt(str);
        str = scanner.next();               //read in height
        int height = Integer.parseInt(str);
        str = scanner.next();               //read in max number
        int maxNum = Integer.parseInt(str);
        ppmImage original = new ppmImage(height, width, maxNum);    //create a new ppmImage
        int r = 0;
        int g = 0;
        int b = 0;
        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++) {
                str = scanner.next();
                r = Integer.parseInt(str);
                str = scanner.next();
                g = Integer.parseInt(str);
                str = scanner.next();
                b = Integer.parseInt(str);
                original.addPixel(r, g, b, h);
            }
        }
        /*while(scanner.hasNext()) {
            str = scanner.next();
            r = Integer.parseInt(str);
            str = scanner.next();
            g = Integer.parseInt(str);
            str = scanner.next();
            b = Integer.parseInt(str);
            //original.addPixel(r,g,b, heightCalc);
            if(widthCalc < width - 1){
                widthCalc++;
            }
            else{
                widthCalc = 0;
                heightCalc++;
            }*/
        return original;
    }
}
