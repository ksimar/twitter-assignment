package edu.knoldus.twitterApi

import org.scalatest.FunSuite
import twitter4j.Twitter


class ConnectionToTwitterTest extends FunSuite {

  val connectionObj = new ConnectionToTwitter("credentials.conf")

  test("Connection should be made") {
    val connection: Twitter = connectionObj.makeConnection()
  }

}

