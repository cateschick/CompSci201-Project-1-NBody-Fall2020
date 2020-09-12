// Cate Schick
// Comp Sci 201
// P1

import java.util.Scanner;

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){

		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		this(b.getX(), b.getY(), b.getXVel(), b.getYVel(), b.getMass(), b.getName());

	}

	/**
	 * Returns the x-position of this Body
	 * @return
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 * Returns the y-position of this Body
	 * @return
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Returns the x-velocity of this Body
	 * @return
	 */
	public double getXVel() {
		return myXVel;
	}

	/**
	 * Return y-velocity of this Body.
	 * @return
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 * Returns the mass of this Body
	 * @return
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 * Returns the filename of this Body
	 * @return
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double rsquared = ((getX() - b.getX()) * (getX() - b.getX())) +
				((getY() - b.getY()) * (getY() - b.getY()));
		return Math.sqrt(rsquared);
	}

	/**
	 * Returns the total force exerted by the Body
	 * @param b
	 * @return
	 */
	public double calcForceExertedBy(CelestialBody b) {
		double g = (6.67 * 1e-11);
		double distance = calcDistance(b);
		double force = g * ((getMass() * b.getMass()) / (distance * distance));
		return force;
	}

	/**
	 * Returns the force exerted in the x-direction
	 * @param b
	 * @return
	 */
	public double calcForceExertedByX(CelestialBody b) {
		double r = calcDistance(b);
		double f = calcForceExertedBy(b);
		double dx = (b.getX() - getX());
		double fx = f * (dx / r);

		return fx;
	}

	/**
	 * Returns the force exerted in the y-direction
	 * @param b
	 * @return
	 */
	public double calcForceExertedByY(CelestialBody b) {
		double r = calcDistance(b);
		double f = calcForceExertedBy(b);
		double dy = (b.getY() - getY());
		double fy = f * (dy / r);

		return fy;
	}

	/**
	 * Returns the net force exerted by all bodies in the x-direction
	 * @param bodies
	 * @return
	 */
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0;
		for(int i = 0; i < bodies.length; i++)
		{
			if(!bodies[i].equals(this)) {
				sum += calcForceExertedByX(bodies[i]);
			}
		}
		return sum;
	}

	/**
	 * Returns the net force exerted by all bodies in the y-direction
	 * @param bodies
	 * @return
	 */
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0;
		for(int i = 0; i < bodies.length; i++)
		{
			if(!bodies[i].equals(this)) {
				sum += calcForceExertedByY(bodies[i]);
			}
		}
		return sum;
	}

	/**
	 * Returns an updated state variable of CelestialBody object
	 * @param deltaT
	 * @param xforce
	 * @param yforce
	 */
	public void update(double deltaT, 
			           double xforce, double yforce) {
		// get acceleration
		double ax = xforce/this.getMass();
		double ay = yforce/this.getMass();

		// get new position values
		double nvx = deltaT*ax + myXVel;
		double nvy = deltaT*ay + myYVel;

		myXVel = nvx;
		myYVel = nvy;

		// Positions need to update by adding deltaT*nv(x or y)
		myXPos = myXPos + deltaT*nvx;
		myYPos = myYPos + deltaT*nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
