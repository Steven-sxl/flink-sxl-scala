package com.sxl.flink.scala.stream_POJO

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * 通过field 来定义key
  */
object StreamWcScala {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val data = env.socketTextStream("localhost",9999)

    import org.apache.flink.api.scala._

    data.flatMap(_.toLowerCase().split(","))
      .map(x => WC(x,1))
//      .keyBy("word")//field 选择key
        .keyBy(_.word)//选择器
      .timeWindow(Time.seconds(5))
      .sum("count")
      .print()
      .setParallelism(1)

    env.execute("StreamWcScala")
  }

  case class WC(word:String, count:Int)
}
