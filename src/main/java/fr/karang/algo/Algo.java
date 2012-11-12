package fr.karang.algo;

/**
 * Ignore that
 */
public class Algo {

	private static int question1A (double a, double b) {
		int n = 1;
		while (a>=frac(Math.sqrt(n)) || b<=frac(Math.sqrt(n))) {
			n++;
		}
		System.out.println(n);
		return n;
	}
	
	private static int question1B (double a, double b, int nb) {
		int n = 1;
		while (nb>0) {
			while (a>=frac(Math.sqrt(n)) || b<=frac(Math.sqrt(n))) {
				n++;
			}
			System.out.println(n);
			nb--;
			n++;
		}
		return n;
	}
	
	//3 217 280 351 430
	
	/* Question 2
	 * a) B-A>1 et B>A
	 * 	  B>A+1>A donc [A, B] contiens 1 entier A+1
	 * b) B-A>d et B>A
	 *    B>A+d>A donc [A, B] contient d entiers A+1, A+2, ..., A+d
	 */
	
	private static int question3A (double a, double b) {
		double A = a*a;
		double B = b*b;
		int k = 1;
		while (B-A<1) {
			A = (a+k)*(a+k);
			B = (b+k)*(b+k);
			k++;
		}
		
		int n = (int)B;
		return n;
	}
	
	private static int question3B (double a, double b, int nb) {
		double A = a*a;
		double B = b*b;
		int k = 1;
		while (B-A<1) {
			A = (a+k)*(a+k);
			B = (b+k)*(b+k);
			k++;
		}
		
		int n = (int)B;
		for (int i=0 ; i<nb ; i++) {
			System.out.println(n+" "+test(n,a,b));
			n = (int) ((b+k)*(b+k));
			k++;
		}
		
		
		return n;
	}
	
	//2513 2614 2717 2822 2930 3039 3150 3263 3379 3496

	
	// consécutives
	private static void question3C (double a, double b, int nb) {
		double A = a*a;
		double B = b*b;
		int k = 1;
		while (B-A<nb) {
			A = (a+k)*(a+k);
			B = (b+k)*(b+k);
			k++;
		}
		
		int n = (int)A+1;
		for (int i=0 ; i<nb ; i++) {
			System.out.println(n+" "+test(n,a,b));
			n++;
		}
	}
	
	// 250121 250122 250123 250124 250125 250126 250127 250128 250129 250130

	
	private static double frac(double a) {
		return a - ((int) a);
	}
	
	private static boolean test(int n, double a, double b) {
		double c = frac(Math.sqrt(n));
		return ((c>a && c<b));
	}

	public static void main (String [] args) {
		question3C(0.12, 0.13, 10);
	}
}