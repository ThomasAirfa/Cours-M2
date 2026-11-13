package fr.uge.servlet.hellosession;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/hellosession")
public class HelloSession extends HttpServlet {
  private static final String COOKIE_NAME = "MYSESSION";
  private final SessionManager sessions = new SessionManager();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String token = null;
    var cookies = request.getCookies();
    if (cookies != null) {
      for (var c : cookies) {
        if (COOKIE_NAME.equals(c.getName())) {
          token = c.getValue();
          break;
        }
      }
    }
    if (!sessions.isValid(token)) {
      token = sessions.newToken();
      var cookie = new Cookie(COOKIE_NAME, token);
      cookie.setHttpOnly(true);
      cookie.setPath("/");
      response.addCookie(cookie);
    }
    int count = sessions.increment(token);
    response.setContentType("text/plain; charset=utf-8");
    response.getWriter().println("Bonjour pour la " + count + "-ème fois");
  }
}