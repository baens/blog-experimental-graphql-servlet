import com.coxautodev.graphql.tools.SchemaParser
import graphql.servlet.SimpleGraphQLServlet
import org.glassfish.grizzly.http.server.CLStaticHttpHandler
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.grizzly.servlet.WebappContext

fun main(args: Array<String>) {
    // Setting up graphql schema, servlet, and bindings to server
    val graphqlSchema = SchemaParser.newParser()
            .file("schema.graphqls")
            .resolvers(LinkQueryResolver()) // we can add any number of resolvers here with any name
            .build()
            .makeExecutableSchema()

    val graphqlServlet = SimpleGraphQLServlet
            .builder(graphqlSchema)
            .build()

    val webappContext = WebappContext("Graphql Context", "/")

    webappContext.addServlet("GraphQL Endpoint", graphqlServlet)
                 .addMapping("/graphql")

    val server = HttpServer.createSimpleServer()

    webappContext.deploy(server)

    // This is the configuration to allow serving up HTML content
    server.serverConfiguration
          .addHttpHandler(CLStaticHttpHandler(Thread.currentThread().contextClassLoader), "/")

    server.start()

    if (System.getenv()["SHUTDOWN_TYPE"].equals("INPUT")) {
        println("Press any key to shutdown")
        readLine()
        println("Shutting down from input")
        server.shutdownNow()
    } else {
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutting down from shutdown hook")
            server.shutdownNow()
        })

        println("Press Ctrl+C to shutdown")
        Thread.currentThread().join()
    }
}
