package fr.uge.servlet.hellosession;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionManager {
  private final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

  public String newToken() {
    var token = UUID.randomUUID().toString();
    counters.put(token, new AtomicInteger());
    return token;
  }

  public boolean isValid(String token) {
    return token != null && counters.containsKey(token);
  }

  public int increment(String token) {
    return counters.get(token).incrementAndGet();
  }
}