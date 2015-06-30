lazy val root = (project in file(".")).
    settings(
        name := "test",
        version := "1.0",
        scalaVersion := "2.11.4",
        libraryDependencies ++= Seq( 
            "joda-time" % "joda-time" % "2.8.1",
            "org.joda" % "joda-convert" % "1.2"
        )
    )
