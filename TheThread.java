
import org.apache.commons.math3.complex.Complex;
public class TheThread extends Thread{
	
	private int id;

	private final static int MAX_ITERATIONS = 150;
	private final static int RADIUS = 2;
	private int WIDTH = 640;
	private int HEIGHT = 640;
	private int FROM_WIDTH = 0;
	private int TO_WIDTH= 0;
	
	int[][] SET = new int[WIDTH][HEIGHT];
	
	TheThread(int id, int width,int height, int fwidth, int twidth, int[][] set){
    	this.id = id;
    	this.WIDTH = width;
    	this.HEIGHT = height;
    	this.FROM_WIDTH = fwidth;
    	this.TO_WIDTH = twidth;
    	this.SET = set;
    }
	
	public void run() {
		
		for(int i = FROM_WIDTH; i < TO_WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				
				double cr = (4.0 * i - 2 * WIDTH) / WIDTH;
				double ci = (4.0 * j - 2 * HEIGHT) / HEIGHT;
				
				Complex Zc = new Complex(cr, ci);
				Complex Zn = new Complex(0, 0);
				Complex Ze = new Complex(Math.E);
				
				int k = 0;
	            while (k < MAX_ITERATIONS && Zn.abs() < RADIUS ) {

	                Zn = Zn.multiply(Zn).add(Zc);
	              //Zn = Ze.pow(Zn);
	                k++;
	            }

	            SET[i][j] = k;
				
			}
		}
	}
}
