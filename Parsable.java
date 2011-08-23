import java.io.IOException;

import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.xerces.internal.util.URI;

import simpleML.*;


public class Parsable {

	private float count;
	
	private String _content;
	
	private String _urlpath;
	
	private String _domain;
	
	private String _reactions;
	private String _authority;
	private float _flesch;
	private String _energy;
	private String _feedback;
	
	public Parsable()
	{
		
	}
	
	public void sendUrl(String urlpath) throws IOException
	{
		
		
		_urlpath = urlpath;
		
		URI uri = new URI(_urlpath);
		_domain = uri.getHost();
	}//endfunction
	
	
	
	public String blogReactions()
	{
		A2ZUrlReader urlr = null;
		try {
			urlr = new A2ZUrlReader("http://www.technorati.com/search/"+_urlpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String stuff = urlr.getContent();
		
		Pattern description = Pattern.compile("(<h1>)(.*[0-9].*)(<span class=\"subject\">)", Pattern.CASE_INSENSITIVE);
		Matcher desc = description.matcher(stuff);
		while (desc.find()) {
			String newDescr = desc.group(2);
			String words[] = newDescr.split(" ");
			//System.out.println(words[0]);
			words[0] = words[0].replaceAll(",", "");
			//System.out.println(words[0]);
			_reactions = words[0];
			
		}//endwhile
		return _reactions;
	}//endfunction
	
	
	public String authority()
	{
		A2ZUrlReader urlr = null;
		try {
			urlr = new A2ZUrlReader("http://www.technorati.com/search/"+_domain);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String stuff = urlr.getContent();
		
		
		Pattern authority = Pattern.compile("(title=\"View blog reactions\">)(.*[0-9].*)(</a>)", Pattern.CASE_INSENSITIVE);
		Matcher auth = authority.matcher(stuff);
		int counter = 0;
		while (auth.find() && counter <1) {
			String newAuth = auth.group(2);
			//System.out.println(newAuth);
			counter++;
			String authGroup[] = newAuth.split(" ");
			//System.out.println(authGroup[1]);
			
			//title="View blog reactions">Authority: 11,992</a>
			authGroup[1] = authGroup[1].replaceAll(",", "");
			
			_authority = authGroup[1];
			double temp= Integer.parseInt(_authority);
			temp = temp*0.01;
			
			_authority = Double.toString(temp);
			
			//	addDesc(newDescr);
		}//endwhile
		return _authority;
		
	}//endfunction
	
	
	
	public String energy()
	{
		System.out.println(blogReactions());
		 String s = blogReactions();
		
		if(s ==  null) 
		{
			s = "1";
		}//endif
		double blog = Double.parseDouble(s);
		if(blog == 0) blog = 1;
		double _energy = 8.88*0.977*24*blog*0.01;
		
		return Double.toString(_energy);
		
	}//endfunction
	
	public String fleschIndex()
	{
			int syllables = 0;
		    int sentences = 0;
		    int words     = 0;
		    A2ZUrlReader urlr = null;
			try {
				urlr = new A2ZUrlReader(_urlpath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_content = urlr.getContent();
			
		    String delimiters = ".,':;?{}[]=-+_!@#$%^&*() ";
		    java.util.StringTokenizer tokenizer = new StringTokenizer(_content,delimiters);
		    //go through all words
		    while (tokenizer.hasMoreTokens())
		    {
		      String word = tokenizer.nextToken();
		      syllables += countSyllables(word);
		      words++;
		    }
		    //look for sentence delimiters
		    String sentenceDelim = ".:;?!";
		    StringTokenizer sentenceTokenizer = new StringTokenizer(_content,sentenceDelim);
		    sentences = sentenceTokenizer.countTokens();
		    
		    //calculate flesch index
		    final float f1 = (float) 206.835;
		    final float f2 = (float) 84.6;
		    final float f3 = (float) 1.015;
		    float r1 = (float) syllables / (float) words;
		    float r2 = (float) words / (float) sentences;
		    float flesch = f1 - (f2*r1) - (f3*r2);

		    //Write Report
		    String report = "";
		    
		    report += "Total Syllables: " + syllables + "\n";
		    report += "Total Words    : " + words + "\n";
		    report += "Total Sentences: " + sentences + "\n";
		    report += "Flesch Index   : " + flesch + "\n";
		    _flesch = flesch;
		    _flesch = 100%_flesch;
		    return Float.toString(_flesch);
		    
	}
	
	// A method to count the number of syllables in a word
	// Pretty basic, just based off of the number of vowels
	// This could be improved
	public static int countSyllables(String word) {
	    int      syl    = 0;
	    boolean  vowel  = false;
	    int      length = word.length();

	    //check each word for vowels (don't count more than one vowel in a row)
	    for(int i=0; i<length; i++) {
	      if        (isVowel(word.charAt(i)) && (vowel==false)) {
	        vowel = true;
	        syl++;
	      } else if (isVowel(word.charAt(i)) && (vowel==true)) {
	        vowel = true;
	      } else {
	        vowel = false;
	      }
	    }

	    char tempChar = word.charAt(word.length()-1);
	    //check for 'e' at the end, as long as not a word w/ one syllable
	    if (((tempChar == 'e') || (tempChar == 'E')) && (syl != 1)) {
	      syl--;
	    }
	    return syl;
	}
	
	public String feedback()
	{
		//System.out.println(blogReactions());
		String s = blogReactions();
		if(s == null) s = "0";
		double comment = Double.parseDouble(s)*.2 + 3;
		//System.out.println(comment);
		return Double.toString(comment);
	}//endfunction

	//check if a char is a vowel (count y)
	public static boolean isVowel(char c) {
	    if      ((c == 'a') || (c == 'A')) { return true;  }
	    else if ((c == 'e') || (c == 'E')) { return true;  }
	    else if ((c == 'i') || (c == 'I')) { return true;  }
	    else if ((c == 'o') || (c == 'O')) { return true;  }
	    else if ((c == 'u') || (c == 'U')) { return true;  }
	    else if ((c == 'y') || (c == 'Y')) { return true;  }
	    else                               { return false; }
	  }


	public String count(String s)
	{
		// double d = Double.valueOf(s.trim()).doubleValue();
       System.out.println("s: " + s);
       if(s == null) s = "0";
       if(Double.valueOf(_flesch).isNaN())  s = "0";
		
		double value = Double.valueOf(s);
		//System.out.println( "\n\n\n\nValue :- " + value);
		//dividing it by 3
		value = value * .003 ;
		DecimalFormat valueFormat = new DecimalFormat("####.##") ;
		//System.out.println( "Divided Value before formating "+ value );
		value=Math.floor(value*100.0);
		//System.out.println( "Third ---> New value after format := " + valueFormat.format(value/100.0) );
		
		//count = count *.01f;
		return valueFormat.format(value/100.0);
	}//endfunction
	
	
}
