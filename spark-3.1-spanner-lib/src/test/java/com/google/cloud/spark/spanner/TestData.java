package com.google.cloud.spark;

import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TestData {
  public static List<String> initialDDL = createInitialDDL();

  private TestData() {}

  private static List<String> createInitialDDL() {
    String initialDDL = mustReadResource("/db/populate_ddl.sql");
    String[] splits = initialDDL.trim().split(";");
    List<String> stmts = new ArrayList<>();
    for (String stmt : splits) {
      stmt = stmt.trim();
      if (stmt != "" && stmt != "\n") {
        stmts.add(stmt);
      }
    }
    return stmts;
  }

  private static String mustReadResource(String path) {
    try (InputStream stream = TestData.class.getResourceAsStream(path)) {
      String data = CharStreams.toString(new InputStreamReader(Objects.requireNonNull(stream)));
      if (data == null || data.length() == 0) {
        throw new RuntimeException(path + " has no content");
      }
      return data;
    } catch (IOException e) {
      throw new RuntimeException("failed to read resource " + path, e);
    }
  }
}