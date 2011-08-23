import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;


public class ItemManager {
	
	private PApplet _p;
	private PFont[] _fonts;
	
	private float currentItemY;
	private ArrayList<Item>	_items; //this is actual arrayList to be sent to drawing.
	
	private BackButton b;
	
	private Dataminer _dataminer;
	
	
	private Boolean _removable = false;
	//TODO
	//We should have an arrayList here
	//and we should be able to add and remove 
	//from the list runtime.
	//This will give us the chance of
	//animating them in/out of the screen.
	
	//almost like a particle system
	
	private LoadBar _loadbar;
	private int _loader = 1;
	
	public ItemManager(PApplet p_, PFont[] fonts_,LoadBar loadbar_)
	{
		_p = p_;
		_fonts = fonts_;
		_loadbar = loadbar_;
		_items = new ArrayList<Item>() ;
		b = new BackButton(_p, this);
		
		
	}
	
	public void drawItems()
	{
		checkRemove();
		for (int i = 0; i < _items.size() ; i++) 
		{
			 Item item = (Item) _items.get(i) ;
			 item.update();
			 item.drawItem(); 
		}//endfor
		
		b.draw();
		
		
		
		
	}//endfunction
	
	public void moveItems()
	{
		//set the back button visible on
		b.visible(true);
		
		// quit the thread.
		for (int i = 0; i < _items.size() ; i++) 
		{
			 Item item = (Item) _items.get(i) ;
			 if(!item.selected())
			 {
				 item.setX(-725.0f);
			 }
			 else
			 {
				 currentItemY = item.getY();
				 item.setY(157.0f);
			 }
		}//endfor
	}//endfunction
	
	
	public void moveBack()
	{
		//set the back button visible off
		b.visible(false);
		for (int i = 0; i < _items.size() ; i++) 
		{
			
			 Item item = (Item) _items.get(i);
			 if(!item.selected())
			 {
				 System.out.println("foo");
				 item.setX(158.0f);
			 }
			 else
			 {
				 item.selected(false);
				 item.setY(currentItemY);
			 }
		}//endfor
	}//endfunction
	
	public void moveFirst()
	{
		//set the back button visible on
		b.visible(false);
		for (int i = 0; i < _items.size() ; i++) 
		{
			
			 Item item = (Item) _items.get(i);
				 item.setX(1000.0f);
		}//endfor
		
		_removable = true;
	}//endfunction
	
	
	
	
	public void checkRemove()
	{
		if(_removable)
		{
			for (int i = _items.size()-1; i >= 0; i--) {
			        _items.remove(i);
			 }//endfor
			_removable = false;
		}
	}//endfunction
	
	
	public void addItem(Vector3D loc, Vector3D destLoc, String[] values,String[] elements)
	{
		//_p.println(_items.size());
		destLoc.setY(157+ 76*_items.size());
		
		if(_items.size()%2==0) // even 
		{
			
			Item item = new Item(_p, this, _fonts, loc, destLoc,40,  values,elements);
			_items.add(item);
			_loadbar.setDestLocY(destLoc.y+76);
		}//endif
		else //odd
		{
			Item item = new Item(_p, this, _fonts, loc, destLoc,20,  values,elements);
			_items.add(item);
			_loadbar.setDestLocY(destLoc.y+76);
		}//end else
	}//endfunction

	public void mouseMoved()
	{
		
		for (int i = 0; i < _items.size() ; i++) 
		{
			 Item item = (Item) _items.get(i) ;
			 item.mouseCheck(); 
		}//endfor
		if(b.visible())
		{
			b.mouseCheck();
		}//endif
	}//endfunction
	
	public void mousePressed()
	{
		if(!_dataminer.running())
		{
			for (int i = 0; i < _items.size() ; i++) 
			{
				 Item item = (Item) _items.get(i) ;
				 item.mousePressed();
				 item.receipt().mousePressed();
			}//endfor
			if(b.visible())
			{
				b.mousePressed();
			}//endif
		}//endif
	}//endfunction
	
	
	public void dataMiner(Dataminer d)
	{
		_dataminer = d;
	}//endfunction
	
	public ArrayList<Item> getItems()
	{
		return _items;
	}//endfunction
	
}
