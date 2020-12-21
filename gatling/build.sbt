enablePlugins(GatlingPlugin)

scalaVersion := "2.12.12"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

val gatlingVersion = "3.4.2"
val testContainerVersion = "1.15.0"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion       % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % gatlingVersion       % "test"
libraryDependencies += "org.testcontainers"    % "testcontainers"            % testContainerVersion % "test"
