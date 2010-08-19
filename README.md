Sheddy
======

Sheddy is a simple wrapper for Jetty.  It allows you to

*   Create a (simple) Jetty server with minimal overhead
*   Handle web requests directly with Scala functions

This was inspired by the difficulty encountered in mocking out various
portions of the Servlet milieu.  With Sheddy you can start the actual
server and run tests against a live Jetty/Servlet environment.

Note that you can provide your own Server object to Sheddy, and you
can attach function handlers to your own handler chain, allowing you
to simulate your production server fairly easily.

As an example, the following code starts a server on port 8080 and
prints the path back out to the requester

    import com.twitter.sheddy._
    import org.mortbay.jetty._
 
    object SheddyExample {
      val server = new Sheddy(8080, { case (path, request, response) => {
        val out = response.getWriter()
        response.setStatus(200)
        out.write("got path " + path)
        out.close
      }})
      ... do stuff to the server here ...
      // now destroy server
      server.stop
    }

More examples can be found in src/examples.

Disclaimer
==========

This code is extremely minimally tested.  Depending on how useful I find the
package I may or may not beef up the test suite.

Credits
=======

Inspired by the awfulness that is trying to implement a to-spec OAuth
server with the Servlet API and Jetty.
