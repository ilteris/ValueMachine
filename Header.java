import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;


public class Header {
	
	private PApplet _p;
	private PFont[] _fonts;
	Vector3D _loc;
	Vector3D _vel, _acc;
	
	Vector3D _hvel, _hacc;
	
	Vector3D _destLoc;
	
	private float _w = 725;
	private Vector3D _h = new Vector3D (0, 270, 0); //154 for later
	
	
	private float _maxvel = 5.0f;
	private float _damper;
	
	private int mass = 10; 
	private int k = 1;
	

	private String _readability = "";
	
	private String _text = "Please type the keyword you want to search below and press ENTER:";
	private String _origText = "Please type the keyword you want to search below and press ENTER:";
	
	
	
	Vector3D _destH = new Vector3D(0,270,0);
	
	private Boolean _up = false;
	
	private PImage b;
	
	private LoadBar _loadbar;
	
	public Header(PApplet p_, PFont[] fonts_, LoadBar loadbar_)
	{
		_p = p_;	
		_fonts = fonts_;
		_loc = new Vector3D(157,158,0); //-157
		_acc = new Vector3D(0,0,0);
		_vel = new Vector3D(0,0,0);
		
		_hacc = new Vector3D(0,0,0);
		_hvel = new Vector3D(0,0,0);
		
		_destLoc = _loc.copy();
		_damper =  0.3f;
		
		_loadbar = loadbar_;
		

		
		
	}
	
	public void update()
	{
		_vel = Vector3D.sub(_destLoc, _loc);
		//_p.println(_loc.y);
		_vel.mult(_damper);
		_loc.add(_vel);
		_vel.limit(_maxvel);
		
		
		// shrink
		Vector3D force = Vector3D.sub(_h,_destH);
	    force.mult(-k);
	    force.div(mass);

	    _hacc.setXYZ(0,0,0);
	    _hacc.add(force);
	    _hvel.add(_hacc);
	    _hvel.mult(0.7f);
	    _h.add(_hvel);
	}//endfunction
	
	public void draw()
	{
		_p.noFill();
		_p.noStroke();
		_p.fill(0, 0, 0,20);
		_p.rectMode(PConstants.CORNER);
		_p.rect(_loc.x,_loc.y, _w, _h.y);
		
		int yspace = 35;
		//value
		_p.fill(255, 0, 0,255);
		_p.textFont(_fonts[3], 18); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		String s = "VALUE MACHINE > ";
		float valueWidth = _p.textWidth(s);
	 	_p.text(s, _loc.x + 20, _loc.y + yspace); //36
	 	
	 	int d = PApplet.day();    // Values from 1 - 31
	 	int m = PApplet.month();  // Values from 1 - 12
 	  	int y = PApplet.year();   // 2003, 2004, 2005, etc.
 	  	String ds = String.valueOf(d) + ".";
 	  	_p.text(ds, _loc.x + 20 + valueWidth + 5, _loc.y + yspace);
 	  	float dsWidth = _p.textWidth(ds);
 	  	
 	  	String ms = String.valueOf(m) + ".";
 	  	_p.text(ms,  _loc.x + 20 + valueWidth + 5 + dsWidth + 3, _loc.y + yspace);
 	  	float msWidth = _p.textWidth(ms);
 	  	
 	  	
 	  	String ys = String.valueOf(y);
 	  	_p.text(ys,  _loc.x + 20 + valueWidth + 5 + dsWidth + 3 + msWidth + 3, _loc.y + yspace);
	 	
 	  	
 	  	//date
		_p.fill(0, 0, 0,150);
		_p.textFont(_fonts[2], 10); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		float dateHeight = _p.textAscent()+_p.textDescent();
		//_p.println("th: " + th);
	 	_p.text(_text, _loc.x + 20, _loc.y + 120);
	 	
	 	
	 	
	 	if(_up)
	 	{
	 	//readability

	 		int space = 150;
	 		
	 		_p.fill(0, 255, 0,255);
	 		_p.pushMatrix();
	 		_p.translate(25, 130);
	 		_p.beginShape(PApplet.TRIANGLES);
	 		
			_p.vertex(_loc.x, _loc.y-8);
			_p.vertex(_loc.x-5, _loc.y);
			_p.vertex(_loc.x+5, _loc.y);
			_p.endShape();
			_p.popMatrix();
			_p.textFont(_fonts[5], 12); 
	 		_p.text("Readability", _loc.x + 20, _loc.y + 100);
	 		_p.text("$ 5432.0", _loc.x + 40, _loc.y + 122);
	 		
	 		//feedback
	 	
	 		_p.fill(0, 255, 0,255);
	 		_p.pushMatrix();
	 		_p.translate(25+space, 130);
	 		_p.beginShape(PApplet.TRIANGLES);
	 		
			_p.vertex(_loc.x, _loc.y-8);
			_p.vertex(_loc.x-5, _loc.y);
			_p.vertex(_loc.x+5, _loc.y);
			_p.endShape();
			_p.popMatrix();
			_p.textFont(_fonts[5], 12); 
	 		_p.text("Feedback", _loc.x + 20+space, _loc.y + 100);
	 		_p.text("$ 2323.3", _loc.x + 40+space, _loc.y + 122);
	 		
	 		//trackback
		 	
	 		_p.fill(0, 255, 0,255);
	 		_p.pushMatrix();
	 		_p.translate(25+space*2, 130);
	 		_p.beginShape(PApplet.TRIANGLES);
	 		
			_p.vertex(_loc.x, _loc.y-8);
			_p.vertex(_loc.x-5, _loc.y);
			_p.vertex(_loc.x+5, _loc.y);
			_p.endShape();
			_p.popMatrix();
			_p.textFont(_fonts[5], 12); 
	 		_p.text("Trackback", _loc.x + 20+space*2, _loc.y + 100);
	 		_p.text("$ 4183.3", _loc.x + 40+space*2, _loc.y + 122);
	 		
	 		
			
	 		//popularity
		 	
	 		_p.fill(0, 255, 0,255);
	 		_p.pushMatrix();
	 		_p.translate(25+space*3, 130);
	 		_p.beginShape(PApplet.TRIANGLES);
	 		
			_p.vertex(_loc.x, _loc.y-8);
			_p.vertex(_loc.x-5, _loc.y);
			_p.vertex(_loc.x+5, _loc.y);
			_p.endShape();
			_p.popMatrix();
			_p.textFont(_fonts[5], 12); 
	 		_p.text("Popularity", _loc.x + 20+space*3, _loc.y + 100);
	 		_p.text("$ 4183.3", _loc.x + 40+space*3, _loc.y + 122);
	 		

			
	 		//energy
		 	
	 		_p.fill(255, 0, 0,255);
	 		_p.pushMatrix();
	 		_p.translate(25+space*4, 130);
	 		_p.beginShape(PApplet.TRIANGLES);
	 		
			_p.vertex(_loc.x, _loc.y+8);
			_p.vertex(_loc.x-5, _loc.y);
			_p.vertex(_loc.x+5, _loc.y);
			_p.endShape();
			_p.popMatrix();
			_p.textFont(_fonts[5], 12); 
	 		_p.text("Energy", _loc.x + 20+space*4, _loc.y + 100);
	 		_p.text("$ 4183.3", _loc.x + 40+space*4, _loc.y + 122);

	 	}//endif
	 	
	}//endfunction
	
	public void setBackToOriginal()
	{
		_destH.setXYZ(0,270,0);
		_text = _origText;
		_destLoc = new Vector3D(157,158,0);

		
	}
	public void setNewHeight(String s)
	{
		_destH.setXYZ(0,154,0);
		_text = "";
		_up = true;
		_loadbar.loading(true);
		_loadbar.setDestLocY(158);
	}//endfunction

	public void up(Boolean b)
	{
		_up = b;
	}
		
	public void setDestLoc(Vector3D v)
	{
		_destLoc = v;
		
	}//endfunction
	
}
