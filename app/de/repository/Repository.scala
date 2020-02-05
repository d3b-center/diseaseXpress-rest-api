package de.repository

import org.jongo.Jongo

import de.model.input.FilterUnit
import de.dao.MongoDAO

// ===========================================================================
trait Dao {
  
  def find(
      collectionName: String,
      filters:        Seq[FilterUnit])
    : Any // TODO
    
}

// ===========================================================================
trait Repository { def dao: Dao }

  // ---------------------------------------------------------------------------
  trait MongoRepository extends Repository {
  
    val context: Jongo
    
    def dao = { new MongoDAO(context) }

  }
  
// ===========================================================================
