package edu.knoldus.twitterApi

import org.scalatest.FunSuite

class HashTagTest extends FunSuite {

  val hashTag = new HashTag("Modi")

  test("output should be '#' prepended to the String") {
    val output = assert( hashTag.toString == "#Modi")
  }

}
