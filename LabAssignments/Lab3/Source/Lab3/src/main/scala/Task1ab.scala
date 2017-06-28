import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}


import scala.collection.immutable.HashMap

/**
  * Created by shankar pentyala on 27-06-2017.
  */
object Task1ab {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "F:\\winutils")

    val sparkConf = new SparkConf().setAppName("Task1ab").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    //Reading the Text File
    val documents = sc.textFile("C:\\KDM\\Tutorial 3 Source Code\\Spark_TFIDF_W2V\\data\\Article.txt")

    //Getting the Lemmatised form of the words in TextFile
    val documentseq = documents.map(f => {
       val lemmatised = Lemma.returnLemma(f)
       val splitString = f.split(" ")
      //val splitString = lemmatised.split(" ")
       splitString.toSeq


    })

    documentseq.foreach(println)
    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF()

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(documentseq)
    tf.cache()


    val idf = new IDF().fit(tf)

    //Creating Inverse Document Frequency
    val tfidf = idf.transform(tf)

    val tfidfvalues = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val values = ff(2).replace("]", "").replace(")", "").split(",")
      values
    })

    val tfidfindex = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val indices = ff(1).replace("]", "").replace(")", "").split(",")
      indices
    })

    tfidf.foreach(f => println(f))

    val tfidfData = tfidfindex.zip(tfidfvalues)

    var hm = new HashMap[String, Double]

    tfidfData.collect().foreach(f => {
      hm += f._1 -> f._2.toDouble
    })

    val mapp = sc.broadcast(hm)

    val documentData = documentseq.flatMap(_.toList)
    val dd = documentData.map(f => {
      val i = hashingTF.indexOf(f)
      val h = mapp.value
      (f, h(i.toString))
    })

    val dd1 = dd.distinct().sortBy(_._2, false)
    dd1.take(20).foreach(f => {
      println(f)
    })

  }

}
