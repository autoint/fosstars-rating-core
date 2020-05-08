package com.sap.sgs.phosphor.fosstars.nvd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.sap.sgs.phosphor.fosstars.data.github.NvdEntryMatcher;
import com.sap.sgs.phosphor.fosstars.nvd.data.NvdEntry;
import com.sap.sgs.phosphor.fosstars.tool.github.GitHubProject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class NVDTest {

  @Test
  public void get() throws IOException {
    TestNVD nvd = new TestNVD();
    try (InputStream content = getClass().getResourceAsStream("NVD_part.json")) {
      nvd.add("file.json", content);
      assertFalse(nvd.downloadFailed());
      nvd.parse();
      Optional<NvdEntry> something = nvd.get("CVE-2020-9547");
      assertTrue(something.isPresent());
      NvdEntry nvdEntry = something.get();
      assertNotNull(nvdEntry);
      assertEquals("CVE-2020-9547", nvdEntry.getCve().getCveDataMeta().getId());
      assertNotNull(nvdEntry.getConfigurations());
      assertEquals(1, nvdEntry.getConfigurations().getNodes().size());
    }
  }

  @Test
  public void find() throws IOException {
    TestNVD nvd = new TestNVD();
    try (InputStream content = getClass().getResourceAsStream("NVD_part.json")) {
      nvd.add("file.json", content);
      nvd.parse();

      List<NvdEntry> entries = nvd.search(
          NvdEntryMatcher.entriesFor(
              new GitHubProject("odata4j_project", "odata4j")));
      assertNotNull(entries);

      assertEquals(1, entries.size());

      entries = nvd.search(
          NvdEntryMatcher.entriesFor(
              new GitHubProject("not_existing", "something")));
      assertNotNull(entries);
      assertEquals(0, entries.size());
    }
  }
}
