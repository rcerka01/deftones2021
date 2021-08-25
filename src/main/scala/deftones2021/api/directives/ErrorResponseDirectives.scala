package deftones2021.api.directives

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import deftones2021.api.directives.ErrorResponseDirectives.ErrorResponseData
import deftones2021.domain.marshalling.JsonSerializers
import deftones2021.domain.response.ErrorResponse

trait ErrorResponseDirectives extends JsonSerializers {

  def completeWithError(documentationUrl: String)
                       (errorResponseData: ErrorResponseData): StandardRoute = {

    val errorResponse = ErrorResponse(
      documentationUrl = documentationUrl,
      httpStatus = errorResponseData.statusCode.intValue,
      message = errorResponseData.message)

    complete(errorResponseData.statusCode, errorResponse)
  }

}

object ErrorResponseDirectives {
  case class ErrorResponseData(statusCode: StatusCode, message: String)
}

