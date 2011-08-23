import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;


public class Main extends PApplet 
{
	
	private Dataminer _dataminer;
	private InputBox _inputbox;
	
	private Item _item;
	
	private ItemManager _itemManager;
	
	private PImage b;
	
	private Header _header;
	PFont[] fonts ;
	
	private SearchButton sb;
	
	//TODO
	//scenario
	//user puts keywords
	//then results start to come in the dataminer.
	//now we should pass each result to an arrayList
	//this arrayList is being checked on every draw 
	//and if we see an element in the arrayList, we go 
	//ahead and create a new Item out of this
	//and remove the item from the arrayList.
	//ItemManager should take care of adding/removing/
	//all kinds of interactions(rollOvers/rollOuts/presses)
	
//	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "Main" });
	  }
	
	
	
	private LoadBar loadbar;
	
	
	public void setup()
	{
		frameRate(60);
		size(1024,768);
		b = loadImage("interface3a.jpg");
		
		fonts = new PFont[] {
				loadFont("BodoniAntiquaT-Bold-22.vlw"),
				loadFont("DIN-BlackAlternate-14.vlw"),
				loadFont("Monaco-10.vlw"),
				loadFont("RockwellStd-Bold-18.vlw"),
				loadFont("BodoniAntiquaT-Bold-16.vlw"),
				loadFont("Monaco-12.vlw"),
				loadFont("ProFont-30.vlw"),
		};
		
		
		loadbar = new LoadBar(this,fonts);
		
		_inputbox = new InputBox(this);
		_itemManager = new ItemManager(this,fonts,loadbar);
		
		_header = new Header(this, fonts, loadbar);
		
	}//endsetup
	
	public void draw()
	{
		background(0);
		image(b, 0, 0);
		
		
		//pushMatrix();
		//translate(157, 158); 
		_inputbox.update();
		_inputbox.draw();
		_header.update();
		_header.draw();
		loadbar.draw();
		
		
		//println(_itemManager.getItems().size());
		if(_itemManager.getItems().size() > 0)
		{
			_itemManager.drawItems();
		}
		
		if(sb != null)
		{
			sb.update();
			sb.draw();
		}//endfunction
	}//enddraw

	
	public void mouseMoved() 
	{
		if(_itemManager.getItems().size() > 0)
		{
			_itemManager.mouseMoved();
		}//endif
		if(sb !=null) sb.mouseCheck();
	}//endfunction
	
	public void mouseReleased()
	{
		if(_itemManager.getItems().size() > 0)
		{
			_itemManager.mousePressed();
		}//endif
		if(sb != null) sb.mousePressed();
		
	}
	
	public void keyPressed() {
		  if(key != CODED) {
		    if(key == ENTER) {
		      //t1.tag = in.keyboardInput;
		      // keyword pointer to the text that is going to be searched.
		    	_inputbox.keyboardInput = _inputbox.keyboardInput.replaceAll(" ", "+");
		    	_dataminer = new Dataminer(_itemManager, _inputbox.keyboardInput);
		    	_dataminer.start();
		    	_itemManager.dataMiner(_dataminer);
		    	_header.setDestLoc(new Vector3D(157,0,0));
		    	_header.setNewHeight(_inputbox.keyboardInput);
		    	_inputbox.setDestLoc(new Vector3D(157,-100,0));
		        _inputbox.keyboardInput = "";
		        sb = new SearchButton(this,fonts, _itemManager, _header, _inputbox, _dataminer);
		        sb.setDestLoc(new Vector3D(745,25,0));
		        sb.up(true);
		    }  
		    else if (key == BACKSPACE && _inputbox.keyboardInput.length() > 0) {
		    	_inputbox.keyboardInput = _inputbox.keyboardInput.substring(0,_inputbox.keyboardInput.length()-1); 
		    }
		    else { 
		    	_inputbox.keyboardInput = _inputbox.keyboardInput + key;
		      // clear the pointer here
		    }
		  }
		}
	
//	public void mousePressed() {
//		  inputbox.initialClick = true;
//		}

	

}
