package de.controllers

import de.model.output.GeneInfo
import de.repository.GeneRepository
import de.validators.{ GeneIdFilters, GeneSymbolQuery }
import play.api.libs.json.Json
import play.api.mvc.Controller
import utils.Implicits.AnythingImplicits
import de.validators.GeneSymbolFilters
import play.api.mvc.Action

// ===========================================================================
class Genes @javax.inject.Inject() (
      configuration: play.api.Configuration)
    extends Controller {
  
  // ---------------------------------------------------------------------------
  def getGeneIds() =
    Action {
      Ok(Json.toJson(GeneRepository.getGeneIds))
    }

  // ---------------------------------------------------------------------------
  def getGenesBySymbol() =
    Action {
      Ok(Json.toJson(GeneRepository.getGeneSymbols))
    }

  // ---------------------------------------------------------------------------
  def getGeneInfoByIds(gene_ids: String) =
    Action {
      implicit request =>

        val result = GeneIdFilters(gene_ids.split(",", -1))

        result match {
          case Left(errorObject) =>
            BadRequest(Json.toJson(errorObject.formatJson))

          case Right(genes) => render {
            case Accepts.Json()    => Ok(Json.toJson(genes))
            case Play.AcceptsTsv() => Ok(TsvFormatter.geneInfo(genes))
          }
        }
    }

  // ---------------------------------------------------------------------------
  def getGeneInfoBySymbols(gene_symbols: String) =
    Action {
      implicit request =>

        val result = GeneSymbolFilters(gene_symbols.split(",", -1))

        result match {
          case Left(errorObject) =>
            BadRequest(Json.toJson(errorObject.formatJson))

          case Right(genes) => render {
            case Accepts.Json()    => Ok(Json.toJson(genes))
            case Play.AcceptsTsv() => Ok(TsvFormatter.geneInfo(genes))
          }
        }
  }

}

// ===========================================================================
