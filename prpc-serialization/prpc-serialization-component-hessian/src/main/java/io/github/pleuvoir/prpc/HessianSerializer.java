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
package io.github.pleuvoir.prpc;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import io.github.pleuvoir.prpc.exception.SerializationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Hessian序列化实现，待传输的类必须实现{@link Serializable}接口
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class HessianSerializer implements ISerializer {

  private SerializerFactory serializerFactory = new SerializerFactory();

  @Override
  public byte[] serialize(Object obj) throws SerializationException {
    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    Hessian2Output output = new Hessian2Output(byteArray);
    output.setSerializerFactory(serializerFactory);
    try {
      output.writeObject(obj);
      output.close();
    } catch (IOException e) {
      throw new SerializationException("IOException occurred when Hessian serializer encode!", e);
    }

    return byteArray.toByteArray();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
    Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
    input.setSerializerFactory(this.serializerFactory);
    Object resultObject;
    try {
      resultObject = input.readObject();
      input.close();
    } catch (IOException e) {
      throw new SerializationException("IOException occurred when Hessian serializer decode!", e);
    }
    return (T) resultObject;
  }
}
