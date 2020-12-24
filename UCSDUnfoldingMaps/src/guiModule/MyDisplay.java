package guiModule;
import processing.core.PApplet;

public class MyDisplay extends PApplet {

	
	public void setup() {
		size(400,400); //setting up the size with the parameters as width and height
		background(0,0,0); //rgb color value is the parameter for this method
		//therefore, black(0,0,0) will be shown as a background color
		
		
	}
	public void draw() {
		fill(255,255,255);
		ellipse(200,200,390,390); //this method is for drawing a circle
		//first two parameters are for setting the position of the circle and the other two are for sizing
		fill(0,0,0);
		ellipse(100,130,50,70);
		fill(0,0,0);
		ellipse(280,130,50,70);
		
		noFill();
		arc(200,280,75,75,0,PI);
	}
}
