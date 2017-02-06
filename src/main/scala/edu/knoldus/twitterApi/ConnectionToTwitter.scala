package edu.knoldus.twitterApi

import com.typesafe.config.{ConfigException, ConfigFactory}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterException, TwitterFactory}


class ConnectionToTwitter(val credentialsFile: String) {

  @throws(classOf[TwitterException])
  @throws(classOf[ConfigException])
  def makeConnection(): Twitter = {
    try {
      val conf = ConfigFactory.load(credentialsFile)
      val consumerKey = conf.getString("consumerKey")
      val consumerSecretKey = conf.getString("consumerSecretKey")
      val accessToken = conf.getString("accessToken")
      val accessTokenSecret = conf.getString("accessTokenSecret")

      val configurationBuilder = new ConfigurationBuilder()
      configurationBuilder.setDebugEnabled(false)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecretKey)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)

      val twitter: Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
      twitter
    }
    catch {
      case exception: ConfigException => throw exception

      case exc: TwitterException => throw exc
    }
  }

}
