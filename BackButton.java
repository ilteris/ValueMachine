import processing.core.PApplet;
import processing.core.PConstants;


public class BackButton {


	private PApplet _p;
	Vector3D _loc;
	
	
	private float _w = 75;
	private float _h = 75;
	

	private boolean	inbounds;
	private boolean	over;
	private boolean	pressed;
	
	private Boolean _selected;
	
	private float _alpha;
	
	private Boolean _visible = false;
	
	private ItemManager _itemManager;
	
	public BackButton(PApplet p_, ItemManager itemManager_)
	{
		_p = p_;
		_itemManager = itemManager_;
		_alpha = 100;
		_loc = new Vector3D(150,420,0);
		
	}
	
	public void draw()
	{
		if(_visible)
		{
			checkOver();
			checkPress();
			_p.noFill();
			_p.noStroke();
			//_p.stroke(200,110,115);
			_p.fill(255, 255, 255,_alpha);
			
			
			_p.beginShape(PApplet.TRIANGLES);
			_p.vertex(_loc.x-50, _loc.y);
			_p.vertex(_loc.x, _loc.y-50);
			_p.vertex(_loc.x, _loc.y+50);
			_p.endShape();
			
			_p.rectMode(PConstants.CORNER);
			//_p.rect(_loc.x,_loc.y, _w, _h);

		}
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
			_itemManager.moveBack();
			System.out.println("pressed");
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
		Vector3D newLoc 	= new Vector3D(_loc.x-50 + 50/2, _loc.y - 50 + 100/2);
		
		Vector3D diff = Vector3D.sub(mouseCoord, newLoc);
		if(Math.abs(diff.x) < 100/2 && Math.abs(diff.y) < 100/2) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean visible()
	{
		return _visible;
	}//endfunction
	
	public void visible(Boolean b)
	{
		_visible = b;
	}//endfunction
	
}
