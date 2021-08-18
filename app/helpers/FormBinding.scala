package helpers

import play.api.data.format.Formatter
import play.api.data.Mapping
import play.api.data.format.Formats._
import play.api.data.FormError
import play.api.data.Forms._
import play.api.data.validation.Constraint
import play.api.data.validation.Invalid
import play.api.data.validation.Valid
import play.api.data.validation.ValidationError
import play.api.data.validation.Constraints

import java.time.LocalDate

object FormBinding {

  implicit def doubleFormat: Formatter[Double] = new Formatter[Double] {

    override val format = Some( "format.double", Nil )

    def bind( key: String, data: Map[String, String] ) = {
      stringFormat.bind( key, data ).flatMap { s =>
        scala.util.control.Exception.allCatch[Double]
          .either( java.lang.Double.parseDouble( s ) )
          .left.map( e => Seq( FormError( key, "error.double", Nil ) ) )
      }
    }

    def unbind( key: String, value: Double ) = Map( key -> value.toString )
  }

  implicit def dateTime: Formatter[LocalDate] = new Formatter[LocalDate] {

    override val format = Some( "format.double", Nil )

    def bind( key: String, data: Map[String, String] ) = {
      stringFormat.bind( key, data ).flatMap { s =>
        scala.util.control.Exception.allCatch[LocalDate]
          .either( LocalDate.parse(s) )
          .left.map( e => Seq( FormError( key, "error.localDate", Nil ) ) )
      }
    }

    def unbind( key: String, value: LocalDate ) = Map( key -> value.toString )
  }

}
