import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

/**
 * Created by shankar pentyala on 04-07-2017.
 */
public class openIE {


    public static String oiefun(String inp1) {
        String triples = "";
        Document doc1 = new Document(inp1);
        for (Sentence sen1 : doc1.sentences()) {
            triples += sen1.openie();
           triples = triples.replace("),",":");
           triples = triples.replaceAll("[ \\( \\] \\[ \\) ]"," ");
          // triples = triples.replace("[","")

            triples += ":";
        }

        return triples;
    }
}