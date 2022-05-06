package Mandelbrot;

import Mandelbrot.GeneralClass.Color;
import Mandelbrot.GeneralClass.Vector2;
import processing.core.PApplet;

/**
 * Represent a camera object in the Mandelbrot visualization.
 */
public abstract class Camera implements IColorScheme{
    private Vector2<Double> position;                       //Position of the camera center in the axis that represent c = a + bi.
    private double zoom;                                    //Zoom level of the camera, the smaller the closer.
    private int pixelSize;                                  //Size of the big pixel. The bigger the value becomes, the faster it renders.
    private boolean isMoving;                               //Is the camera moving? This is used to modify the pixel size.

    public final static int DEFAULT_PIXEL_SIZE_MIN = 1;     //The default highest resolution, set this to 1 if want full resolution. (big pixel size)
    public final static int DEFAULT_PIXEL_SIZE_MAX = 10;    //The default lowest resolution when moving camera. (big pixel size)
    public final static int DEFAULT_PIXEL_SIZE_STEP = 1;    //The default speed of increasing resolution, higher may increase lag but reduce time to render the highest resolution. (big pixel size)
    public final static double DEFAULT_ZOOM = 0.8;            //The default zoom on creation of the object.
    private final static double DEFAULT_HEIGHT = 4;         //The default height of the canvas in the axis c = a + bi on creation of the object. It is set to 4 because of the boundary of Mandelbrot vertically is (-2, 2).
    
    //The default position of the camera center.
    private final static Vector2<Double> DEFAULT_POSITION = new Vector2<Double>(-0.35, 0.0);

    public Camera() {
        this.isMoving = false;
        this.position = DEFAULT_POSITION;
        this.zoom = DEFAULT_ZOOM;
        this.pixelSize = DEFAULT_PIXEL_SIZE_MAX;
    }

    /**
     * Implementation of IColorScheme, need to implement a method to identify iterations using colors.
     */
    public abstract Color rule(int iterations, int maxIterations);
    /**
     * Render a pixel in the canvas according to given pixel location and c value.
     * @param x Pixel position X.
     * @param y Pixel position Y.
     * @param a Value of a in c = a + bi.
     * @param b Value of b in c = a + bi.
     * @param mainProgram The main program to draw the graph.
     */
    public abstract void renderPixels(int[] pixelIndexList, Double a, Double b, int maxIterations, PApplet mainProgram);

    /**
     * Render method draws the whole canvas.
     */
    public boolean render(int maxIterations, PApplet mainProgram){

        //If the camera start moving, change the resolution to a lower value.
        if(isMoving) setPixelSize(DEFAULT_PIXEL_SIZE_MAX);

        //If the camera is not moving, increase the resolution.
        else if(!isMoving && pixelSize > DEFAULT_PIXEL_SIZE_MIN) setPixelSize(pixelSize - 1);

        //If this is the highest resolution, stop render.
        else if(pixelSize <= DEFAULT_PIXEL_SIZE_MIN) return false;

        //Get width and height of the canvas in the axis of c = a + bi.
        double aspectRatio = (double)GlobalSettings.CANVAS_WIDTH / (double)GlobalSettings.CANVAS_HEIGHT;
        double width  = aspectRatio * DEFAULT_HEIGHT * zoom;
        double height = DEFAULT_HEIGHT * zoom;

        //Get the total render size. (Big pixel amount in canvas width and height)
        int renderWidth = (int)((double) GlobalSettings.CANVAS_WIDTH / pixelSize);
        int renderHeight = (int)((double) GlobalSettings.CANVAS_HEIGHT / pixelSize);

        //Get the sample interval in the axis of c = a + bi.
        double renderUnitWidth = width / (double) renderWidth;
        double renderUnitHeight = height / (double) renderHeight;

        //Get the top left to draw from.
        double topLeftPositionX = position.x - width/2;
        double topLeftPositionY = position.y + height/2;

        //Load the pixels.
        mainProgram.loadPixels();

        //For each big pixel.
        for(int x = 0; x < renderWidth; x++){
            for(int y = 0; y < renderHeight; y++){
                
                //Get the a & b values.
                double currentA = topLeftPositionX + renderUnitWidth * (double)x;
                double currentB = topLeftPositionY - renderUnitHeight * (double)y;

                //Get the top left pixel position of the big pixel.
                int xOnScreen = x * pixelSize;
                int yOnScreen = y * pixelSize;
                int[] pixelIndexList = new int[(int)Math.pow(pixelSize, 2)];

                //Iteration of pixels in big pixel in order to get pixel list.
                for(int px = 0; px<pixelSize; px++){
                    for(int py = 0; py<pixelSize; py++){

                        //Pixel positions in big pixel.
                        int pixelPosX = xOnScreen + px;
                        int pixelPosY = yOnScreen + py;

                        //If position is out of bound, skip.
                        if(isOutOfScreen(pixelPosX, pixelPosY)) continue;

                        //Put all pixels index of the big pixel into the list.
                        pixelIndexList[px * pixelSize + py] = GlobalSettings.CANVAS_WIDTH * pixelPosY + pixelPosX;
                    }
                }

                //Draw the big pixel.
                renderPixels(pixelIndexList, currentA, currentB, maxIterations, mainProgram);
                
            }
        }

        //Draw the pixels.
        mainProgram.updatePixels();
        return true;
    }

    public static boolean isOutOfScreen(int x, int y){
        return (x >= GlobalSettings.CANVAS_WIDTH || y >= GlobalSettings.CANVAS_HEIGHT);
    }

    public Vector2<Double> getPosition() {
        return position;
    }

    public void setPosition(Vector2<Double> position) {
        this.position = position;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
}
