/*
 * This file is part of FastClasspathScanner.
 *
 * Author: Luke Hutchison
 *
 * Hosted at: https://github.com/lukehutch/fast-classpath-scanner
 *
 * --
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Luke Hutchison
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.fastclasspathscanner;

import io.github.fastclasspathscanner.utils.ClasspathOrder;
import io.github.fastclasspathscanner.utils.LogNode;

/**
 * A ClassLoader handler.
 *
 * <p>
 * Custom ClassLoaderHandlers can be registered by listing their fully-qualified class name in the file:
 * META-INF/services/io.github.fastclasspathscanner.scanner.classloaderhandler.ClassLoaderHandler
 *
 * <p>
 * However, if you do create a custom ClassLoaderHandler, please consider submitting a patch upstream for
 * incorporation into FastClasspathScanner.
 */
public interface ClassLoaderHandler {
    /**
     * The fully-qualified names of handled classloader classes.
     * 
     * @return The names of ClassLoaders that this ClassLoaderHandler can handle.
     */
    public String[] handledClassLoaders();

    /**
     * The delegation order configuration for a given ClassLoader instance (this is usually PARENT_FIRST for most
     * ClassLoaders, but this can be overridden by some ClassLoaders, e.g. WebSphere).
     */
    public enum DelegationOrder {
        /** Delegate to parent before handling in child. */
        PARENT_FIRST,
        /** Handle classloading in child before delegating to parent. */
        PARENT_LAST;
    }

    /**
     * The delegation order configuration for a given ClassLoader instance (this is usually PARENT_FIRST for most
     * ClassLoaders, since you don't generally want to be able to override system classes with user classes, but
     * this can be overridden by some ClassLoaders, e.g. WebSphere).
     * 
     * @param classLoaderInstance
     *            The ClassLoader to get the delegation order for.
     * @return The delegation order for the given ClassLoader.
     */
    public DelegationOrder getDelegationOrder(ClassLoader classLoaderInstance);

    /**
     * Determine if a given ClassLoader can be handled (meaning that its classpath elements can be extracted from
     * it), and if it can, extract the classpath elements from the ClassLoader and register them with the
     * ClasspathFinder using classpathFinder.addClasspathElement(pathElement) or
     * classpathFinder.addClasspathElements(path).
     *
     * @param scanSpec
     *            the scanning specification, in case it is needed, e.g. this could be used to reduce the number of
     *            classpath elements returned in cases where it is very costly for a given classloader to return the
     *            entire classpath. (The ScanSpec can be safely ignored, however, and the returned paths will be
     *            filtered by FastClasspathScanner.)
     * @param classLoader
     *            The ClassLoader class to attempt to handle. If you can't directly use instanceof (because you are
     *            using introspection so that your ClassLoaderHandler implementation can be added to the upstream
     *            FastClasspathScanner project), you should iterate through the ClassLoader's superclass lineage to
     *            ensure subclasses of the target ClassLoader are correctly detected.
     * @param classpathOrderOut
     *            The ClasspathOrder to register any discovered classpath elements with.
     * @param log
     *            A logger instance -- if this is non-null, write debug information using log.log("message").
     * @throws Exception
     *             If anything goes wrong while fetching classpath elements.
     */
    public void handle(ScanSpec scanSpec, final ClassLoader classLoader, final ClasspathOrder classpathOrderOut,
            LogNode log) throws Exception;
}