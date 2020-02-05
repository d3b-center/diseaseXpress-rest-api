import com.typesafe.sbt.packager.MappingsHelper._

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SwaggerPlugin)
  .settings(
    name                        := "disease-express-rest-api",
    version                     := "0.1.6",
    scalaVersion                := "2.11.8",
    sbtVersion                  := "0.13.15",
    resourceDirectory in Test   := baseDirectory.value / "test/resources")
    
lazy val playVersion            = "2.5.13"
lazy val jacksonVersion         = "2.8.4"
lazy val jongoVersion           = "1.3.0"
lazy val enumeratumVersion      = "1.5.1"
lazy val swaggerVersion         = "0.6.1"
lazy val log4jVersion           = "1.2.17"
lazy val mongoVersion           = "3.4.2"
lazy val fongoVersion           = "2.1.0"
lazy val junitVersion           = "4.12"
lazy val junitInterfaceVersion  = "0.11" // see https://github.com/sbt/junit-interface


libraryDependencies ++= Seq(
      filters,
      cache,
    
      // play
      "com.typesafe.play"      %% "play"             % playVersion     withSources () withJavadoc (),
      "com.typesafe.play"      %% "play-json"        % playVersion     withSources () withJavadoc (),
      
      //swagger-ui
      "com.iheart"             %% "play-swagger"     % swaggerVersion  withSources () withJavadoc (),
      
      //mongo
      "org.jongo"              % "jongo"             % jongoVersion    withSources () withJavadoc (),
      "org.mongodb"            % "mongo-java-driver" % mongoVersion    withSources () withJavadoc (),
      "com.github.fakemongo"   % "fongo"             % fongoVersion    withSources () withJavadoc (),
    
      //enumeratum
      "com.beachape"   %% "enumeratum"           % enumeratumVersion withSources () withJavadoc (),
      "com.beachape"   %% "enumeratum-play-json" % enumeratumVersion withSources () withJavadoc (),
      
      //jackson
      "com.fasterxml.jackson.core"   % "jackson-databind"      % jacksonVersion withSources () withJavadoc (),
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion withSources () withJavadoc (),
      
      // tests
      "junit"        % "junit"           % junitVersion          % "test" withSources() withJavadoc(),
      "com.novocode" % "junit-interface" % junitInterfaceVersion % "test" withSources() withJavadoc()
  
  )
  

mappings in Universal ++= directory(baseDirectory.value / "public")
unmanagedResourceDirectories in Compile += { baseDirectory.value / "public" }

//https://stackoverflow.com/questions/25144484/sbt-assembly-deduplication-found-error
//https://github.com/swagger-api/swagger-samples/blob/master/scala/scala-play2.4/build.sbt
assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)                    => MergeStrategy.first
  case PathList("org", "apache", "commons", "logging", xs @ _*) => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html"            => MergeStrategy.first
  case "application.conf"                                       => MergeStrategy.concat
  case PathList("org", "slf4j", xs @ _*)                        => MergeStrategy.last
  case x if x.endsWith("com/mongodb/util/JSONParser.class")     => MergeStrategy.first
  case n if n.startsWith("reference.conf")                      => MergeStrategy.concat
  case PathList("META-INF", m) if m.equalsIgnoreCase("MANIFEST.MF") || m.endsWith(".properties") => MergeStrategy.discard

  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

//add domain package names for play-swagger to auto generate swagger definitions for domain classes mentioned in your routes
swaggerDomainNameSpaces := Seq("de.model.output")
