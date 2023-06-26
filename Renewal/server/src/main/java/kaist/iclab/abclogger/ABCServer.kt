package kaist.iclab.abclogger

import io.grpc.Server
import io.grpc.ServerBuilder

class ABCServer(private val port: Int) {
    val server: Server = ServerBuilder
        .forPort(port)
        .addService(TestService())
        .build()


    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@ABCServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    internal class TestService : TestGrpcKt.TestCoroutineImplBase() {
        override suspend fun testReply(request: TestRequest) = testResponse {
            content = "Hello ${request.content}"
        }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = ABCServer(port)
    server.start()
    server.blockUntilShutdown()
}