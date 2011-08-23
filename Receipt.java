import java.io.PrintWriter;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;


public class Receipt {

	

	private PApplet _p;
	private PFont[] fonts;
	
	
	private float _w = 725;
	private float _h = 75;
	
	private float _wrect = 123;
	private float _hrect = 37;
	
	
	
	
	Vector3D _loc;
	Vector3D _vel, _acc;
	
	Vector3D _destLoc;
	
	private boolean	over;
	private boolean	pressed;
	private ItemManager _itemManager;
	
	private Boolean _selected = false;
	private int _alpha;
	
	private int _textAlpha;
	
	private String[] _elements;
	private String[] _values;
	private String _total;
	
	public Receipt(PApplet p_, ItemManager itemManager_, PFont[] fonts_, Vector3D loc_, String[] values_, String[] elements_, String total_)
	{
		_p = p_;
		fonts = fonts_;
		_itemManager = itemManager_;
		_loc = loc_;
		_alpha = 50;
		_textAlpha  = 150;
		_elements = elements_;
		_values = values_;
		_total = total_;
		
		createTxt();
	}
	
	
	private void createTxt()
	{
		 PrintWriter output = _p.createWriter("c:\\VM\\receipt.txt"); // Creating the text file


		  // Design of the Receipt
		  output.println("");
		  output.println("");
		  output.println("------------------------------------");
		  output.println(_elements[1]); 
		  output.println(_elements[0]);
		  output.println("");
		  output.println(_elements[2]);
		  output.println("");
		  output.println("");
		  output.println("Readability ............. $ " + "+" + _values[1]);
		  output.println("Feedback ................ $ " + "+" + _values[2]);
		  output.println("Trackback ............... $ " + "+" + _values[0]);
		  output.println("Popularity .............. $ " + "+" + _values[3]);
		  output.println("Energy .................. $ " + "-" + _values[4]);
		  output.println("___________________________________");
		  output.println("TOTAL                     $ " + _total);
		  output.println("");
		  output.println("");
		  output.println(" ##################################");
		  output.println("             THANKS FOR");
		  output.println("   PURCHASING THIS INFORMATION");
		  output.println("####################################");
		  output.println("        ID# LNZV01"+_p.year()+_p.month()+_p.day()+_p.hour()+_p.minute());
		  output.println("");
		  output.println("This receipt is printed by VM engine v1 on "+_p.day()+"/"+_p.month()+"/"+_p.year()+" at "+_p.hour()+":"+_p.minute()+":"+_p.second()+" in Linz.");
		  output.println("-----------------------------------");
		  output.println("  www.casualdata.com/valuemachine");
		  output.println("-----------------------------------");
		  output.println("");
		  output.println("");
		  output.println("");


		  // Finalizing the file
		  output.flush(); // Writes the remaining data to the file
		  output.close(); // Finishes the file
	}//endfunction
	public void drawReceipt()
	{
		checkOver();
		checkPress();
		
		//print button
 		_p.noFill();
		_p.noStroke();
		//_p.stroke(200,110,115);
		_p.fill(255, 255, 255,_alpha);
		_p.rectMode(PConstants.CORNER);
		_p.rect(_loc.x + _w - 139,_loc.y + (_h-37)/2, 123, 37);
		
		//print receipt
		
		_p.fill(0, 0, 0,_textAlpha);
		_p.textFont(fonts[4], 16); 
		_p.textAlign(PConstants.LEFT,PConstants.TOP);
		String receipt = "Print Receipt";
		float receiptWidth = _p.textWidth(receipt);
		float receiptHeight = _p.textAscent()+_p.textDescent();
		//_p.println("th: " + th);
	 	_p.text(receipt, _loc.x + _w - 139 + (123 - receiptWidth)/2, _loc.y + (_h-receiptHeight)/2);
	 	
	 	
	 	
	}
	
	
	private void checkOver() {
		if(!_selected)
		{
			if(over) {
				_alpha = 80;
				_textAlpha = 255;
				
			} else {
				_alpha = 50;
				_textAlpha = 150;
			}//endelse
		}//endif
	}
	
	
	private void checkPress() 
	{
		if(pressed) {
			String params = "copy c:\\VM\\receipt.txt c:\\prnspl";
			_p.open(params);
			//print it from the printer
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
		Vector3D newLoc 	= new Vector3D(_loc.x + _w - 139 + _wrect/2,_loc.y + (_h-37)/2 + _hrect/2);
		
		Vector3D diff = Vector3D.sub(mouseCoord, newLoc);
		if(Math.abs(diff.x) < _wrect/2 && Math.abs(diff.y) < _hrect/2) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
