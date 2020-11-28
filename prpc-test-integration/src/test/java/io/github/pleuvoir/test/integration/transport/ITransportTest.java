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
package io.github.pleuvoir.test.integration.transport;

import io.github.pleuvoir.prpc.contract.DefaultContractFactory;
import io.github.pleuvoir.prpc.transport.ITransport;
import io.github.pleuvoir.test.integration.serialization.ISerializerTest;
import org.junit.Test;

/**
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ITransportTest {


    @Test
    public void load() throws Exception {
        DefaultContractFactory factory = new DefaultContractFactory();
        factory.setLocation(DefaultContractFactory.DEFAULT_CONTRACT_DIRECTORY);
        factory.load();
        factory.load();
        ITransport mock1 = factory.getOrEmpty(ITransport.class, "mock1");
        System.out.println(mock1.echo("hello"));
    }

}
