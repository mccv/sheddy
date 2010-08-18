import sbt._
import com.twitter.sbt._

class SheddyProject(info: ProjectInfo) extends StandardProject(info)
{
  val jettyVersion = "6.1.25"
  val jetty = "org.mortbay.jetty" % "jetty" % jettyVersion
  val jettyUtil = "org.mortbay.jetty" % "jetty-util" % jettyVersion
}
