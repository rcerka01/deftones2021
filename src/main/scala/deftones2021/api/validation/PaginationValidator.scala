package deftones2021.api.validation

class PaginationValidator {
  def isInvalidOffset(offset: Int): Boolean = offset < 0

  def isInvalidPageLimit(maximumPageLimit: Int)(requestedPageLimit: Int): Boolean =
    requestedPageLimit < 0 || requestedPageLimit > maximumPageLimit
}

