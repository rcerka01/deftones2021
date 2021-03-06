import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.Specs2RouteTest
import deftones2021.api.Api
import deftones2021.domain.Track
import deftones2021.domain.response.{ErrorResponse, Response}
import org.specs2.mutable.Specification

class MultipleTrackSpec extends Specification
  with Specs2RouteTest
  with Api {

  override lazy val maximumPageLimit: Int = 50

  "The service for multiple tracks" should {

    "return a response with items" in {
      Get("/tracks") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        contentType must be equalTo `application/json`
        responseAs[Response[Track]].total must be > 0
        header("Cache-Control").get.value must be equalTo "public, max-age=300, s-maxage=300, stale-while-revalidate=7200, stale-if-error=7200"
      }
    }

    "return a response with the requested limit" in {
      Get("/tracks?limit=1") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        responseAs[Response[Track]].total must be equalTo 1
      }
    }

    "return a 400 Bad Request when a limit greater than the maximum is requested" in {
      Get("/tracks?limit=51") ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.BadRequest
        responseAs[ErrorResponse].errors.size must be > 0
      }
    }
  }

}
