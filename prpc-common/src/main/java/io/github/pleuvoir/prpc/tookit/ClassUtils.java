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

import io.github.pleuvoir.prpc.exception.PRpcRuntimeException;

/**
 * 类帮助
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
public class ClassUtils {

    /**
     * 获取类加载器
     * <p>首先尝试获取当前线程的类加载器，若未找到继续获取加载类的类加载器，若还未找到返回系统类加载器
     */
    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        ClassLoader classLoader = clazz.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return ClassLoader.getSystemClassLoader();
    }

    @SuppressWarnings("unchecked")
    public static <A> A cast(Class<A> clazz, Object instance) {
        return (A) instance;
    }

    public static Object newInstanceNoConstructor(String clazzName) {
        try {
            return Class.forName(clazzName).newInstance();
        } catch (Exception e) {
            throw new PRpcRuntimeException(e);
        }
    }

    public static Class<?> forName(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (Exception e) {
            throw new PRpcRuntimeException(e);
        }
    }

}