package helloapp

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class IngressSimulationSoakTest500Users5Hours extends Simulation {
  val ingressBaseUrl = System.getenv("INGRESS_BASE_URL")

  val httpProtocol = http
    .baseUrl(ingressBaseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Soak Scenario") // Simulating users over a long period of time, in order to detect any performance degradations over time
    .exec(http("Access Home Page")
      .get("/"))

  // 1 Check overview during users arriving
  // 2 Check throughput
  setUp(scn.inject(rampUsersPerSec(1) to 500 during 10.minutes, constantUsersPerSec(500) during 5.hours)).protocols(httpProtocol)
}
