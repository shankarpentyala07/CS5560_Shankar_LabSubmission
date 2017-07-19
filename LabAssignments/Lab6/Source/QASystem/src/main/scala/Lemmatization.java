import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

/**
 * Created by shankar pentyala on 24    -06-2017.
 */
public class Lemmatization {
    public static String returnLemma(String sentence) {

        Document doc = new Document(sentence);
        String lemma="";
        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
            List<String> l=sent.lemmas();
            for (int i = 0; i < l.size() ; i++) {
                lemma+= l.get(i) +" ";
            }
        }
        return lemma;
    }
}
