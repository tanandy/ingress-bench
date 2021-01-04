package helloapp

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class IngressSimulationCapacityTest10UsersPer5Minutes100Times extends Simulation {
  val ingressBaseUrl = System.getenv("INGRESS_BASE_URL")

  val httpProtocol = http
    .baseUrl(ingressBaseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Capacity Scenario") // Increasing step by step the number of users during the simulation, in order to know the limits of system
    .exec(http("Access Home Page")
      .get("/"))
  
// Add 10 Constant Users per second every 5 minutes
// Ramp Lasting 30 seconds
// 100 times
  setUp(scn.inject(incrementUsersPerSec(10).times(100).eachLevelLasting(5.minutes).separatedByRampsLasting(30.seconds).startingFrom(10))).protocols(httpProtocol)
}
