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
package io.github.pleuvoir.prpc.tookit;

import io.github.pleuvoir.prpc.contract.DefaultContractFactory;
import io.github.pleuvoir.prpc.contract.DefaultContractFactorySingleton;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class DefaultContractFactorySingletonTest {

    @Test
    public void test() throws Exception {
        DefaultContractFactorySingleton singleton = DefaultContractFactorySingleton.INSTANCE;

        DefaultContractFactory contractFactory = new DefaultContractFactory();
        contractFactory.load();


        singleton.set(contractFactory);

        DefaultContractFactory instance = singleton.getInstance();

        System.out.println(instance);
    }
}
