package editor;

public class Pixel {
    private int red;
    private int green;
    private int blue;

    public Pixel(int r, int g, int b) {
        red = r;
        green = g;
        blue = b;
    }

    public void setRed(int r){

        red = r;
    }
    public void setGreen(int g){

        green = g;
    }
    public void setBlue(int b){
        blue = b;
    }

    public int getRed(){
        return red;
    }
    public int getGreen(){
        return green;
    }
    public int getBlue(){
        return blue;
    }

}
