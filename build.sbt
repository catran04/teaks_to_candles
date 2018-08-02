name := "teaks_to_candles"

version := "1.0"

scalaVersion := "2.12.6"

// for timing
libraryDependencies += "joda-time" % "joda-time" % "2.9.9"


// for a logging
libraryDependencies += "log4j" % "log4j" % "1.2.17"

// for a parsing jsons
libraryDependencies += "org.json4s" %% "json4s-native" % "3.5.2"

libraryDependencies += "org.json4s" %% "json4s-core" % "3.5.2"

// for tests
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

// https://mvnrepository.com/artifact/io.netty/netty-all
libraryDependencies += "io.netty" % "netty-all" % "4.1.25.Final"

