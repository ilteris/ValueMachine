import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Dataminer extends Thread {
	private GoogleQuery			google;
	private ArrayList<Parsable>	parsables; //this is actual arrayList to be sent to drawing.
	
	private ItemManager _itemManager;
	
	 private boolean running;           // Is the thread running?  Yes or no?
	 private boolean available;         // Is new news available?
  
	 private String query;
	//thread vars
	public Dataminer(ItemManager itemManager_,String s_)
	{
	    running = false;
	    available = false;
	    parsables = new ArrayList();
		_itemManager = itemManager_;
		query = s_;
	}
	
	// Overriding "start()"
	public void start ()
	{
		// Set running equal to true
		running = true;
		// Print messages
		// Do whatever start does in Thread, don't forget this!
		super.start();
	}//endfunction
	  
	  
	// We must implement run, this gets triggered by start()
	public void run ()
	{
		while (running){
			startMining();
			// Ok, let's wait for however long we should wait
			try {
				quit();//quit here.
			} 
			catch (Exception e) {
			}
		}
	}
	  
	// Our method that quits the thread
	public void quit()
	{
		System.out.println("Quitting."); 
		running = false;  // Setting running to false ends the loop in run()
		// We used to need to call super.stop()
		// We don't any more since it is deprecated, see: http://java.sun.com/j2se/1.5.0/docs/guide/misc/threadPrimitiveDeprecation.html
		// super.stop();
		// Instead, we use interrupt, in case the thread is waiting. . .
		super.interrupt();
	}
	
	public boolean available() {
		return available;
	}
	
	
	private synchronized void startMining()
	{
		google = new GoogleQuery();
		System.out.println("Searching for " + query);
		google.sendString(query);
		google.search();
		
		for (int i = 0; i < google.URLsToVisit().size(); i++) {
			Parsable p = new Parsable();
			try {
				p.sendUrl((String)google.URLsToVisit().get(i));
				//p.fleschIndex();
				//p.blogReactions();
				//p.authority();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.println("google is " +  (String)google.TITLESToVisit().get(i));
			String title = (String)google.TITLESToVisit().get(i);	
			String date = (String)google.datesReturn().get(i);
			String url = (String)google.URLsToVisit().get(i);
			String description = (String)google.returnDescriptions().get(i);
			
			String elements[] = {title,date,url,description};
			
//			double total = Double.parseDouble(p.count(p.blogReactions())) + Double.parseDouble(p.count(p.fleschIndex())) + Double.parseDouble(p.count(p.feedback())) + Double.parseDouble(p.count(p.authority())) - Double.parseDouble(p.count(p.energy()));
			
			String values[] = {
					p.count(p.blogReactions()),
					p.count(p.fleschIndex()),
					p.count(p.feedback()),
					p.count(p.authority()),
					p.count(p.energy()),
			};
			
			
			DecimalFormat valueFormat = new DecimalFormat("####.##") ;
			//System.out.println( "Divided Value before formating "+ value );
			
			
			_itemManager.addItem(new Vector3D(157,1058,0),new Vector3D(157,158,0),values,elements);
			parsables.add(p);
			available = true;
			notifyAll();  // let's notify everyone that the headlines have been updated
		}//endfor
	}//endfunction
	
	
	public synchronized ArrayList<Parsable> getParsables()
	{
	    // We should put a while (!available) loop here
	    // but since we are explicitly only calling this function if available is true, we're ok
	    available = false;
	    notifyAll(); // let's notify everyone that available has changed
	    return parsables;     
	}//endfunction
	
	public Boolean running()
	{
		return running;
	}//endfunction
	
	
}
