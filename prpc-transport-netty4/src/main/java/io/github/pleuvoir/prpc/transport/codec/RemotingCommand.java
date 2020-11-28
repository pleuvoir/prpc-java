package io.github.pleuvoir.prpc.transport.codec;

import lombok.Data;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
public class RemotingCommand {

  // 指令，用于匹配远程命令
  protected int requestCode;

  // 序列化编码
  protected int serialCode;
}
