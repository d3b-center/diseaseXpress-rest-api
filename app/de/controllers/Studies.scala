package de.controllers

import play.api.Configuration
import play.api.mvc.Controller
import play.api.libs.json.Json
import de.repository.SamplesRepository
import play.api.mvc.Action

// ===========================================================================
class Studies @javax.inject.Inject()(
    conf: Configuration)
  extends Controller {
  def getStudies() =
    Action {
      implicit request =>
        Ok(Json.toJson(SamplesRepository.getStudies))
    }
}

// ===========================================================================
