
package edu.knoldus.twitterApi

import com.typesafe.config.ConfigException
import org.scalatest.FunSuite
import twitter4j.TwitterException
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class TwitterFeedsTest extends FunSuite {

  val twitterFeeds = new TwitterFeeds()
  val hashTag = new HashTag("Modi")

  test("Output should have received all tweets") {
    val resultantTweets = Await.result(twitterFeeds.getTweets(hashTag), 1000.second)
    val expected = 100
    val actual = resultantTweets.size
    assert(actual <= expected)
  }

  test("Output should contain actual no. of tweets") {
    val listOfMyTweets: Future[List[MyTweets]] = Future {
      List(MyTweets("Hello", "Simar"), MyTweets("Good Morning", "Simar"))
    }
    val resultantCount = Await.result(twitterFeeds.countNoOfTweets(listOfMyTweets), 1000.second)
    val expected = 2
    val actual = resultantCount
    assert(actual == expected)
  }

  test("Avarage Likes and Retweets should be greater than zero") {
   // val result = Await.result(twitterFeeds.averageLikesAndRetweets())
  }

}
