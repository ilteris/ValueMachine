import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;


public class LoadBar {
	
	private float _w = 725;
	private float _h = 75;
	
	
	
	private PApplet _p;
	Vector3D _loc;
	
	Vector3D _vel, _acc;
	
	private float _alpha;
	private float _alphaOrig;
	
	private Boolean _loading = true;
	
	
	private float _maxvel = 5.0f;
	private float _damper = 0.3f;
	
	private PFont[] _fonts;
	
	
	Vector3D _destLoc;
	
	public LoadBar(PApplet p_, PFont[] fonts_)
	{
		_p = p_;
		_fonts = fonts_;
		
		_acc = new Vector3D(0,0,0);
		_vel = new Vector3D(0,0,0);
		_loc = 	new Vector3D(157,1057,0);
		
			
		
		_destLoc = _loc.copy();
		
		
	}
	
	

	public void update() {
		_vel = Vector3D.sub(_destLoc, _loc);
		//_p.println(_loc.y);
		_vel.mult(_damper);
		_loc.add(_vel);
		_vel.limit(_maxvel);
	}//endfunction
	
	
	
	public void draw() {
		
		if(_loading)
		{
		update();
		_p.noFill();
		_p.noStroke();
		//_p.stroke(200,110,115);
		_p.fill(255, 255, 255,10);
		_p.rectMode(PConstants.CORNER);
		_p.rect(_loc.x,_loc.y, _w, _h);
		
		
		//date
		_p.fill(0, 0, 0,150);
		_p.textFont(_fonts[2], 10); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		float dateHeight = _p.textAscent()+_p.textDescent();
		//_p.println("th: " + th);
	 	_p.text("Loading, thanks for your patience...", _loc.x + 20, _loc.y + (_h-dateHeight)/2);
	 	
		

		}//endif
	}//endfunction
	
	public Boolean loading()
	{
		return _loading;
	}//endfunction
	
	public void loading(Boolean b)
	{
		_loading = b;
	}
	
	public void setDestLocY(float y)
	{
		_destLoc.setY(y);
	}//endfunction

}
