package editor;

import java.util.ArrayList;

public class ppmImage {
    private int height;
    private int width;
    private int maxColor;
    private ArrayList<ArrayList<Pixel>> pixels;

    public ppmImage(int h, int w, int mColor){
        height = h;
        width = w;
        maxColor = mColor;
        pixels = new ArrayList<ArrayList<Pixel>>();
        for(int i = 0; i < h; i++){
            pixels.add(new ArrayList<Pixel>());
        }
    }

    public void addPixel(int r, int g, int b, int height){
        Pixel p = new Pixel(r, g, b);
        pixels.get(height).add(p);
    }




    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public int getMaxColor(){
        return maxColor;
    }
    public ArrayList<ArrayList<Pixel>> getAllPixels() {
        return pixels;
    }
    public void setAllPixels(ArrayList<ArrayList<Pixel>> p) {
        pixels = p;
    }
    public Pixel getPixel(int height, int width) {
        ArrayList<Pixel> w= pixels.get(height);
        return w.get(width);
        //return pixels.get(height).get(width);
    }
    public void setPixel(int height, int width, int r, int g, int b){
        pixels.get(height).get(width).setRed(r);
        pixels.get(height).get(width).setRed(g);
        pixels.get(height).get(width).setRed(b);
    }
}
