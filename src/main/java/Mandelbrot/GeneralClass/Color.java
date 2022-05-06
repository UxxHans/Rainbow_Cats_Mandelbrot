package Mandelbrot.GeneralClass;

/**
 * Color object class.
 * Because set color RGB values is ineffecient, I set up a color palette for the text.
 */
public class Color{

    private int r;
    private int g;
    private int b;

    public final static Color BLACK     = new Color(000,000,000);
    public final static Color WHITE     = new Color(255,255,255);
    public final static Color RED       = new Color(255,000,000);
    public final static Color YELLOW    = new Color(255,255,000);
    public final static Color GREEN     = new Color(000,255,000);
    public final static Color CYAN      = new Color(000,255,255);
    public final static Color BLUE      = new Color(000,000,255);
    public final static Color MAGENTA   = new Color(255,000,255);


    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public void setColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }


    public int getR() {
        return r;
    }


    public void setR(int r) {
        this.r = r;
    }


    public int getG() {
        return g;
    }


    public void setG(int g) {
        this.g = g;
    }


    public int getB() {
        return b;
    }


    public void setB(int b) {
        this.b = b;
    }
}