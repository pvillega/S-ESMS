package com.perevillega.sesms.support

import java.util.{Locale, ResourceBundle}
import scala.util.Random
import org.slf4j.LoggerFactory

/**
 * Class that contains the I18N strings and provides values for the reports
 * @param locale the locale to use when retrieving comments, defaults to 'en'
 */
case class Comment(locale: Locale = new Locale("en")) {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private val bundle = ResourceBundle.getBundle("Messages", locale)

  /**
   * Obtains a random comment given an I18N key, to give more variety to the comments
   * @param key key of the comment to get
   * @param params for the message, if any
   * @return a random comment matching the I18N key
   */
  def get(key: String, params: Any*) = {
    logger.debug(s"Comment.get($key, $params)")
    val res = if (bundle.containsKey(key)) {
      logger.debug(s"Key [$key] exists in bundle")
      val value = bundle.getString(key)

      if (value.length == 1) {
        logger.debug(s"Key [$key] is multi key")
        val options = (1 to value.toInt).map {
          i => bundle.getString(key + "." + i)
        }
        val chosen = Random.shuffle(options).head
        chosen.format(params)

      } else {
        logger.debug(s"Key [$key] is single key")
        value.format(params)
      }

    } else {
      logger.debug(s"No such key [$key] in bundle")
      key
    }

    logger.debug(s"Comment.get($key, $params) - Result[$res]")
    res
  }
}
