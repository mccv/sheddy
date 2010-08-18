package com.twitter.sheddy

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.mortbay.jetty.Server
import org.mortbay.jetty.handler.{AbstractHandler, HandlerList}
import scala.collection.mutable.HashMap

/**
 * A Jetty Handler that calls a function if the request path matches a given regex
 */
class FunctionHandler(urlPattern: String, f: Sheddy.handlerFunc) extends AbstractHandler{
  override def handle(target: String,
                      request:HttpServletRequest,
                      response: HttpServletResponse, dispatch:Int) = {
    if (target.matches(urlPattern)) {
      f(target, request, response)
    }
  }
}

/**
 * Wraps a Jetty HandlerList, adding support adding/removing FunctionHandlers by regex
 */
class FunctionHandlerList(handlerList: HandlerList) {
  val handlerPatternMap = new HashMap[String, FunctionHandler]
  def addHandler(urlPattern: String)(f: Sheddy.handlerFunc) = {
    val handler = new FunctionHandler(urlPattern, f)
    handlerPatternMap + (urlPattern -> handler)
    handlerList.addHandler(handler)
  }

  def removeHandler(urlPattern: String): Unit = {
    val handler = handlerPatternMap(urlPattern)
    handlerList.removeHandler(handler)
  }
}

object Sheddy {
  type handlerFunc = (String, HttpServletRequest, HttpServletResponse) => Unit
}

/**
 * Wraps a Jetty Server, attaching a FunctionHandlerList for adding/removing function handlers
 */
class Sheddy(server: Server) {

  def this(port: Int) = this(new Server(port))

  def this(port: Int, f: Sheddy.handlerFunc) = {
    this(new Server(port))
    addHandler(".*")(f)
  }

  private val handlerList = new HandlerList()
  val functionHandlerList = new FunctionHandlerList(handlerList)

  server.addHandler(handlerList)
  server.start

  def stop() = {
    server.stop()
  }

  def start() = {
    server.start()
  }

  def addHandler(urlPattern: String)(f: Sheddy.handlerFunc) = {
    functionHandlerList.addHandler(urlPattern)(f)
  }

  def removeHandler(urlPattern: String): Unit = {
    functionHandlerList.removeHandler(urlPattern)
  }
}
