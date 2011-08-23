import java.text.DecimalFormat;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;

public class Item {

	private PApplet _p;
	private PFont[] fonts;
	
	private float _w = 725;
	private float _h = 75;
	
	private PImage arrow;
	private PImage shadow;
	Vector3D _loc;
	Vector3D _vel, _acc;
	
	Vector3D _destLoc;
	
	
	private float _maxvel;
	private float _damper;
	private float _alpha;
	private float _alphaOrig;
	private String _total;
	
	
	
	private boolean	inbounds;
	private boolean	over;
	private boolean	pressed;
	private ItemManager _itemManager;
	
	private Boolean _selected;
	
	private Receipt _r;
	
	private String _elements[];
	
	
	private String[] _values;
	public Item(PApplet p_, ItemManager itemManager_, PFont[] fonts_,Vector3D loc_, Vector3D destLoc_, float alpha_, String[] values, String[] elements_)
	{
		_itemManager = itemManager_;
		_alpha = alpha_;
		_alphaOrig = alpha_;
		_p = p_;
		fonts = fonts_;
		arrow = _p.loadImage("arrow.png");
		shadow = _p.loadImage("shadow.png");
		
		_values = values;
		_destLoc = destLoc_;
		_loc = loc_;
		_damper =  0.3f;
		_elements = elements_;
		
		
		_selected = false;
		_acc = new Vector3D(0,0,0);
		_vel = new Vector3D(0,0,0);
		
		double total_ = Double.parseDouble(values[0]) + Double.parseDouble(values[1]) + Double.parseDouble(values[2]) + Double.parseDouble(values[3]) - Double.parseDouble(values[4]);
		
		DecimalFormat valueFormat = new DecimalFormat("####.##") ;
		//System.out.println( "Divided Value before formating "+ value );
		
		total_=Math.floor(total_*100.0);
		
		_total = valueFormat.format(total_/100.0);
		
		_r = new Receipt(p_, itemManager_, fonts_, loc_, _values, _elements, _total);
		
		
		
	}//endfunction
	
	
	
	public void drawItem()
	{
		checkOver();
		checkPress();
		
		_p.noFill();
		_p.noStroke();
		//_p.stroke(200,110,115);
		_p.fill(255, 255, 255,_alpha);
		_p.rectMode(PConstants.CORNER);
		_p.rect(_loc.x,_loc.y, _w, _h);
		//value
		_p.fill(0, 0, 0,100);
		_p.textFont(fonts[0], 22); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		String s = "$ "+_total;
		float valueWidth = _p.textWidth(s);
		float th = _p.textAscent()+_p.textDescent();
		//_p.println("th: " + th);
	 	_p.text(s, _loc.x + 20, _loc.y + (_h-th)/2);
	 	
	 	//title
	 	_p.fill(0, 0, 0,100);
		_p.textFont(fonts[1], 14); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		String title = "Al-Maliki's Announcement: A Big Deal ";
		float titleWidth = _p.textWidth(title);
		float titleHeight = _p.textAscent()+_p.textDescent();
		//_p.println("th: " + th);
	 	_p.text(_elements[0], _loc.x + 30+80+35, _loc.y + (_h-36)/2);
	 	
	 	//date
		_p.fill(0, 0, 0,100);
		_p.textFont(fonts[2], 10); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		//String date = "on July 18, 2008 6:25 AM";
		float dateHeight = _p.textAscent()+_p.textDescent();
		//_p.println("th: " + th);
	 	_p.text(_elements[1], _loc.x + 30+80+35, _loc.y + (_h-36)/2+20);

	 	//arrow
	 	if(_selected)
	 	{
	 		_r.drawReceipt();
	 		_r.mouseCheck();
	 		//_r.mousePressed();
	 		//_p.image(arrow, _loc.x + _w-50, _loc.y + (_h-20)/2);
	 		_p.image(shadow, _loc.x, _loc.y);
	 		_alpha = 20;
	 		
	 		//url
	 		_p.fill(0, 0, 0,200);
			_p.textFont(fonts[2], 10); 
			_p.textAlign(PConstants.LEFT,PConstants.TOP);
			//String date = "on July 18, 2008 6:25 AM";
			float urlHeight = _p.textAscent()+_p.textDescent();
			//_p.println("th: " + th);
		 	_p.text(_elements[2], _loc.x + 20, _loc.y + 100 );
		 	
		 	//below box
		 	_p.noFill();
			_p.noStroke();
			//_p.stroke(200,110,115);
			_p.fill(255, 255, 255,30);
			_p.rectMode(PConstants.CORNER);
			_p.rect(_loc.x,_loc.y + 130, _w, 300);
			
			//description
	 		_p.fill(0, 0, 0,200);
			_p.textFont(fonts[2], 10); 
			_p.textAlign(PConstants.LEFT,PConstants.TOP);
			//String date = "on July 18, 2008 6:25 AM";
			float descrHeight = _p.textAscent()+_p.textDescent();
			//_p.println("th: " + th);
			//text(stringdata, x, y, width, height, z)
			_p.text("Brief Summary of the Page:", _loc.x + 20+10, _loc.y + 230, 364, 300 );
		 	_p.text(_elements[3], _loc.x + 20+10, _loc.y + 260, 364, 300 );
			
		 	
		 	//readability
		 	_p.fill(255, 255, 255,255);
	 		
			
	 		_p.textFont(fonts[6], 30); 
	 		_p.text("$ " + _values[1], _loc.x + 450, _loc.y + 202);
	 		
	 		_p.textFont(fonts[5], 12); 
	 		_p.text("Readability", _loc.x + 450, _loc.y + 180);
	 		
	 		//feedback
		 	_p.fill(255, 255, 255,255);
	 		
			
	 		_p.textFont(fonts[6], 30); 
	 		_p.text("$ " + _values[2], _loc.x + 600, _loc.y + 202);
	 		
	 		_p.textFont(fonts[5], 12); 
	 		_p.text("Feedback", _loc.x + 600, _loc.y + 180);
	 		
	 		//trackback
		 	_p.fill(255, 255, 255,255);
	 		
			
	 		_p.textFont(fonts[6], 30); 
	 		_p.text("$ " + _values[0], _loc.x + 450, _loc.y + 282);
	 		
	 		_p.textFont(fonts[5], 12); 
	 		_p.text("Trackback", _loc.x + 450, _loc.y + 260);
	 		
	 		//popularity
		 	_p.fill(255, 255, 255,255);
	 		
			
	 		_p.textFont(fonts[6], 30); 
	 		_p.text("$ " + _values[3], _loc.x + 600, _loc.y + 282);
	 		
	 		_p.textFont(fonts[5], 12); 
	 		_p.text("Popularity", _loc.x + 600, _loc.y + 260);
	 		
	 		//energy
		 	_p.fill(255, 255, 255,255);
	 		
			
	 		_p.textFont(fonts[6], 30); 
	 		_p.text("$ " +"-"+ _values[4], _loc.x + 450, _loc.y + 362);
	 		
	 		_p.textFont(fonts[5], 12); 
	 		_p.text("Energy", _loc.x + 450, _loc.y + 340);
		 	
		 	
	 	}//endif
	}//endfunction
	
	public void update() {
		_vel = Vector3D.sub(_destLoc, _loc);
		//_p.println(_loc.y);
		_vel.mult(_damper);
		_loc.add(_vel);
		_vel.limit(_maxvel);
	}//endfunction
	
	
	private void checkOver() {
		if(!_selected)
		{
			if(over) {
				//_p.fill(100,100,100);
				_p.image(arrow, _loc.x + _w-50, _loc.y + (_h-20)/2);
		 		_p.image(shadow, _loc.x, _loc.y);
				_alpha = 80;
			} else {
				//_p.fill(211,211,211);
				_alpha = _alphaOrig;
			}//endelse
		}//endif
	}
	
	private void checkPress() 
	{
		if(pressed) {
			if(!_selected)
			{
			//System.out.println("pressed");
			_selected = true;
			_itemManager.moveItems();
			}//endif
			pressed = false;
			
		}
	}
	
	public void mouseCheck() {
		
		if(inbounds()) {
			over = true;
		} else { 
			over = false;
		}
	}
	
	public void mousePressed() {
		if(inbounds()) {
			pressed = true;
		} else { 
			pressed = false;
		}
	}

	private boolean inbounds() {
		Vector3D mouseCoord = new Vector3D(_p.mouseX, _p.mouseY);
		Vector3D newLoc 	= new Vector3D(_loc.x + _w/2, _loc.y + _h/2);
		
		Vector3D diff = Vector3D.sub(mouseCoord, newLoc);
		if(Math.abs(diff.x) < _w/2 && Math.abs(diff.y) < _h/2) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setX(float x)
	{
		_destLoc.setX(x);
	}//endfunction
	

	public void setY(float y)
	{
		_destLoc.setY(y);
	}//endfunction
	
	
	public float getHeight()
	{
		return _h;
	}//endfunction
	
	public float getY()
	{
		return _loc.y;
	}
	
	public Receipt receipt()
	{
		return _r;
	}//endfunction
	
	public void selected(Boolean v)
	{
		_selected = v;
	}
	public Boolean selected()
	{
		return _selected;
	}//endfunction

	
}
