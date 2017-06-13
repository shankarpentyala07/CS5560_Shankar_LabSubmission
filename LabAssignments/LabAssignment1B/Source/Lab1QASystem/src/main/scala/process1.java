/**
 * Created by shankar pentyala on 8-06-2017.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class process1 {

    public static void main(String args[])
    {
        //Instantating coreNLP object
        Properties setprop = new Properties();
        setprop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP buildpipeline1 = new StanfordCoreNLP(setprop);
        String sCurrentLine="";
        StringBuilder sb1 = new StringBuilder();
        HashMap<String,HashSet<String>> hm = new HashMap<String,HashSet<String>>();
       //Getting the Dataset
        try {
            BufferedReader br1 = new BufferedReader(new FileReader("C:/KDM/bbcsport-fulltext/bbcsport/cricket/001.txt"));
            try {
                while ((sCurrentLine = br1.readLine()) != null) {
                    sb1.append(sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


       //Core NLP processing
      Annotation docdata = new Annotation(sb1.toString());
       buildpipeline1.annotate(docdata);
        List<CoreMap> docsentence = docdata.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap docsent : docsentence)
        {
            for (CoreLabel word: docsent.get(CoreAnnotations.TokensAnnotation.class))
            {
                String ne = word.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                ne = ne.toLowerCase();
                if (!ne.equals("o"))
                {
                    if (!hm.containsKey(ne))
                    {
                        HashSet<String> hs = new HashSet<>();
                        hs.add(word.originalText());
                        hm.put(ne,hs);
                    }
                    else
                    {
                        hm.get(ne).add(word.originalText());
                    }
                }
            }
        }


        //Processed data is stored in the hashmap and used for Question & Answering.
         Scanner inp = new Scanner(System.in);
         String s1="";

            //Start asking and get answers
       while (0 == 0)
        {
             System.out.println("Enter Q to ask questions,Enter BYE to quit");
             s1 = inp.nextLine();
            switch(s1)
            {
                case "Q" :
                    {
                        System.out.println("Please go ahead and enter your question");
                         s1 = inp.nextLine();
                        StringTokenizer s2 = new StringTokenizer(s1);
                        while(s2.hasMoreTokens())
                        {
                            s1=s2.nextToken();
                         if (hm.containsKey(s1))
                         {

                             System.out.println(hm.get(s1));
                         }

                         }
                         break;
                    }
                case "BYE" :
                {
                    System.exit(0);
                }
                default:
                {
                    System.out.println("Please make a valid choice");
                }
            }


        }

    }
}
