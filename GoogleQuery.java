import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//TODO
/* &scoring=d sorts by date
 * &scoring=r sorts by relevance
 * tie this to interface element 
 * when it's necessary
 */

public class GoogleQuery 
{
	
	private String _reg;
	private String months [] = {
							"",
	                      "January",
	                      "February",
	                      "March",
	                      "April",
	                      "May",
	                      "June",
	                      "July",
	                      "August",
	                      "September",
	                      "October",
	                      "November",
	                      "December"
	};
	                      
	private LinkedList<String> googleUrls;				// A queue of URLs to visit
	
	private ArrayList urlsToVisit;
	private ArrayList titlesToVisit;
	private ArrayList dates;
	private ArrayList descriptions;
	
	private HashMap<String, String> urlsVisited;		// A table of already visited URLs
	
	private int count = 0;
	private int limit = Configuration.limit;
	
	public GoogleQuery()
	{
		googleUrls = new LinkedList<String>();
		urlsToVisit = new ArrayList();
		titlesToVisit = new ArrayList();
		descriptions = new ArrayList();
		
		dates = new ArrayList();
		urlsVisited = new HashMap<String, String>();
	}
	
	public void sendString(String query)
	{
		String urlpath = "http://www.google.com/search?hl=en&q="+ query + "&ie=utf-8&tbm=blg&num="+Configuration.limit+"&output=rss";
		addUrl(urlpath);
	}//endfunction
	
	
	public void addUrl(String urlpath) 
	{
		// Add it to both the LinkedList and the HashMap
		googleUrls.add(urlpath);
		urlsVisited.put(urlpath,urlpath);

	}//endfunction
	
	
	
	
	public void search()
	{
		String urlpath = (String) googleUrls.removeFirst();
		read(urlpath);
		
	}//endfunction
	
	
	private void read(String urlpath)
	{
		System.out.println("urlpath is to read is " + urlpath);
		try
		{
			A2ZUrlReader urlr = new A2ZUrlReader(urlpath);
			String stuff = urlr.getContent();
			Pattern href = Pattern.compile("(http[^\"<\\s]*)", Pattern.CASE_INSENSITIVE);
			// We will ignore URLs ending with certain extensions
			String ignore = ".*(mov|jpg|gif|pdf)$";
			
			
			Matcher m = href.matcher(stuff);
			// While there are URLs
			int counter = 0;
			while (m.find()) {
				// Grab the captured part of the regex (the URLPath itself)
				String newurl = m.group(1);
				// If it hasn't already been visited (or if it matches the ignore pattern)
				
				
				if (counter < Configuration.limit && !newurl.matches(ignore) && !urlsVisited.containsKey(newurl) && !Pattern.matches(".*google.*", newurl) && !Pattern.matches(".*purl.*", newurl)) {
					addURLsToVisit(newurl);
					addTitlesToVisit(newurl); ///added these methods here for now, need to clear the html titles and dates later
					addDates("23.8.2011"); ///added these methods here for now, need to clear the html titles and dates later
					System.out.println("new url " + newurl);
					counter++;
				}//endif
			} //endwhile
			
			Pattern title = Pattern.compile("(<item><title>)(.*[a-z].*)(</title>)", Pattern.CASE_INSENSITIVE);

			String ignores = ".*(mov|jpg|gif|pdf)$";
			
			
			Matcher t = title.matcher(stuff);
			while (t.find()) {
				String newurl = t.group(2);
				//System.out.println("new url is           " + newurl);
				//if (!newurl.matches(ignores) && !urlsVisited.containsKey(newurl) && !Pattern.matches(".*google.*", newurl) && !Pattern.matches(".*purl.*", newurl)) {
					addTitlesToVisit(newurl);
				//}//endif
			}//endwhile
			
			//<dc:date>2008-09-03T17:17:10Z</dc:date>
			Pattern date = Pattern.compile("(<dc:date>)(.*[a-z][0-9].*)(</dc:date>)", Pattern.CASE_INSENSITIVE);
//			System.out.println(stuff);
			
			
			Matcher d = date.matcher(stuff);
			while (d.find()) {
				String newdate = d.group(2);
				//"on July 18, 2008 6:26 AM"
				String monthphrase = newdate.substring(5,7);
				String dayphrase = newdate.substring(8,10);
				String yearphrase= newdate.substring(0,4);
				
				
				
				String hourphrase = dateReturnHours(Integer.parseInt(newdate.substring(11,13)));
				String minphrase = newdate.substring(14,16);
				
				String month = "";
				if(monthphrase.substring(0,1).equals("0")) // use the second part
					 
				{
					 month = months[Integer.parseInt(monthphrase.substring(1,2))];
					
				}//endif
				else
				{
					 month = months[Integer.parseInt(monthphrase.substring(0,2))];
				}//endelse
				//System.out.println(month);
				String format = "on "+month+" "+dayphrase+", " + yearphrase + " at " + hourphrase + ":" + minphrase + " " + _reg;
				//System.out.println(newdate);
					addDates(format);
			}//endwhile
			
			
			//description
			Pattern description = Pattern.compile("(<description>)(.*[a-z].*)(</description>)", Pattern.CASE_INSENSITIVE);
			Matcher desc = description.matcher(stuff);
			while (desc.find()) {
				String newDescr = desc.group(2);

				if (!Pattern.matches(".*Google.*", newDescr) ) {
					addDesc(newDescr);
				}//endif
				
					
					
			}//endwhile
			
			
		}//endtry
		catch (Exception e)
		{
		}//endcatch
	}//endfunction
	
	
	private String dateReturnHours(int num){
		// turns Military time into Standard time
		_reg = "";
		if(num > 12) {
			num = num - 12;
			_reg = "PM";
		}
		else
		{
			_reg = "AM"; 
			num = num;
		}
		return Integer.toString(num);
	}
	
	private void addDesc(String desc)
	{
		descriptions.add(desc);
	}//endfunction
	
	private void addURLsToVisit(String urlpath) 
	{
		// Add it to both the LinkedList and the HashMap
		urlsToVisit.add(urlpath);
		//urlsVisited.put(urlpath,urlpath);
	}//endfunction
	
	private void addTitlesToVisit(String title) 
	{
		System.out.println("add tittles to visit");
		// Add it to both the LinkedList and the HashMap
		titlesToVisit.add(title);
		
		//urlsVisited.put(urlpath,urlpath);

	}//endfunction
	
	private void addDates(String date)
	{
		dates.add(date);
	}//endfunction
	
	public ArrayList URLsToVisit()
	{
		return urlsToVisit;
	}//endfunction
	
	public ArrayList TITLESToVisit()
	{
		return titlesToVisit;
	}//endfunction
	
	public ArrayList datesReturn()
	{
		return dates;
	}//endfunction
	
	
	public ArrayList returnDescriptions()
	{
		return descriptions;
	}//endfunction
	public boolean queueEmpty() 
	{
		return googleUrls.isEmpty();
	}//endfunction
	
}
