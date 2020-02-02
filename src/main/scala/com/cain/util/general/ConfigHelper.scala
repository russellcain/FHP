package com.cain.util.general

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import com.cain.route.Main.AppStarter.system

object ConfigHelper {

  def configReader: Config = system.settings.config

  val config = ConfigFactory.load

  def getStringValue(path: String): String = if (config.hasPath(path)) config.getString(path) else ""

  def getIntValue(path: String): Int = if (config.hasPath(path)) config.getInt(path) else -1

  def getStringList(path: String): List[String] = {

    if (config.hasPath(path)) {

      config.getStringList(path).asScala.toList

    } else List.empty[String]
  }

  val operatingSystem: String = System.getProperty("os.name").toLowerCase()
  def getHost(): String = {
    //    config.getString("host")
    operatingSystem match {
      case a if a.contains("mac") => "mac"
      case b if b.contains("windows") => "local"
      case _ => "server"
    }
  }
}
