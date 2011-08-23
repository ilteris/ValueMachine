import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

class InputBox {

	private PImage b;
	
	Vector3D _loc;
	Vector3D _vel, _acc;
	
	Vector3D _destLoc;
	
	
	String keyboardInput;
	String displaytext;
	boolean initialClick;
	PApplet p;

	private float _damper;
	
	public InputBox(PApplet p_) {
		p = p_;
		initialClick = false;
		keyboardInput = "";
		displaytext = "";
		
		_damper =  0.3f;
		
		_loc = new Vector3D(167,318,0); 
		_acc = new Vector3D(0,0,0);
		_vel = new Vector3D(0,0,0);
		_destLoc = _loc.copy();
		
		b = p.loadImage("searchbox.png");

	}	

	public void draw() {
		p.image(b, _loc.x, _loc.y);
		// println(initialClick);
		PFont f1 = p.loadFont("HelveticaNeue-20.vlw");
		p.fill(0);
		p.textFont(f1);
		p.textSize(20);
      
		if(keyboardInput.length() == 0) 
		{
			displaytext = "";
			if(initialClick) {
				displaytext = "";
			} 
			else 
			{
				displaytext = "";
			} 
		} 
		else if (keyboardInput == "") 
		{
			displaytext = "";
		} 
		else 
		{
			displaytext = keyboardInput;
		}
    
		float sw = p.textWidth(displaytext);
		p.stroke(0,0,0,255);
		p.line(_loc.x  + sw+30, _loc.y+30, _loc.x + sw+30,_loc.y + 55);
		p.text(displaytext,_loc.x+30,_loc.y+35); 
	}

	public void update()
	{
		_vel = Vector3D.sub(_destLoc, _loc);
		//_p.println(_loc.y);
		_vel.mult(_damper);
		_loc.add(_vel);
	}//endfunction
	
	
	public void setDestLoc(Vector3D v)
	{
		_destLoc = v;
	}//endfunction
  
	
  
}

