package Mandelbrot;

import Mandelbrot.GeneralClass.Color;
import processing.core.PApplet;

/**
 * This is the render camera object.
 */
public class RenderCamera extends Camera{

    /**
     * Implementation of the color scheme rule.
     */
    @Override
    public Color rule(int iterations, int maxIterations) {
        //Set the color of the graph.
        final int MAX_COLOR_VALUE = 255;
        final double R = 1.0, G = 0.5, B = 0.65;
        final double EXPOSURE = 2;

        //Get value between 0 - 1.
        double value = (double) iterations / (double) maxIterations;
        int brightness = (int) (MAX_COLOR_VALUE * value * EXPOSURE);

        //Apply color
        return new Color((int) (R * brightness), (int) (G * brightness), (int) (B * brightness));
    }

    /**
     * Render the given pixels.
     */
    @Override
    public void renderPixels(int[] pixelIndexList, Double a, Double b, int maxIterations, PApplet mainProgram) {
        //Get result and get color for pixel.
        int result = MandelbrotLogic.startCalculate(a, b, maxIterations);
        Color colorScheme = rule(result, maxIterations);

        //Render pixels with color rules.
        for(int pixelIndex: pixelIndexList){
            mainProgram.pixels[pixelIndex] = mainProgram.color(colorScheme.getR(), colorScheme.getG(), colorScheme.getB());;
        }
    }
}
