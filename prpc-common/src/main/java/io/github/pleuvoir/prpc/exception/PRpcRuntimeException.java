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
package io.github.pleuvoir.prpc.exception;

/**
 * 运行时异常
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class PRpcRuntimeException extends RuntimeException {

    public PRpcRuntimeException() {
    }

    public PRpcRuntimeException(String message) {
        super(message);
    }

    public PRpcRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PRpcRuntimeException(Throwable cause) {
        super(cause);
    }

    public PRpcRuntimeException(String message, Object... args) {
        super(String.format(message, args));
    }

}
