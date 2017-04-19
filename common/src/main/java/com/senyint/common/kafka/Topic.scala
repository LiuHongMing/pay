package com.senyint.common.kafka

object Topic {

  val LOG: String = "log"

  def topics(ele: String*): List[String] = {
    ele.toList
  }

}
