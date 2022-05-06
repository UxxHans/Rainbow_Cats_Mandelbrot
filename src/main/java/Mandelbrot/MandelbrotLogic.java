package Mandelbrot;

/**
 * This class contains the calculation logic of the Mandelbrot Set.
 */
public class MandelbrotLogic {

	/**
	 * Start calculating the iteration it takes to move out of bound.
	 * @param a Value of constant a in c = a + bi from the last iteration.
	 * @param b Value of constant b in c = a + bi from the last iteration.
	 * @return Return (1 - Max Iteration) if out of bound. Return 0 if still in bound after Max Iteration.
	 */
    public static int startCalculate(double a, double b, int maxIterations){
        return calculate(0, 0, a, b, 0, maxIterations);
    }

	/**
	 * Recursive function representing repeating iteration.
	 * @param a Value of a in z = a + bi from the last iteration.
	 * @param b Value of b in z = a + bi from the last iteration.
	 * @param ca Value of constant a in c = a + bi from the last iteration.
	 * @param cb Value of constant b in c = a + bi from the last iteration.
	 * @param iteration Which iteration is the current one? It is used to stop the calculation.
	 * @return Return (1 - Max Iteration) if out of bound. Return 0 if still in bound after Max Iteration.
	 */
	public static int calculate(double a, double b, double ca, double cb, int iteration, int maxIterations){
		//If the mod is larger than 2, it is not in the set.
		double MAX_MOD = 2;

		//If exceed max iteration, return, else add iteration.
		if(iteration >= maxIterations){
			return 0;
		}else{
			iteration += 1;
		}

		//Calculate the iteration.
		double na = Math.pow(a,2) - Math.pow(b,2) + ca;
		double nb = 2 * a * b + cb;
		double mod = Math.sqrt(Math.pow(na,2) + Math.pow(nb,2));

		//Check is out of bound. If outside, return the iteration.
		if(mod > MAX_MOD){
			return iteration;
		}else{
			return calculate(na, nb, ca, cb, iteration, maxIterations);
		}
	}
}
