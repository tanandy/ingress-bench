package helloapp

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class IngressSimulationLoadTest5000Users extends Simulation {
  val ingressBaseUrl = System.getenv("INGRESS_BASE_URL")

  val httpProtocol = http
    .baseUrl(ingressBaseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Load Scenario") // Simulating an expected load of 5000 concurrent users
    .exec(http("Access Home Page")
      .get("/"))
 
  setUp(scn.inject(atOnceUsers(5000))).protocols(httpProtocol)
}
