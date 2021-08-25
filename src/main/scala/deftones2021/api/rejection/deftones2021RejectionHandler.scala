package deftones2021.api.rejection

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.RejectionHandler
import deftones2021.api.directives.ErrorResponseDirectives.ErrorResponseData
import deftones2021.api.directives.ErrorResponseDirectives
import deftones2021.config.Config

trait deftones2021RejectionHandler extends ErrorResponseDirectives
  with Config {

  implicit val rejectionHandler = RejectionHandler.newBuilder()
    .handle { case deftones2021Rejection: deftones2021Rejection =>
      completeWithError(errorDocumentationUrl) {
        ErrorResponseData(deftones2021Rejection.statusCode, deftones2021Rejection.message)
      }
    }
    .handleNotFound {
      completeWithError(errorDocumentationUrl) {
        ErrorResponseData(StatusCodes.NotFound, "Not found")
      }
    }
    .result()
}
