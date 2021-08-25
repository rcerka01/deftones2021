package deftones2021.domain.response

import java.util.UUID
import org.joda.time.{DateTime, DateTimeZone}

case class ErrorResponse(errors: Seq[ErrorItem])

case class ErrorItem(
  id: String,
  href: String,
  status: Int,
  message: String,
  replied_at: DateTime)

object ErrorResponse {
  private def hash = UUID.randomUUID.toString

  def apply(documentationUrl: String, httpStatus: Int, message: String): ErrorResponse = {
    ErrorResponse(
      errors = Seq(
        ErrorItem(
          id = hash,
          href = documentationUrl,
          status = httpStatus,
          message = message,
          replied_at = DateTime.now(DateTimeZone.UTC)
        )
      )
    )
  }
}
