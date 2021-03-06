/*
 * Copyright 2013-2020 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package brave.baggage;

import brave.propagation.Propagation;

/**
 * This allows configuration plugins to collaborate on building an instance of {@link
 * CorrelationScopeDecorator}.
 *
 * <p>For example, a customizer can {@linkplain CorrelationScopeDecorator.Builder#add(CorrelationScopeConfig)
 * configure a baggage field} without instantiating the {@link CorrelationScopeDecorator.Builder).
 *
 * <p>This also allows one object to customize both {@linkplain BaggagePropagation baggage}
 * and {@link CorrelationScopeDecorator correlation integration}, by implementing both customizer
 * interfaces.
 *
 * <h3>Integration examples</h3>
 *
 * <p>In practice, a dependency injection tool applies a collection of these instances prior to
 * {@link CorrelationScopeDecorator.Builder#build() building the scope instance}. For example, an
 * injected {@code List<CorrelationCustomizer>} parameter to a provider of {@link
 * Propagation.Factory }.
 *
 * <p>Here are some examples, in alphabetical order:
 * <pre><ul>
 *   <li><a href="https://dagger.dev/multibindings.html">Dagger Set Multibindings</a></li>
 *   <li><a href="http://google.github.io/guice/api-docs/latest/javadoc/com/google/inject/multibindings/Multibinder.html">Guice Set Multibinder</a></li>
 *   <li><a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-autowired-annotation">Spring Autowired Collections</a></li>
 * </ul></pre>
 *
 * @see CorrelationScopeConfig
 * @see BaggageCustomizer
 * @since 5.11
 */
public interface CorrelationScopeCustomizer {
  /** Use to avoid comparing against null references */
  CorrelationScopeCustomizer NOOP = new CorrelationScopeCustomizer() {
    @Override public void customize(CorrelationScopeDecorator.Builder builder) {
    }

    @Override public String toString() {
      return "NoopCorrelationScopeCustomizer{}";
    }
  };

  void customize(CorrelationScopeDecorator.Builder builder);
}
