/**
 * @author Cate Schick
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));

		// ignore number of bodies
		s.nextInt();

		double rad = s.nextDouble();
		s.close();

		return rad;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {

		Scanner s = new Scanner(new File(fname));

		// TODO: read # bodies, store in nb

		int nb = s.nextInt(); // # bodies to be read

		// TODO: Create array that can store nb CelestialBodies

		CelestialBody[] array = new CelestialBody[nb];

		// TODO: read and ignore radius

		s.nextDouble();

		for(int k=0; k < array.length; k++) {

			// TODO: read data for each body

			double xp = s.nextDouble();
			double yp = s.nextDouble();
			double xv = s.nextDouble();
			double yv = s.nextDouble();
			double mass = s.nextDouble();
			String filename = s.next();

			// TODO: construct new body object and add to array

//			CelestialBody body = new CelestialBody(xp, yp, xv, yv, mass, filename);
//			array[i] = body;
//			i++;
			array[k] = new CelestialBody(xp, yp, xv, yv, mass, filename);
		}
		// TODO: return array of body objects read
		return array;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 39447000.0;
		double dt = 25000.0;

		String fname= "./data/planets.txt";

		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}

		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");

		// TODO: for music/sound, uncomment next line

		StdAudio.play("images/2001.wav");

//		 run simulation until over

		for(double t = 0.0; t < totalTime; t += dt) {
			
			// TODO: create double arrays xforces and yforces
			//       to hold forces on each body

			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];

//			// TODO: loop over all bodies
			int numforces = 0;

			for(CelestialBody planet : bodies) {
				// TODO: calculates netForcesX and netForcesY and store in
				//       arrays xforces and yforces

				xforces[numforces] = planet.calcNetForceExertedByX(bodies);
				yforces[numforces] = planet.calcNetForceExertedByY(bodies);
				numforces += 1;
			}

				// TODO: loop over all bodies and call update
				//       with dt and corresponding xforces and yforces values
				int numupdates = 0;

				for (CelestialBody planet : bodies) {
					planet.update(dt, xforces[numupdates], yforces[numupdates]);
					numupdates += 1;
				}


				StdDraw.clear();
				StdDraw.picture(0,0,"images/starfield.jpg");

			// TODO: loop over all bodies and call draw on each one

				for(CelestialBody planet : bodies) {
					planet.draw();
				}

			StdDraw.show();
			StdDraw.pause(10);

		}
		
		// prints final values after simulation
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
