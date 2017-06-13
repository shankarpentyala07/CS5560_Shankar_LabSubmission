package com.umkc.kdm.lab1b;

//Importing the  libraries
import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by shankar pentyala on 10-06-2017.
 */
public class nlptasks {
    public static void main(String args[]) {

        //build pipeline for nlp
        Properties setprop = new Properties();
        setprop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref,sentiment");
        StanfordCoreNLP buildpipeline1 = new StanfordCoreNLP(setprop);
        String sCurrentLine = "";
        StringBuilder sb1 = new StringBuilder();
       //Read the input data set
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

        //processing the dataset for pos and ner
        String sentianalyzinp = sb1.toString();
        Annotation docdata = new Annotation(sb1.toString());
        buildpipeline1.annotate(docdata);
        List<CoreMap> docsentence = docdata.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap docsent : docsentence) {
            for (CoreLabel word : docsent.get(CoreAnnotations.TokensAnnotation.class)) {

               //pos technique
                System.out.println("POS for " +word.originalText() +" is " +word.get(CoreAnnotations.PartOfSpeechAnnotation.class));

              //Ner procedure

                String ne = word.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                ne = ne.toLowerCase();
                if (!ne.equals("o"))
                     System.out.println("NER is " + ne +" for " +word.originalText());


                }
            }
              //extract coref
            Map<Integer,CorefChain> coref = docdata.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            System.out.println(coref.values().toString());
              //knowing the sentiment logic
        int longest = 0;
        int mainSentiment = 0;
        Annotation annotate = buildpipeline1.process(sentianalyzinp);
        for (CoreMap sentence : annotate.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            String partText = sentence.toString();
            if (partText.length() > longest) {
                mainSentiment = sentiment;
                longest = partText.length();
            }
        }
        String s3="";

        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0)
            s3="-1";
        else
        {
        switch (mainSentiment) {
            case 0: {
                s3 = "very negative";
                break;
            }
            case 1:
                {
                s3 = "negative";
                break;
                }
            case 2:
                {
                s3 = "neutral";
                break;
                }
            case 3:
            {
                s3 = "positive";
                break;
            }
            case 4:
            {
                s3 = "very positive";
                break;
            }
            default:
                s3="";
        }}

        System.out.println("Sentiment of the input data Set is :"+s3);

    }
    }

