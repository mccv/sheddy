Sheddy
======

Sheddy is a simple wrapper for Jetty.  It allows you to
*   Create a (simple) Jetty server with minimal overhead
*   Handle web requests directly with Scala functions

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
  }

More examples can be found in src/examples.

Disclaimer
==========

This code is extremely minimally tested.  Depending on how useful I find the
package I may or may not beef up the test suite.
