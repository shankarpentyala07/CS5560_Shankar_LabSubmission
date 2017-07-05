import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by shankar pentyala on 04-07-2017.
  */
object openIE {
def main(args: Array[String])
{
  val sconfig = new SparkConf().setAppName("Lab4T1a").setMaster("local[*]")
   val scontext = new SparkContext(sconfig)
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  val inpdata = scontext.textFile("C:\\KDM\\bbcsport-fulltext\\bbcsport\\cricket\\001.txt")

  val oie = inpdata.map(str =>
  {
    JavaOpenIE.oiefun(str)
  })
  println(oie.collect.mkString("\n"))
 // println(oie.collect.mkString("\n"))
}

}
