import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * Created by shankar pentyala on 20-06-2017.
 */
public class javaclass implements Serializable{
    public String getlemma(String sb1)
    {

    Properties setprop = new Properties();
    setprop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
    StanfordCoreNLP buildpipeline1 = new StanfordCoreNLP(setprop);
    String sCurrentLine="";
    Annotation docdata = new Annotation(sb1);
    buildpipeline1.annotate(docdata);
    List<CoreMap> docsentence = docdata.get(CoreAnnotations.SentencesAnnotation.class);
     List l1=new ArrayList();
        String lemma="";
    for (CoreMap docsent : docsentence)
    {
        for (CoreLabel word: docsent.get(CoreAnnotations.TokensAnnotation.class))
        {
             lemma += word.get(CoreAnnotations.LemmaAnnotation.class);
              lemma += " ";

        }
    }
   // System.out.println(lemma);
return lemma;
    }
}
