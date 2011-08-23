/* Daniel Shiffman               */
/* Programming from A to Z       */
/* Spring 2006                   */
/* http://www.shiffman.net       */
/* daniel.shiffman@nyu.edu       */

/* Class to read an input URL    */
/* and return a String           */


import java.net.*;
import java.sql.ResultSet;
import java.io.*;

public class A2ZUrlReader
{
	// Stings to hold url as well as url's source content
	private String urlPath;
	private String content;
	private String _tempContent;
	
	private Pars parser;
	
	public A2ZUrlReader(String name) throws IOException {
		urlPath = name;
		readContent();
		
	}
	
	
	public void readContent() throws IOException {
		// Create an empty StringBuffer
		StringBuffer stuff = new StringBuffer();
		try {
			// Call the openStream method (from below)
			InputStream stream = createInputStream(urlPath);
			//System.out.println(stream);
			if(stream != null) //make sure stream is not null
			{
			//Create a BufferedReader from the InputStream
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		
			// Read the stream line by line and append to StringBuffer
			String line;
			while (( line = reader.readLine()) != null) {
				stuff.append(line + "\n");
			}
			// Close the reader 
			reader.close();
			}//endif
		} catch (IOException e) {
			System.out.println("Ooops!  Something went wrong! " + e);
		}
		// Convert the StringBuffer to a String
		content = stuff.toString();
		content = content.replaceAll("&lt;", "");
		content = content.replaceAll("/b&gt;", "");
		content = content.replaceAll("b&gt;", "");
		content = content.replaceAll("#39;", "'");
		content = content.replaceAll("quot;", "\"");
		
		
		content = content.replaceAll("&amp;", "");
		
	//	ResultSet rset = sta.executeQuery();
		
		//cleanUp(_tempContent, "&lt;", false);
		
		
	}
	
	
//	public void cleanUp( String s, String sToMatch, boolean isToKeep ) {
//		 final int size = s.length();
//		 StringBuffer buf = new StringBuffer( size );
//		 if ( ! isToKeep ) {
//		   for ( int i = 0; i < size; i++ ){
//		     if ( sToMatch.indexOf(s.charAt(i) ) == -1 ){
//		       buf.append( s.charAt(i) );
//		     }
//		   }
//		 }
//		 else {
//		   for ( int i = 0; i < size; i++ ){
//		     if ( sToMatch.indexOf(s.charAt(i) ) != -1 ){
//		       buf.append( s.charAt(i) );
//		     }
//		   }
//		 }
//		 content = buf.toString();
//		} 
			
	
	
		
	// A method to create an InputStream from a URL
	public static InputStream createInputStream(String urlpath) {
		InputStream stream = null;
		try {
			URL url = new URL(urlpath);
			URLConnection myConn = (HttpURLConnection)url.openConnection();
			myConn.setRequestProperty("User-agent","Mozilla/4.0");

			//stream = url.openStream();
			return myConn.getInputStream();
			
		} catch (MalformedURLException e) {
			System.out.println("Something's wrong with the URL:  "+ urlpath + " " + e);
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("there's a problem downloading from:  "+ urlpath + " " + e);
			 e.printStackTrace();
		}
		return stream;
	}
	
	public String getContent() {
		return content;
	}
}



