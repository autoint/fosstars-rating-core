package com.sap.sgs.phosphor.fosstars.nvd.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Defines a vulnerability in the NVD data feed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cve",
    "configurations",
    "impact",
    "publishedDate",
    "lastModifiedDate"
})
// the properties below are ignored because they are not used
// that saves a bit of memory
// when they become necessary, then can be enabled
@JsonIgnoreProperties({
    "lastModifiedDate"
})
public class NvdEntry {

  @JsonProperty("cve")
  private CVE cve;

  /**
   * Defines the set of product configurations for a NVD applicability statement.
   */
  @JsonProperty("configurations")
  private Configurations configurations;

  /**
   * Impact scores for a vulnerability as found on NVD.
   */
  @JsonProperty("impact")
  private Impact impact;

  @JsonProperty("publishedDate")
  private String publishedDate;

  @JsonProperty("lastModifiedDate")
  private String lastModifiedDate;

  @JsonProperty("cve")
  public CVE getCve() {
    return cve;
  }

  @JsonProperty("cve")
  public void setCve(CVE cve) {
    this.cve = cve;
  }

  /**
   * Defines the set of product configurations for a NVD applicability statement.
   */
  @JsonProperty("configurations")
  public Configurations getConfigurations() {
    return configurations;
  }

  /**
   * Impact scores for a vulnerability as found on NVD.
   */
  @JsonProperty("impact")
  public Impact getImpact() {
    return impact;
  }

  @JsonProperty("publishedDate")
  public String getPublishedDate() {
    return publishedDate;
  }
}
