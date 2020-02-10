package com.sxl.flink.scala.batch_test

import org.apache.flink.api.scala.ExecutionEnvironment

object BatchWcScala {

  def main(args: Array[String]): Unit = {

    val input = "/Users/sxl/Desktop/test/hello.txt"

    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = env.readTextFile(input)

    import org.apache.flink.api.scala._

    data.flatMap(_.toLowerCase.split("\t"))
      .filter(_.nonEmpty)
      .map((_,1))
      .groupBy(0)
      .sum(1)
      .print()

  }
}
