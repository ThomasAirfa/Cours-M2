package fr.uge.servlet.rectangle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebServlet("/rectangle")
public class RectangleServlet extends HttpServlet {
    private String template;

    // Appelée une seule fois, à l'initialisation du servlet
    @Override
    public void init() throws ServletException {
        try (var in = getServletContext().getResourceAsStream("/WEB-INF/templates/rectangle-form.html")) {
            if (in == null) {
                throw new ServletException("Template introuvable");
            }
            template = readFromInputStream(in);
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }


    public static String readFromInputStream(InputStream inputStream) throws IOException {
        var lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        return lines.collect(Collectors.joining("\n"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        writeForm(response, "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        var height = request.getParameter("height");
        var width = request.getParameter("width");
        try {
            var h = Double.parseDouble(height.trim());
            var w = Double.parseDouble(width.trim());
            response.setContentType("text/html; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("<html><body><p>Aire : " + (h * w) + "</p></body></html>");
        } catch (NumberFormatException | NullPointerException e) {
            writeForm(response, height, width, "Veuillez entrer deux nombres valides.");
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    private void writeForm(HttpServletResponse response, String height, String width, String message)
            throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        var page = template
                .replace("{{height}}", escape(height))
                .replace("{{width}}", escape(width))
                .replace("{{message}}", escape(message));
        response.getWriter().print(page);
    }
}