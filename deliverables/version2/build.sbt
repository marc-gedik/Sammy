name := "sammy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
val graphCore = "com.assembla.scala-incubator" %% "graph-core" % "1.9.4"
val graphJson = "com.assembla.scala-incubator" %% "graph-json" % "1.9.2"
val scopt = "com.github.scopt" %% "scopt" % "3.3.0"
val freetts = "org.mobicents.external.freetts" % "freetts" % "1.0"

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= Seq(
  //jdbc,
  cache,
  ws,
  specs2 % Test,
  scalaTest,
  graphCore,
  graphJson,
  scopt,
  freetts
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true