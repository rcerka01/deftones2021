import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.{MessageEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.Specs2RouteTest
import deftones2021.api.Api
import deftones2021.domain.{Availability, Titles, Track}
import deftones2021.domain.response.{ErrorResponse, Response}
import org.specs2.mutable.Specification

class SingleTrackSpec extends Specification
  with Specs2RouteTest
  with Api {

  val testTrack = Track(
    `type` = "test_type",
    id = "test_id",
    urn = "test_uri",
    titles = Titles(
      primary = "test_primary",
      secondary = "test_secondary",
      tertiary = "test_tertirary"),
    availability = Availability(
      from = "test_from",
      to = "test_to",
      label = "test_label"))

  val initialTrackId: String = "nznx3r"

  "The service for single track" should {

    "return a response with item" in {
      Get("/track/" + initialTrackId) ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.OK
        contentType must be equalTo `application/json`
        header("Cache-Control").get.value must be equalTo "public, max-age=300, s-maxage=300, stale-while-revalidate=7200, stale-if-error=7200"
        responseAs[Response[Track]].total must be > 0
        responseAs[Response[Track]].results.head.id must be equalTo initialTrackId
      }

      "return a response without item" in {
        Get("/track/abc") ~> Route.seal(routes) ~> check {
          status must be equalTo StatusCodes.OK
          contentType must be equalTo `application/json`
          header("Cache-Control").get.value must be equalTo "public, max-age=300, s-maxage=300, stale-while-revalidate=7200, stale-if-error=7200"
          responseAs[Response[Track]].total must be equalTo 0
          responseAs[Response[Track]].results.size must be equalTo 0
        }
      }
    }

    "return a response when adding item" in {
      val entity = Marshal(testTrack).to[MessageEntity].value.get.get
      Post("/track/create", Map("Content-Type"->"application/json")).withEntity(entity) ~> Route.seal(routes) ~> check {
        status must be equalTo StatusCodes.Created
        contentType must be equalTo `application/json`

        Get("/track/test_id") ~> Route.seal(routes) ~> check {
          status must be equalTo StatusCodes.OK
          responseAs[Response[Track]].results.head.id must be equalTo testTrack.id
        }
      }
    }
  }
}
