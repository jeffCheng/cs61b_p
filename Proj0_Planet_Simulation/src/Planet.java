public class Planet {
	
	//: Its current x position
	public double xxPos;
	//: Its current y position
	public double yyPos;
	//: Its current velocity in the x direction
	public double xxVel;
	//: Its current velocity in the y direction
	public double yyVel;
	//: Its mass
	public double mass;
	//: The name of an image in the images directory that depicts the plane
	public String imgFileName;
	
	public double G = 6.67 * Math.pow(10, -11);
	
	
	public Planet(double xP, double yP, double xV,
            double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass= m;
		imgFileName = img; 
	}
	
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass= p.mass;
		imgFileName = p.imgFileName; 
	}
	
	public double calcDistance (Planet anotherPlant) {
        double dx = this.xxPos - anotherPlant.xxPos;
        double dy = this.yyPos - anotherPlant.yyPos;
        double rSquared = (dx * dx) + (dy * dy);
        return Math.sqrt(rSquared);
	}
	
	// Calculate the force exerted on this planet by the given plane
    public double calcForceExertedBy(Planet anotherPlant) {
        double dist = calcDistance(anotherPlant);
        return G*this.mass*anotherPlant.mass/(dist*dist);
    }
    
    /*
     * sun.calcForceExertedByY(saturn) should return F1,x and F1,y, respectively; 
     * similarly, saturn.calcForceExertedByX(sun) 
     * and saturn.calcForceExertedByY(sun) should return F2,x and F2,y, respectively.
     */
    public double calcForceExertedByX (Planet anotherPlant) {
    	double totalForce = calcForceExertedBy(anotherPlant);
    	double deltaX = anotherPlant.xxPos-this.xxPos;
    	double dist = calcDistance(anotherPlant);
        return totalForce*deltaX/dist;
    }
    
    public double calcForceExertedByY (Planet anotherPlant) {
       	double totalForce = calcForceExertedBy(anotherPlant);
       	double deltaY =anotherPlant.yyPos- this.yyPos;
    	double dist = calcDistance(anotherPlant);
        return totalForce*deltaY/dist;
    }
    
    public double calcNetForceExertedByX(Planet[] allPlanets){
    	double totalNetForce = 0.0;
    	for (Planet planet : allPlanets) {
            if (this.equals(planet)) {
                continue;
            } else {
                double forceX = this.calcForceExertedByX(planet);
                totalNetForce += forceX;
            }
        }
    	return totalNetForce;
    }
    
    public double calcNetForceExertedByY(Planet[] allPlanets){
    	double totalNetForce = 0.0;
    	for (Planet planet : allPlanets) {
            if (this.equals(planet)) {
                continue;
            } else {
                double forceY = this.calcForceExertedByY(planet);
                totalNetForce += forceY;
            }
        }
    	return totalNetForce;
    }
    
    public void update(double dt, double fX, double fY) {
    	double anetX = fX/this.mass;
    	double anetY = fY/this.mass;
    	
    	// new velocity
        this.xxVel += dt * anetX;
        this.yyVel += dt * anetY;
        
        // new position
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }
    
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
    }
 
}
