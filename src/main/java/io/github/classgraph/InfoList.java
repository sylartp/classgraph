/*
 * This file is part of ClassGraph.
 *
 * Author: Luke Hutchison
 *
 * Hosted at: https://github.com/classgraph/classgraph
 *
 * --
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Luke Hutchison
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
package io.github.classgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A list of named objects.
 *
 * @param <T>
 *            the element type
 */
class InfoList<T extends HasName> extends ArrayList<T> {
    /**
     * Constructor.
     */
    InfoList() {
        super();
    }

    /**
     * Constructor.
     *
     * @param sizeHint
     *            the size hint
     */
    InfoList(final int sizeHint) {
        super(sizeHint);
    }

    /**
     * Constructor.
     *
     * @param infoCollection
     *            the initial elements.
     */
    InfoList(final Collection<T> infoCollection) {
        super(infoCollection);
    }

    // -------------------------------------------------------------------------------------------------------------

    /**
     * Get the names of all items in this list, by calling {@code getName()} on each item in the list.
     *
     * @return The names of all items in this list, by calling {@code getName()} on each item in the list.
     */
    public List<String> getNames() {
        if (this.isEmpty()) {
            return Collections.emptyList();
        } else {
            final List<String> names = new ArrayList<>(this.size());
            for (final T i : this) {
                names.add(i.getName());
            }
            return names;
        }
    }

    /**
     * Get the String representations of all items in this list, by calling {@code toString()} on each item in the
     * list.
     *
     * @return The String representations of all items in this list, by calling {@code toString()} on each item in
     *         the list.
     */
    public List<String> getAsStrings() {
        if (this.isEmpty()) {
            return Collections.emptyList();
        } else {
            final List<String> toStringVals = new ArrayList<>(this.size());
            for (final T i : this) {
                toStringVals.add(i == null ? "null" : i.toString());
            }
            return toStringVals;
        }
    }

    // -------------------------------------------------------------------------------------------------------------

    /**
     * A list of named objects that can be indexed by name.
     *
     * @param <T>
     *            the element type
     */
    static class MappableInfoList<T extends HasName> extends InfoList<T> {

        /**
         * Constructor.
         */
        MappableInfoList() {
            super();
        }

        /**
         * Constructor.
         *
         * @param sizeHint
         *            the size hint
         */
        MappableInfoList(final int sizeHint) {
            super(sizeHint);
        }

        /**
         * Constructor.
         *
         * @param infoCollection
         *            the initial elements
         */
        MappableInfoList(final Collection<T> infoCollection) {
            super(infoCollection);
        }

        /**
         * Get an index for this list, as a map from the name of each list item (obtained by calling
         * {@code getName()} on each list item) to the list item.
         *
         * @return An index for this list, as a map from the name of each list item (obtained by calling
         *         {@code getName()} on each list item) to the list item.
         */
        public Map<String, T> asMap() {
            final Map<String, T> nameToInfoObject = new HashMap<>();
            for (final T i : this) {
                nameToInfoObject.put(i.getName(), i);
            }
            return nameToInfoObject;
        }

        /**
         * Check if this list contains an item with the given name.
         *
         * @param name
         *            The name to search for.
         * @return true if this list contains an item with the given name.
         */
        public boolean containsName(final String name) {
            for (final T i : this) {
                if (i.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Get the list item with the given name, or null if not found.
         *
         * @param name
         *            The name to search for.
         * @return The list item with the given name, or null if not found.
         */
        public T get(final String name) {
            for (final T i : this) {
                if (i.getName().equals(name)) {
                    return i;
                }
            }
            return null;
        }
    }
}
