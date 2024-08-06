import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException
import kotlin.system.exitProcess

data class Arguments(val ip: String, val firstPort: Int, val lastPort: Int)

fun closeScript() {
    println("\nClosing script..")
    exitProcess(0)
}

fun scanner(arguments: Arguments) {
    for (port in arguments.firstPort..arguments.lastPort) {
        try {
            Socket().use { socket ->
                socket.connect(InetSocketAddress(arguments.ip, port), 200)
                println("Port $port OPEN.")
            }
        } catch (e: SocketException) {
            println("Port $port Closed.")
        }
    }
}

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("ERROR: Incorrect number of arguments given. Provide: <IP> <First Port> <Last Port>")
        exitProcess(-1)
    }

    val ip = args[0]
    val firstPort = args[1].toIntOrNull()
    val lastPort = args[2].toIntOrNull()

    if (ip.length > 16 || firstPort == null || lastPort == null || firstPort > 65535 || lastPort > 65535) {
        println("ERROR: Incorrect argument values. Maximum 16 chars for IP address and valid port numbers (0-65535).")
        exitProcess(-1)
    }

    val arguments = Arguments(ip, firstPort, lastPort)

    Runtime.getRuntime().addShutdownHook(Thread { closeScript() })

    println("-------------------------")
    println(">> Port Scanner Script <<")
    println("-------------------------")

    scanner(arguments)
}
