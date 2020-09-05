package io.github.pleuvoir.prpc;

import lombok.Getter;
import lombok.Setter;

/**
 * URL domain
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */

public class URL {

  @Getter
  @Setter
  private String ip;

  @Getter
  @Setter
  private Integer port;


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUri() {
    return this.ip + ":" + this.port;
  }
}
