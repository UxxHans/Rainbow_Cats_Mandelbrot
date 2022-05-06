package Mandelbrot;

import Mandelbrot.GeneralClass.Color;
import Mandelbrot.GeneralClass.Vector2;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.MouseEvent;

/**
 * The main program of the project.
 */
public class App extends PApplet {

    //Main camera.
    public Camera camera;

    //Text object to show title.
    public PFont font;
    public TextObject titleText;
    public TextObject instructionText;
    public TextObject iterationText;

    //Temporary mouse positions for camera movement.
    private int maxIterations;
    private int deltaMouseX;
    private int deltaMouseY;
    private int lastMouseX;
    private int lastMouseY;
    private Vector2<Double> lastCameraPosition;

    private final static double ZOOM_SPEED = 0.1;               //Zoom speed of the camera, set this to negative if want to invert control.
    private final static double MOVEMENT_SPEED = 0.01;          //Movement speed of the camera control.
    private final static int ITERATIONS_DEFAULT = 200;          //Default maximum iterations.
    private final static int ITERATIONS_LIMIT = 500;            //The maximum of the maximum iterations that can be modified during runtime.
    private final static int ITERATIONS_INCREASE_STEP = 10;     //How many iteration to add on an increase using control.

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        noSmooth();
        size(GlobalSettings.CANVAS_WIDTH, GlobalSettings.CANVAS_HEIGHT);
    }

    /**
     * Load all resources. Initialise the elements.
    */
    public void setup() {
        //Set black background.
        background(0, 0, 0);

        //Set frame rate of the game.
        frameRate(GlobalSettings.FRAME_RATE);

        //Load font.
        final int paddings = 30;
        this.font = createFont(this.getClass().getResource("Quicksand-Light.ttf").getPath(), 128);
        this.titleText = new TextObject(GlobalSettings.CANVAS_WIDTH / 2, paddings, 28, "MANDELBROT", Color.WHITE, font);
        this.iterationText = new TextObject(GlobalSettings.CANVAS_WIDTH / 2, paddings * 2, 12, "Iteration", Color.WHITE, font);
        this.instructionText = new TextObject(GlobalSettings.CANVAS_WIDTH / 2, GlobalSettings.CANVAS_HEIGHT - paddings, 12, "Drag & Scroll to control. W & A to Change Maximum Iterations.", Color.WHITE, font);

        //Create a camera
        this.maxIterations = ITERATIONS_DEFAULT;
        this.camera = new RenderCamera();
    }

    /**
     * Camera position control. Set low resolution if start moving
     */
    public void mousePressed() {
        cameraMove();

        lastMouseX = mouseX; 
        lastMouseY = mouseY; 

        lastCameraPosition = this.camera.getPosition();
    }

    /**
     * Camera position control.
     */
    public void mouseDragged() {
        cameraMove();

        deltaMouseX = mouseX - lastMouseX; 
        deltaMouseY = mouseY - lastMouseY; 

        double zoom = this.camera.getZoom();
        double camX = lastCameraPosition.x - (double)deltaMouseX * zoom * MOVEMENT_SPEED;
        double camY = lastCameraPosition.y + (double)deltaMouseY * zoom * MOVEMENT_SPEED;

        this.camera.setPosition(new Vector2<Double>(camX, camY));
    }

    /**
     * Mouse wheel controls of the camera zoom.
     */
    public void mouseWheel(MouseEvent event) {
        cameraMove();

        //Down <-1> | Up <1>
        float input = event.getCount();
        double zoom = this.camera.getZoom();

        //Change zoom if scrolling.
        if(input < 0) this.camera.setZoom(zoom * (1 - ZOOM_SPEED)); 
        if(input > 0) this.camera.setZoom(zoom * (1 + ZOOM_SPEED)); 
    }

    /**
     * Change iterations max using keyboard.
     */
    public void keyPressed(){
        final int UP   = 87;
        final int DOWN = 83;
        switch(this.keyCode){
            case UP:
                cameraMove();
                maxIterations = maxIterations + ITERATIONS_INCREASE_STEP <= ITERATIONS_LIMIT ? maxIterations + ITERATIONS_INCREASE_STEP : maxIterations;
                break;
            case DOWN:
                cameraMove();
                maxIterations = maxIterations - ITERATIONS_INCREASE_STEP > 0 ? maxIterations - ITERATIONS_INCREASE_STEP : maxIterations;
                break;
        }
    }

    /**
     * Draw all elements by current frame.
    */
    public void draw() {
        //Display current iteration.
        this.iterationText.setText("Maximum Iterations: " + maxIterations);

        //Draw the graph.
        this.camera.render(maxIterations, this);
        this.titleText.draw(this);
        this.iterationText.draw(this);
        this.instructionText.draw(this);

        //Set back to camera stop
        cameraStop();
    }

    /**
     * Set Camera movement status.
     */
    public void cameraMove(){
        this.camera.setMoving(true);
    }

    /**
     * Set Camera movement status.
     */
    public void cameraStop(){
        this.camera.setMoving(false);
    }

    public static void main(String[] args) {
        PApplet.main("Mandelbrot.App");
    }
}
