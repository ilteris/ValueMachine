import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class Pars {

//	public Pars(String urlpath)
//	{
//		
//		Parser parser = null;
//		try {
//			parser = new Parser (urlpath);
//		} catch (ParserException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//			// TODO Auto-generated catch block
//		NodeList list = new NodeList ();
//		NodeFilter filter =new TagNameFilter ("title");
//		try {
//			for (NodeIterator e = parser.elements (); e.hasMoreNodes (); )
//			    e.nextNode ().collectInto (list, filter);
//			//	System.out.println(list.toString());
//		} catch (ParserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
