import java.io.PrintStream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.RandomForest

/**
  * Created by shankar pentyala on 11-07-2017.
  */
object Lab_RF_TFIDF {
  def main(args:Array[String]) :Unit =
  {
    System.setProperty("hadoop.home.dir", "F:\\winutils")
    val conf = new SparkConf().setAppName("RF").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val topic_output = new PrintStream("C:\\Users\\shankar pentyala\\IdeaProjects\\YahooDataSetML\\Data\\RF_Results_TFIDF.txt")
    val QFV = new PrintStream("C:\\Users\\shankar pentyala\\IdeaProjects\\YahooDataSetML\\Data\\Q_FV_RFIDF.txt")
    val AFV = new PrintStream("C:\\Users\\shankar pentyala\\IdeaProjects\\YahooDataSetML\\Data\\A_FV_RFIDF.txt")
    val stopWords=sc.textFile("C:\\KDM\\Tutorial 3 Source Code\\Spark_TFIDF_W2V\\data\\stopwords.txt").collect()
    val stopWordsBroadCast=sc.broadcast(stopWords)


    val df1 = sc.textFile("C:\\Users\\shankar pentyala\\Desktop\\Yahootrain.csv")
    val df2 = df1.map(f=>f.split(",")).map(f=>{
     // val lemma1 = NLPLemma.returnLemma(f(1))
      val split1 = f(1).split(" ")
      (f(0),split1)
    })


    val stopWordRemovedDF=df2.map(f=>{
      //Filtered numeric and special characters out
      val filteredF=f._2.map(_.replaceAll("[^a-zA-Z]",""))
        //Filter out the Stop Words
        .filter(ff=>{
        if(stopWordsBroadCast.value.contains(ff.toLowerCase))
          false
        else
          true
      })
      (f._1,filteredF)
    })

    val data=stopWordRemovedDF.map(f=>{(f._1,f._2.mkString(" "))})

    val dfseq=stopWordRemovedDF.map(_._2.toSeq)

    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF(stopWordRemovedDF.count().toInt)  // VectorSize as the Size of the Vocab

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(dfseq)
    tf.cache()

    val idf = new IDF().fit(tf)
    //Creating Inverse Document Frequency
    val tfidf1 = idf.transform(tf)
    tfidf1.cache()



    val dff= stopWordRemovedDF.flatMap(f=>f._2)
    val vocab=dff.distinct().collect()
    val dataf = data.zip(tfidf1)

    // (tf, data, dff.count()) // Vector, Data, total token count
    //Labelling
    // val (inputVector, corpusData, vocabArrayCount) =
    //  preprocess(sc, params.input)



    val featureVector = dataf.map(f => {


      new LabeledPoint(f._1._1.toString().toDouble, f._2)
    })
    val training = featureVector
    featureVector.collect().foreach(f=>QFV.println(f))

    val numClasses = 8  ///Key factor
  val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val featureSubSet = "auto"
    val maxDepth = 5
    val maxBins = 32
    val numTrees = 10

    val model = RandomForest.trainClassifier(training,numClasses,categoricalFeaturesInfo,numTrees,featureSubSet,impurity,maxDepth,maxBins)

    //Mapping Data Set
    val df1_T = sc.textFile("C:\\Users\\shankar pentyala\\Desktop\\Yahootest.csv")
    val df2_T= df1_T.map(f=>f.split(",")).map(f=>{
     // val lemma1 = NLPLemma.returnLemma(f(1))
      val split1 = f(1).split(" ")
      (f(0),split1)
    })


    val stopWordRemovedDF_T=df2_T.map(f=>{
      //Filtered numeric and special characters out
      val filteredF=f._2.map(_.replaceAll("[^a-zA-Z]",""))
        //Filter out the Stop Words
        .filter(ff=>{
        if(stopWordsBroadCast.value.contains(ff.toLowerCase))
          false
        else
          true
      })
      (f._1,filteredF)
    })

    val data_T=stopWordRemovedDF_T.map(f=>{(f._1,f._2.mkString(" "))})
    val dfseq_T=stopWordRemovedDF_T.map(_._2.toSeq)

    //Creating an object of HashingTF Class
    val hashingTF_T= new HashingTF(stopWordRemovedDF_T.count().toInt)  // VectorSize as the Size of the Vocab

    //Creating Term Frequency of the document
    val tf_T = hashingTF_T.transform(dfseq_T)
    tf_T.cache()

    val idf_T = new IDF().fit(tf_T)
    //Creating Inverse Document Frequency
    val tfidf1_T = idf_T.transform(tf_T)
    tfidf1_T.cache()



    val dff_T= stopWordRemovedDF_T.flatMap(f=>f._2)
    val vocab_T=dff_T.distinct().collect()

    val dataf_T = data_T.zip(tfidf1_T)
    val featureVector_T = dataf_T.map(f => {

      new LabeledPoint(f._1._1.toString().toDouble,f._2)})

    val dataset = dataf_T.map(f=>f._1._2)

    val test_vector = dataf_T.map(f=>f._2)
    val t_v = featureVector_T.map(f=>f.features)
    val abc = model.predict(t_v)
    val abc_map = abc.map(f=>{
      if (f == 1.0) "Business&Finance"
      else if (f == 2.0) "Computers&Internet"
      else if (f == 3.0) "Entertainment&Music"
      else if (f == 4.0) "Family&Relationships"
      else if (f == 5.0) "Education&Reference"
      else if (f == 6.0) "Health"
      else if (f == 7.0) "Science&Mathematics"
      else "Error"
    }
    )
    val result = dataset.zip(abc_map)


    val abc12 =result.collect()
    abc12.foreach(f=>topic_output.println(f))

    featureVector_T.collect().foreach(f=>AFV.println(f))

  }
}
