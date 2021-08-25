package deftones2021.api.error

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ExceptionHandler
import deftones2021.api.directives.ErrorResponseDirectives
import deftones2021.api.directives.ErrorResponseDirectives.ErrorResponseData
import deftones2021.config.Config
import org.slf4s.Logging

trait deftones2021ExceptionHandler extends ErrorResponseDirectives
  with Config
  with Logging {

  implicit val routingExceptionHandler =
    ExceptionHandler {
      case exception => {
        log.error(exception.getMessage, exception)
        completeWithError(errorDocumentationUrl) {
          ErrorResponseData(StatusCodes.InternalServerError, "Server Error")
        }
      }
    }
}
