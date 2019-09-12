package editor;

import java.util.ArrayList;

public class newImage {
    private ppmImage original;
    private ppmImage newPic;
    private String type;

    public newImage(ppmImage og, String t){
        original = og;
        type = t;
    }
    public ppmImage invert(){
        newPic = new ppmImage(original.getHeight(), original.getWidth(), original.getMaxColor());
        ArrayList<ArrayList<Pixel>> pixels = original.getAllPixels();
        newPic.setAllPixels(pixels);
        int r = 0;
        int g = 0;
        int b = 0;
        Pixel p;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        for(int h = 0; h < newPic.getHeight(); h++){
            for(int w = 0; w < newPic.getWidth(); w++){
                /*System.out.print("h = ");
                System.out.println(h);
                System.out.print("w = ");
                System.out.println(w);*/
                p = newPic.getPixel(h, w);
                //r = newPic.getPixel(h, w).getRed();
                r = p.getRed();
                //System.out.println(r);
                //g = newPic.getPixel(h, w).getGreen();
                g = p.getGreen();
                //System.out.println(g);
                //b = newPic.getPixel(h, w).getBlue();
                b = p.getBlue();
                //System.out.println(b);
                //System.out.println(h);
                //System.out.println(w);
                newR = 255 - r;
                //System.out.println(newR);
                newG = 255 - g;
                //System.out.println(newG);
                newB = 255 - b;
                //System.out.println(newB);
                //p = newPic.getPixel(h, w);
                p.setRed(newR);
                p.setGreen(newG);
                p.setBlue(newB);
                //newPic.setPixel(h, w, newR, newG, newB);
            }
        }
        return newPic;
    }

    public ppmImage grayscale(){
        newPic = new ppmImage(original.getHeight(), original.getWidth(), original.getMaxColor());
        newPic.setAllPixels(original.getAllPixels());
        int average = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Pixel p;
        for(int h = 0; h < newPic.getHeight(); h++){
            for(int w = 0; w < newPic.getWidth(); w++){
                r = newPic.getPixel(h, w).getRed();
                g = newPic.getPixel(h , w).getGreen();
                b = newPic.getPixel(h , w).getBlue();
                average = (r + g + b) / 3;
                p = newPic.getPixel(h, w);
                p.setRed(average);
                p.setBlue(average);
                p.setGreen(average);
                //System.out.println(average);
                //newPic.setPixel(h , w, average, average, average);
            }
        }
        return newPic;
    }

    public ppmImage emboss(){
        newPic = new ppmImage(original.getHeight(), original.getWidth(), original.getMaxColor());
        newPic.setAllPixels(original.getAllPixels());
        int v = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        int rDiff = 0;
        int gDiff = 0;
        int bDiff = 0;
        int maxDiff = 0;
        Pixel p;
        for(int h = newPic.getHeight() - 1; h >= 0; h--){
            for(int w = newPic.getWidth() - 1; w >= 0; w--){
                p = newPic.getPixel(h, w);
                r = p.getRed();
                g = p.getGreen();
                b = p.getBlue();
                if(h - 1 < 0 || w - 1 < 0){
                    v = 128;
                }
                else{
                    Pixel upperLeft = newPic.getPixel((h - 1) , (w - 1));
                    rDiff = r - upperLeft.getRed();
                    gDiff = g - upperLeft.getGreen();
                    bDiff = b - upperLeft.getBlue();


                    maxDiff = getMaxdiff(rDiff, gDiff, bDiff);
                    /*if(h == 3 && w == 3){
                        System.out.println(rDiff);
                        System.out.println(gDiff);
                        System.out.println(bDiff);
                        System.out.println(maxDiff);
                    }*/
                    if(maxDiff == Math.abs(rDiff)){
                        maxDiff = rDiff;
                    }
                    else if(maxDiff == Math.abs(gDiff)){
                        maxDiff = gDiff;
                    }
                    else{
                        maxDiff = bDiff;
                    }
                    v = 128 + maxDiff;
                    if(v < 0){
                        v = 0;
                    }
                    else if(v > 255){
                        v = 255;
                    }
                }
                p.setRed(v);
                p.setBlue(v);
                p.setGreen(v);
                //newPic.setPixel(h , w, v, v, v);
            }
        }
        return newPic;
    }

    private int getMaxdiff(int rDiff, int gDiff, int bDiff){
        int r = Math.abs(rDiff);
        int g = Math.abs(gDiff);
        //System.out.println(g);
        int b = Math.abs(bDiff);
        int maxDiff = 0;
        if(r > maxDiff){
            maxDiff = r;
        }
        if(g > maxDiff){
            maxDiff = g;
        }
        if(b > maxDiff){
            maxDiff = b;
        }
        if(r == maxDiff && (r == g || r == b)){
            maxDiff = r;
        }
        else if(g == maxDiff && g == b){
            maxDiff = g;
        }
        return maxDiff;
    }

    public ppmImage motionBlur(int blur){
        newPic = new ppmImage(original.getHeight(), original.getWidth(), original.getMaxColor());
        newPic.setAllPixels(original.getAllPixels());
        if(blur == 1){
            return newPic;
        }
        else{
            int offImg = 0;
            for(int h = 0; h < newPic.getHeight(); h++){
                for(int w = 0; w < newPic.getWidth(); w++){
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    int remainW = newPic.getWidth() - w;
                    offImg = Math.min(blur, remainW);
                    //System.out.println(offImg);
                    //int count = 0;
                    for(int a = w; a < w + offImg; a++){
                        r += newPic.getPixel(h , a).getRed();
                        g += newPic.getPixel(h , a).getGreen();
                        b += newPic.getPixel(h , a).getBlue();
                    }
                    int rAverage = r / offImg;
                    int gAverage = g / offImg;
                    int bAverage = b / offImg;
                    Pixel p = newPic.getPixel(h, w);
                    p.setRed(rAverage);
                    p.setGreen(gAverage);
                    p.setBlue(bAverage);
                    //newPic.setPixel(h , w, rAverage, gAverage, bAverage);
                }
            }
            return newPic;
        }

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String p3 = "P3";
        sb.append(p3);
        sb.append(" ");
        sb.append(newPic.getWidth());
        sb.append(" ");
        sb.append(newPic.getHeight());
        sb.append(" ");
        sb.append(newPic.getMaxColor());
        sb.append(" ");
        for(int h = 0; h < newPic.getHeight(); h++){
            for(int w = 0; w < newPic.getWidth(); w++){
                sb.append(newPic.getPixel(h, w).getRed());
                sb.append(" ");
                sb.append(newPic.getPixel(h, w).getGreen());
                sb.append(" ");
                sb.append(newPic.getPixel(h, w).getBlue());
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
