import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet

/**
  * Created by shankar pentyala on 04-07-2017.
  */
object wordnet {
  def main(args : Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "F:\\winutils")
    val con1 = new SparkConf().setAppName("Lab4Taskc").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc12 = new SparkContext(con1)
    val data12 = sc12.textFile("C:\\KDM\\bbcsport-fulltext\\bbcsport\\cricket\\001.txt")
    val dd12 = data12.map(f12 => {
      val wordnet12 = new RiWordNet("C:\\KDM\\WordNet-3.0")
     // val x : Array[String] = new Array[String](100)
      val farr12 = f12.split(" ").foreach(f123 => {
        val pos12 = wordnet12.getPos(f123)
        val x =wordnet12.getAllSynonyms(f123, "n", 10)
        if(x.size>0) {
          println(f123 + "-----" + x.mkString(","))
          println()
        }
      })

   })
    println(dd12.collect.mkString(","))

  }

}
