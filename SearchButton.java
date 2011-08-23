import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;


public class SearchButton {


	private PApplet _p;
	Vector3D _loc;
	
	Vector3D _vel, _acc;
	
	Vector3D _destLoc;
	
	
	private float _w = 123;
	private float _h = 37;
	

	private boolean	inbounds;
	private boolean	over;
	private boolean	pressed;
	
	private Boolean _selected;
	private float _damper = 0.3f;
	private float _maxvel = 5.0f;
	
	
	private float _alpha;
	
	private ItemManager _itemManager;
	private PFont[] _fonts;
	
	private Header _header;
	
	private InputBox _inputbox;
	private Dataminer _dataminer;
	
	private Boolean up = false;
	
	public SearchButton(PApplet p_, PFont[] fonts_, ItemManager itemManager_, Header header_, InputBox inputbox_,Dataminer dataminer_)
	{
		_p = p_;
		_fonts = fonts_;
		_itemManager = itemManager_;
		_header = header_;
		_inputbox = inputbox_;
		_alpha = 100;
		_dataminer = dataminer_;
		_loc = new Vector3D(745,204,0);
		_acc = new Vector3D(0,0,0);
		_vel = new Vector3D(0,0,0);
		_destLoc = _loc.copy();
		
	}
	
	
	public void update()
	{
		_vel = Vector3D.sub(_destLoc, _loc);
		//_p.println(_loc.y);
		if(_vel.y >= -0.1f)
		{ 
			_vel.setXYZ(0, 0, 0);
		}//endif
		_vel.mult(_damper);
		_loc.add(_vel);
		_vel.limit(_maxvel);
	}//endfunction
	
	public void draw()
	{
		if(!_dataminer.running())
		{
			checkOver();
			checkPress();
		}//endif
		if(up)
		{
			//newsearch
		 	//print button
	 		_p.noFill();
			_p.noStroke();
			//_p.stroke(200,110,115);
			_p.fill(0, 0, 0,_alpha);
			_p.rectMode(PConstants.CORNER);
			_p.rect(_loc.x,_loc.y, 123, 37);
			
			//print receipt
			
			_p.fill(255, 0, 0,100);
			_p.textFont(_fonts[3], 18); 
			_p.textAlign(PConstants.LEFT,PConstants.TOP);
			String receipt = "New Search";
			float receiptWidth = _p.textWidth(receipt);
			float receiptHeight = _p.textAscent()+_p.textDescent();
			//_p.println("th: " + th);
		 	_p.text(receipt, _loc.x+(_w-receiptWidth)/2, _loc.y+(_h-receiptHeight)/2);

		}//endfunction
	}
	
	private void checkOver() {
			if(over) {
				//_p.fill(100,100,100);
				_alpha = 200;
			} else {
				//_p.fill(211,211,211);
				_alpha = 30;
			}//endif
	}
	
	private void checkPress() 
	{
		if(pressed) {
			_itemManager.moveFirst();
			_header.setBackToOriginal();
			_inputbox.setDestLoc(new Vector3D(167,318,0));
			System.out.println("pressed");
			pressed = false;
			up = false;
			_header.up(false);
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
	
	public void setDestLoc(Vector3D v)
	{
		_destLoc = v;
		
	}//endfunction
	
	public void up(Boolean b)
	{
		up = b;
	}//endfunction
	
	public Boolean up()
	{
		return up;
	}//endfunction
	
	
}
