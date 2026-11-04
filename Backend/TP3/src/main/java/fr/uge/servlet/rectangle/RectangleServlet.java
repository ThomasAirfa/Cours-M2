package fr.uge.servlet.rectangle;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class RectangleServlet extends HttpServlet {
    public static String readFromInputStream(InputStream inputStream) throws IOException {
        var lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        return lines.collect(Collectors.joining("\n"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html ; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/templates/rectangle-form.html");

    }
}