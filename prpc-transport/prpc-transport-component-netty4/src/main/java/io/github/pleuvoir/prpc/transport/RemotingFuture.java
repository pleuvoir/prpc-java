/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.prpc.transport;

import io.github.pleuvoir.prpc.IResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步发送占位
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Data
@Slf4j
public class RemotingFuture {

  private final long messageId;
  private IResponse responseCommand;
  private final Long timeoutMillis;
  private final CountDownLatch countDownLatch = new CountDownLatch(1);
  private final InvokeCallback invokeCallback;
  private volatile boolean sendRequestOK = true;
  private volatile Throwable cause;
  private final long beginTimestamp = System.currentTimeMillis();

  public RemotingFuture(long messageId, Long timeoutMillis, InvokeCallback invokeCallback) {
    super();
    this.messageId = messageId;
    this.timeoutMillis = timeoutMillis;
    this.invokeCallback = invokeCallback;
  }

  /**
   * 阻塞等待返回结果
   */
  public IResponse waitResponse(final long timeoutMillis) {
    try {
      this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
    } catch (InterruptedException ignore) {
    }
    return this.responseCommand;
  }

  /**
   * 完成结果响应，一般是正常流程使用 <b>注意：此操作会结束 {@link #waitResponse(long)}的等待</b>
   */
  public void completeResponse(final IResponse responseCommand) {
    this.responseCommand = responseCommand;
    this.countDownLatch.countDown();
  }

  /**
   * 执行回调
   */
  public void onReceiveResponse() {
    try {
      this.invokeCallback.onReceiveResponse(this);
    } catch (Throwable e) {
      log.error("executeInvokeCallback error, ", e);
    }
  }


  /**
   * 执行回调
   */
  public void onRequestException() {
    try {
      this.invokeCallback.onRequestException(this);
    } catch (Throwable e) {
      log.error("executeInvokeCallback error, ", e);
    }
  }


}
