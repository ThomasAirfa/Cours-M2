package fr.uge.servlet.hellosession;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/hellosessionbetter")
public class HelloSessionBetter extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    var session = request.getSession(true);
    int count;
    synchronized (session) {
      var value = (Integer) session.getAttribute("count");
      count = (value == null) ? 1 : value + 1;
      session.setAttribute("count", count);
    }
    response.setContentType("text/plain; charset=utf-8");
    response.getWriter().println("Bonjour pour la " + count + "-ème fois");
  }
}