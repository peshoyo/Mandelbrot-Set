//Name: Petar Angelov
//Date: 08/06/19
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.File;


public class Mandelbrot {
	
	private final static int MAX_ITERATIONS = 100;
	private int HEIGHT = 768;
    	private int WIDTH = 1024;
    	private int NUMBER_THREADS = 4;
	private String NAME = "Mandelbrot.png";
	private Boolean QUIET = false;
	
	static TheThread workers[];
	
	public Mandelbrot(int threads, int width, int height, String name, Boolean q) throws Exception{
		
		this.WIDTH = width;
		this.HEIGHT = height;
		this.NUMBER_THREADS = threads;
		this.NAME = name;
		this.QUIET = q;
		int[][] set = new int[WIDTH][HEIGHT];
		
		long startTime = System.currentTimeMillis();
		
		
		int STEP = WIDTH/NUMBER_THREADS;
		int T = 0;
		workers = new TheThread[NUMBER_THREADS];
		//Workers doing their job.
		for(int i = 0; i < NUMBER_THREADS; i++) {
			TheThread worker = new TheThread(i, WIDTH, HEIGHT, T, STEP + T, set);
			worker.start();
			workers[i] = worker;
			T = T + STEP;
		}
		//Workers joining for lunch after finishing the work.
		for(int i = 0; i < NUMBER_THREADS; i++) {
			workers[i].join();
		}
		
		
		long endTime = System.currentTimeMillis();

	    System.out.println("Calculation completed in "
	            + (endTime - startTime) + " milliseconds");
	    
	    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	    //Coloring the image.
	    for (int i = 0; i < WIDTH; i++) {
	        for (int j = 0; j < HEIGHT; j++) {

	            int k = set[i][j];

	            float level;
	            if (k < MAX_ITERATIONS) {
	                level = (float) k / MAX_ITERATIONS;
	            } else {
	                level = 0;
	            }
	            Color c = new Color(0, level, level);  
	            image.setRGB(i, j, c.getRGB());
	        }
	    }

	    // Writing file.
	    ImageIO.write(image, "PNG", new File(NAME));
	}
	//TODO:
	//Quiet option.
	//Name option.
	
	
	public static void main(String args[]) throws Exception{
		Boolean Quiet = false;
		String name = "Mandelbrot.png";
		int threads = 1;
		int Width = 1080;
		int Height = 1080;
		if("-t".equals(args[0])){threads = Integer.parseInt(args[1]);}
		new Mandelbrot(threads, Width, Height, name, Quiet);
		
	}
}
