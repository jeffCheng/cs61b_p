public class NBody {

	public static double readRadius(String fileName) {
		double radius = 0.0;
		In in = new In(fileName);
		while (!in.isEmpty()) {
			int N = in.readInt();
			radius = in.readDouble();
			break;
		}
		return radius;
	}

	public static void drawBackground(double radius) {
		// set scale so that it matches the radius of the universe
		StdDraw.setScale(-radius, radius);

		// draw the image starfield.jpg
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		StdDraw.show();
	}

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int N = in.readInt();
		System.out.println(N);
		Planet[] toRtn = new Planet[N];
		double radius = in.readDouble();
		int i = 0;
		while (i != N) {
			double XPos = in.readDouble();
			double YPos = in.readDouble();
			double XVel = in.readDouble();
			double YVel = in.readDouble();
			double mass = in.readDouble();
			String planetName = in.readString();
			Planet toAdd = new Planet(XPos, YPos, XVel, YVel, mass,  planetName);
			toRtn[i] = toAdd;
			i++;
		}
		return toRtn;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);
		drawBackground(radius);
		int num = 0;
		for (Planet planet : planets) {
			planet.draw();
			num++;
		}

		//Creating an Animation
		double time = 0.0;

		while (time < T) {
			int index = 0;
			double[] xForces = new double[num];
			double[] yForces = new double[num];
			// Calculate the net x and y forces for each planet
			for (Planet planet : planets) {
				xForces[index] = planet.calcNetForceExertedByX(planets);
				yForces[index] = planet.calcNetForceExertedByY(planets);
				index++;
			}
			int i = 0;
			// Call update on each of the planet
			for (Planet planet : planets) {
				planet.update(dt, xForces[i], yForces[i]);
				i++;
			}

			StdDraw.picture(0.0, 0.0, "images/starfield.jpg");
			for (Planet planet : planets) {
				planet.draw();
			}
			// Pause the animation for 10 milliseconds
			StdDraw.show(10);

			// Increase you time variable by dt
			time = time + dt;
			//System.out.println(time);
		}
		
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
		}
	}
}
