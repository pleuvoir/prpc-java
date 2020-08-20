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
package io.github.pleuvoir.test.integration.tookit;

import io.github.pleuvoir.prpc.tookit.ClassUtils;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;
import org.junit.Test;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ClassUtilsTest {

    @Test
    public void findAllClassRootFilesPathTest() throws IOException {
        Set<Path> filesPath = ClassUtils.findAllClassRootFilesPath("META-INF/contracts");
        for (Path path : filesPath) {
            System.out.println(path);
        }
    }

    @Test
    public void findAllClassRootDirResourcesTest() throws IOException {
        Set<URL> filesPath = ClassUtils.findAllClassRootDirResources("META-INF/contracts");
        for (URL path : filesPath) {
            System.out.println(path);
        }
    }

    @Test
    public void listFileTest() throws IOException {
        Set<URL> filesPath = ClassUtils.findAllClassRootDirResources("META-INF/contracts");
        for (URL folder : filesPath) {

            Set<Path> paths = ClassUtils.listFile(folder);
            System.out.println(String.format("当前目录下 %s ", folder.getPath()));
            for (Path item : paths) {
                System.out.println(item);
            }
        }
    }


    @Test
    public void listFilePatternTest() throws IOException {
        Set<URL> filesPath = ClassUtils.findAllClassRootDirResources("config");
        for (URL folder : filesPath) {

            Set<Path> paths = ClassUtils.listFile(folder, "*.factories");
            System.out.println(String.format("当前目录下 %s ", folder.getPath()));
            for (Path item : paths) {
                System.out.println(item);
            }
        }
    }


}
