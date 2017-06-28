/**
  * Created by shankar pentyala on 27-06-2017.
  */
object Ngram {
  def main(args: Array[String]): Unit = {
    val a = getNGrams("Rinky is pinky pinky is rinky",2)
    a.foreach(f=>println(f.mkString(" ")))
  }

  def getNGrams(sent: String, gramval:Int): Array[Array[String]] = {
    val w = sent
    val ngrams = w.split(' ').sliding(gramval)
    ngrams.toArray
  }
}
