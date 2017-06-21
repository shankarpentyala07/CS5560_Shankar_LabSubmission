import java.util
import scala.io.StdIn.{readLine,readInt}
import java.util.{List, Properties}
import edu.stanford.nlp.ling.CoreAnnotations._
import scala.collection.mutable.ArrayBuffer
//import edu.stanford.nlp.ling.CoreAnnotations.{SentencesAnnotation, TokensAnnotation}
import edu.stanford.nlp.pipeline._
import scala.collection.JavaConversions._
import edu.stanford.nlp.util.CoreMap
import org.apache.spark.{SparkConf, SparkContext}
object SparkWordCount {

  def plainTextToLemmas(text: String): Seq[String] = {
    val props = new Properties()
    props.put("annotators", "tokenize, ssplit, pos, lemma,ner, parse, dcoref")
    val pipeline = new StanfordCoreNLP(props)
    val doc = new Annotation(text)
    pipeline.annotate(doc)
    val ners = new ArrayBuffer[String]()
    val sentences = doc.get(classOf[SentencesAnnotation])
    for (sentence <- sentences; token <- sentence.get(classOf[TokensAnnotation])) {
      val ner = token.ner();
      if (ner != "O" ) {
        ners += (ner +" " +token.originalText());
      }
    }
    ners
  }
  def main(args: Array[String]) {
      //spark props
    System.setProperty("hadoop.home.dir","F:\\winutils");
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sc=new SparkContext(sparkConf)
    val input = sc.textFile("C:/KDM/bbcsport-fulltext/bbcsport/cricket/001.txt")
    val lemmatized = input.flatMap(plainTextToLemmas(_))
    val personrdd =lemmatized.filter(line=>line.contains("PERSON"))
    val locrdd =lemmatized.filter(line=>line.contains("LOCATION"))
    val organizationrdd =lemmatized.filter(line=>line.contains("ORGANIZATION"))
   // personrdd.foreach(println)
    println("Welcome to question answering system")
    while(0 == 0)
    {
      println("Please enter your question,Enter Bye to Quit")
      val ques = readLine()
      if(ques.equalsIgnoreCase("Bye"))
        System.exit(0);
      else if (ques.contains("PERSON"))
        {

          personrdd.foreach(println)
        }

      else if (ques.contains("LOCATION"))
      {

        locrdd.foreach(println)
      }
      else if (ques.contains("ORGANIZATION"))
      {

        organizationrdd.foreach(println)
      }

    }

  }

}
