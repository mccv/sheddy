package com.twitter.sheddy.examples

import com.twitter.sheddy._
import org.mortbay.jetty._

object SheddyExample1 {
  /**
   * Start a server on 8080 with a function that responds to everything
   */
  val server = new Sheddy(8080, { case (path, request, response) => {
    val out = response.getWriter()
    response.setStatus(200)
    out.write("got path " + path)
    out.close
  }})
}

object SheddyExample2 {
  /**
   * Start a server on 8081 with no handlers
   */
  val server = new Sheddy(8081)

  /**
   * And add a handler that responds to everything under /test
   */
  server.addHandler("/test/.*") { (path, request, response) => {
    val out = response.getWriter()
    response.setStatus(200)
    out.write("got path " + path)
    out.close
  }}
}

object SheddyExample3 {
  /**
   * Start a server on 8082 with no handlers
   */
  val server = new Sheddy(8082)

  /**
   * Add a handler for everything under /test1
   */
  server.addHandler("/test1/.*") { (path, request, response) => {
    val out = response.getWriter()
    response.setStatus(200)
    out.write("got path for test1 " + path)
    out.close
  }}

  /**
   * Add another handler under /test2
   */
  server.addHandler("/test2/.*") { (path, request, response) => {
    val out = response.getWriter()
    response.setStatus(200)
    out.write("got path for test2 " + path)
    out.close
  }}
}

object SheddyExample4 {
  /**
   * Use your own Jetty server object
   */
  val jettyServer = new Server(8083)
  val server = new Sheddy(jettyServer)

  /**
   * And add a handler for everything
   */
  server.addHandler(".*") { (path, request, response) => {
    val out = response.getWriter()
    response.setStatus(200)
    out.write("got path " + path)
    out.close
  }}
}
