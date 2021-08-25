package deftones2021.domain

case class Titles(
                   primary: String,
                   secondary: String,
                   tertiary: String
                 )

case class Availability(
                         from: String,
                         to: String,
                         label: String
                       )

case class Track(
                  `type`: String,
                  id: String,
                  urn: String,
                  titles: Titles,
                  availability: Availability
                )
