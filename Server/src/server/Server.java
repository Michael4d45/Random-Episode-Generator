package server;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import handlers.DefaultHandler;

public class Server
{
    private static final int MAX_WAITING_CONNECTIONS = 12;

    public static void main(String[] args)
    {
        String portNumber = args[0];
        new Server().run(portNumber);
    }

    private void run(String portNumber)
    {

        HttpServer server;
        System.out.println("Initializing HTTP Server");

        try
        {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        //clear
        //server.createContext("/clear", new ClearHandler());

        //do last
        server.createContext("/", new DefaultHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }
}

