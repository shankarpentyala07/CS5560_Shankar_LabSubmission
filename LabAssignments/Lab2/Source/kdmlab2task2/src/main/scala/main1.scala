import org.apache.spark.api.java.JavaRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by shankar pentyala on 20-06-2017.
  */
object main1 {

  def main(args: Array[String]) {
    //spark props
    System.setProperty("hadoop.home.dir", "F:\\winutils");
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val input = sc.textFile("C:/KDM/bbcsport-fulltext/bbcsport/cricket/001.txt")
    val caller:javaclass = new javaclass();
    val lemmawords = input.map(t1=>caller.getlemma(t1))
     val mapped = lemmawords.flatMap(word=>word.split(" ")).filter(w1=>(!(w1.isEmpty))).map(w=>(w.charAt(0),w))
    val output = mapped.groupByKey()
    output.foreach(println)
  }
}
