package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class DefaultHandler implements HttpHandler
{
    private static int count = 0;

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        System.out.println("default handle " + count);
        count++;

        try
        {
            switch (exchange.getRequestMethod().toLowerCase())
            {
                case "post":
                    System.out.println("is post");
                    break;
                case "get":
                    System.out.println("is get");
                    break;
                default:
                    System.out.println("is something else");
                    break;
            }

            OutputStream responseBody = exchange.getResponseBody();

            String uri = exchange.getRequestURI().toString();

            //System.out.println(uri);

            String url = "./web";

            String[] path = uri.split("/");

            Headers respHeader = exchange.getResponseHeaders();


            //return file
            if (uri.equals("/") || uri.isEmpty())
            {
                //default
                url += "/index.html";
                respHeader.set("Content-Type", "text/html");
            }
            else
            {
                //get file tag
                String tag = path[path.length - 1];

                String[] check_period = tag.split("[.]");
                if (check_period.length == 0)
                {
                    //System.out.println(" no period");
                }
                else
                {
                    String last_guy = check_period[check_period.length - 1];
                    switch (last_guy.toLowerCase())
                    {
                        case "css":
                            respHeader.set("Content-Type", "text/css");
                            break;
                        case "jpg":
                        case "jpeg":
                            respHeader.set("Content-Type", "image/jpeg");
                            break;
                        case "png":
                            respHeader.set("Content-Type", "image/png");
                            break;
                        case "html":
                            respHeader.set("Content-Type", "text/html");
                            break;
                        default:
                            break;
                    }
                }
            }

            url += uri;

            File file = new File(url);

            System.out.println(url);

            //create the body
            if (file.canRead() && file.exists())
            {
                //System.out.println("can read");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else
            {
                //send back a 404 page
                //System.out.println("can\'t read");
                file = new File("./web/HTML/404.html");
                respHeader.set("Content-Type", "text/html");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            }

            Files.copy(file.toPath(), responseBody);

            responseBody.close();
        }
        catch (IOException e)
        {
            //send back an error page
            OutputStream responseBody = exchange.getResponseBody();
            File file = new File("./web/HTML/500.html");
            Headers respHeader = exchange.getResponseHeaders();
            respHeader.set("Content-Type", "text/html");
            Files.copy(file.toPath(), responseBody);

            //System.out.println("caught");
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }


        //exchange.close();
    }
}