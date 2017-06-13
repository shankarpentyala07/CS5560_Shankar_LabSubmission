package com.umkc.labassignment

/**
  * Created by shankar pentyala on 11-06-2017.
  */
object currenttime extends App{
  val time1 = new org.joda.time.DateTime()
  println("The current time is  "+ time1.toString("hh:mm:ss aa"))

}
