package de

import java.net.InetAddress

import scala.collection.JavaConverters.seqAsJavaListConverter

import com.mongodb.{ MongoClient, MongoClientURI }

import de.repository.MongoRepository
import de.service.{ MongoService, ServiceComponent }
import javax.inject.{ Inject, Singleton }
import play.api.mvc.Accepting

  // ===========================================================================
@Singleton // this is not necessary, I put it here so you know this is possible
class Context @Inject() (configuration: play.api.Configuration) {

  // ===========================================================================  
  // Mongodb
  private val mongo_uri      = configuration.getString("mongo.uri").get
  private val mongo_database = configuration.getString("mongo.db").get

  val client: MongoClient =
    new MongoClient(
      new MongoClientURI(mongo_uri))

  val jongo =
    new org.jongo.Jongo(
      client
        // warning: see https://github.com/bguerout/jongo/issues/254
        .getDB(mongo_database))

  private val mongoService =
    new MongoService with MongoRepository { val context = jongo }
  
  // ===========================================================================
  def getService(): ServiceComponent =
    mongoService

}

// ===========================================================================
