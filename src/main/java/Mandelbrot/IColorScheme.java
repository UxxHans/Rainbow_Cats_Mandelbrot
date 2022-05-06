package Mandelbrot;

import Mandelbrot.GeneralClass.Color;

/**
 * The interface for art approach.
 */
public interface IColorScheme {
    /**
     * Set the rule of how color reacts to the iteration value within the maximum.
     * @param iterations The current iteration value.
     * @param maxIterations The maximum value of the iteration.
     * @return Return the color correspond to the iteration value.
     */
    abstract Color rule(int iterations, int maxIterations);
}
