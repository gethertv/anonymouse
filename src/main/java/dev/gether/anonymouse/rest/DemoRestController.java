package dev.gether.anonymouse.rest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DemoRestController extends HttpServlet {

    // GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String urlPath = request.getPathInfo();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        // /API [register onEnable()]
        // urlPath --->  /api/{urlPath}
        switch (urlPath) {
            case "/" -> {
                response.getWriter().println("# "+urlPath);
                return;
            }
            case "/temp" -> {
                response.getWriter().println("#TEMP "+urlPath);
                return;
            }
        }

        response.getWriter().println("Wrong url "+urlPath);
    }

    // GET
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String urlPath = request.getPathInfo();

        switch (urlPath) {
            case "/" -> {
                String requestBody = getRequestData(request);
                response.getWriter().println("# "+urlPath + " data: "+requestBody);
                return;
            }
            case "/temp" -> {
                String requestBody = getRequestData(request);
                // if its JSON
                //JSONObject json = new JSONObject(requestBody);
                //String command = json.getString("KEY");
                response.getWriter().println("#TEMP "+urlPath + " data: "+requestBody);
                return;
            }
        }

        // wrong post URL
        // [!] OPTIONAL

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);


        PrintWriter writer = response.getWriter();
        writer.println("Wrong Post URL");

    }

    public String getRequestData(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            return requestBody.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
