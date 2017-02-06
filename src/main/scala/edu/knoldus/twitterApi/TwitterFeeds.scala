package edu.knoldus.twitterApi

import org.apache.log4j.Logger
import twitter4j.{Query, Status}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TwitterFeeds {
  val connectionToTwitter = new ConnectionToTwitter("credentials.conf")
  val twitter = connectionToTwitter.makeConnection()
  val log = Logger.getLogger(this.getClass)
  log.info("------*Connection established*---------")

  /**
    *
    * @param hashTag
    * @return
    */
  def getTweets(hashTag: HashTag): Future[List[Status]] = Future {
    val count = 100
    log.info("creating query...")
    val query = new Query(hashTag.toString)
    query.setSince("2016-11-24")
    query.setCount(count)

    log.info("Searching...")
    val list = twitter.search(query)
    val tweets = list.getTweets.asScala.toList
    tweets
  }

  /**
    *
    * @param futureListOfStatus
    * @return
    */
  def getTweets(futureListOfStatus: Future[List[Status]]): Future[List[MyTweets]] = {

    val allTweets = futureListOfStatus map { listOfstatus =>
      listOfstatus map { status =>
        MyTweets(status.getText, status.getUser.getScreenName)
      }
    }
    allTweets
  }

  /**
    *
    * @param futureTweets
    * @return
    */
  def countNoOfTweets(futureTweets: Future[List[MyTweets]]): Future[Int] = {
    futureTweets.map(_.size)
  }

  /**
    * It should return average but somehow i couldn't do that
    * So I am returning only Sum
    *
    * @param futureTweets
    * @return
    */
  def averageLikesAndRetweets(futureTweets: Future[List[Status]]): List[Future[Int]] = {
    val futureReTweetsCount = futureTweets map { tweets => tweets map { status => status.getRetweetCount } }
    val futureSumOfRetweets = futureReTweetsCount map (_.sum)
    val futureSizeOfReTweets = futureReTweetsCount map (_.size)
    // val avg = futureSumOfRetweets map(sum => sum / futureSizeOfReTweets.map(_) )
    val futureLikesCount = futureTweets map (tweets => tweets map { status => status.getFavoriteCount })
    val futureSumOfLikes = futureLikesCount map (_.sum)

    List(futureSumOfRetweets, futureSumOfLikes)
  }

}
