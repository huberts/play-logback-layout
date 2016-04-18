name := "play-logback-layout"
version := "1.0"
scalaVersion := "2.11.7"

lazy val playlogbacklayout = project in file(".")

libraryDependencies += "ch.qos.logback" % "logback-core" % "1.1.7"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"
