package de.model.input

import de.controllers.EnumCombo
import de.model.Error
import de.model.output.GeneInfo
import de.utils.Enums.Normalization
import de.validators._
import play.api.libs.json.JsValue

case class InputFilters(
  primary_ref_ids: Option[Seq[GeneInfo]],
  secondary_ref_ids: Option[SecondaryIdRef],
  normalization_combo:Map[Normalization, InputDataModel])

// ===========================================================================

object InputFilters {

  def apply(
    primaryObject:  PrimaryIdsValidator = GeneIdFilters,
    primaryIds:     Seq[String],
    secondaryIds:   Option[Either[Seq[String], JsValue]],
    normalizations: Option[String] = None,
    projection:     Option[String] = None): Either[Seq[Error], InputFilters] = {

    val _primary_ids: Either[Error, Option[Seq[GeneInfo]]] = if(primaryIds.isEmpty) {
      Right(None)
    } else {
      primaryObject(primaryIds).right.map { x => Some(x) }
    }

    val _secondary_ids: Either[Error, Option[SecondaryIdRef]] = secondaryIds match {
      case Some(obj) => SecondaryIds(obj).right.map { x => Some(x) } //project right as an option
      case None      => Right(None)
    }

    val _normalizations = ValidateNormalization(normalizations)

    val _projection = ValidateProjection(projection)

    val errors = Seq(
                      _primary_ids.left.toOption,
                      _secondary_ids.left.toOption,
                      _normalizations.left.toOption,
                      _projection.left.toOption).flatten

    if (errors.isEmpty) {
      Right(InputFilters(
        primary_ref_ids     = _primary_ids.right.get,
        secondary_ref_ids   = _secondary_ids.right.get,
        normalization_combo = EnumCombo(
                                        _projection.right.get,
                                        _normalizations.right.get).toMap))
    } else {
      Left(errors)

    }

  }
}
