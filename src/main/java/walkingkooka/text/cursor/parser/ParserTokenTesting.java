/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 *
 */

package walkingkooka.text.cursor.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.predicate.Predicates;
import walkingkooka.reflect.BeanPropertiesTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.reflect.TypeNameTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.text.HasTextTesting;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.visit.Visiting;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A mixin interface with tests and helpers to assist testing of {@link ParserToken} implementations.
 *
 * @param <T>
 */
public interface ParserTokenTesting<T extends ParserToken > extends BeanPropertiesTesting,
        HashCodeEqualsDefinedTesting2<T>,
        HasTextTesting,
        TreePrintableTesting,
        ToStringTesting<T>,
        TypeNameTesting<T> {

    @Test
    default void testImplementsEitherParentParserTokenOrLeafParserToken() {
        for (; ; ) {
            final Class<T> type = this.type();
            final boolean leaf = LeafParserToken.class.isAssignableFrom(type);
            final boolean parent = ParentParserToken.class.isAssignableFrom(type);
            if (leaf) {
                assertFalse(
                        parent,
                        () -> "Type " + type.getName() + " must implement either " + LeafParserToken.class.getName() + " or " + ParentParserToken.class.getName() + " but not both"
                );
                break;
            }
            if (parent) {
                break;
            }
            fail("Type " + type.getName() + " must implement either " + LeafParserToken.class.getName() + " or " + ParentParserToken.class.getName());
        }
    }

    @Test
    default void testValueType() {
        for (; ; ) {
            final T token = this.createToken();
            if (token instanceof LeafParserToken) {
                final Object value = ((LeafParserToken<?>) token).value();
                assertFalse(
                        value instanceof Collection,
                        () -> token + " value must not be a Collection but was " + value.getClass() + "=" + value
                );
                break;
            }
            if (token instanceof ParentParserToken) {
                final Object value = ((ParentParserToken) token).value();
                assertTrue(
                        value instanceof Collection,
                        () -> token + " value must be a Collection but was " + value.getClass() + "=" + value
                );
                break;
            }
            fail("ParserToken: " + token + " must implement either " + LeafParserToken.class.getName() + " or " + ParentParserToken.class.getName());
        }
    }

    @Test
    default void testIsSymbol() {
        final String type = this.type().getSimpleName();
        final boolean symbol = type.contains(SYMBOL);

        final T token = this.createToken();

        if (type.contains(WHITESPACE)) {
            this.checkEquals(
                    true,
                    token.isSymbol(),
                    () -> "Token " + token + " is whitespace=true so isSymbol must also be true"
            );
        } else {
            this.checkEquals(
                    symbol,
                    token.isSymbol(),
                    () -> "Token " + token + " name includes " + SYMBOL + " so isSymbol should be true"
            );
        }
    }

    String SYMBOL = "Symbol";

    /**
     * If a type class name includes Whitespace its {@link ParserToken#isWhitespace()} should also return true.
     */
    @Test
    default void testIsWhitespace() {
        final String type = this.type().getSimpleName();
        final boolean whitespace = type.contains(WHITESPACE);

        final T token = this.createToken();
        this.checkEquals(
                whitespace,
                token.isWhitespace(),
                () -> token + " isWhitespace must be true if " + type + " contains " + CharSequences.quote(WHITESPACE)
        );

        if (whitespace) {
            this.checkEquals(
                    whitespace,
                    token.isNoise(),
                    () -> token + " isWhitespace==true, isNoise must also be true"
            );
            this.checkEquals(
                    whitespace,
                    token.isSymbol(),
                    () -> token + " isWhitespace==true, isSymbol must also be true"
            );
        }
    }

    String WHITESPACE = "Whitespace";

    @Test
    default void testWithNullTextFails() {
        assertThrows(NullPointerException.class, () -> this.createToken(null));
    }

    @Test
    default void testText() {
        this.textAndCheck(this.createToken(), this.text());
    }

    @Test
    default void testPublicStaticFactoryMethod() {
        final Class<?> type = this.type();
        final String suffix = ParserToken.class.getSimpleName();

        final String name = type.getSimpleName();
        final String without = Character.toLowerCase(name.charAt("".length())) +
                name.substring("".length() + 1, name.length() - suffix.length());

        String factoryMethodName1 = without;
        for (final String possible : new String[]{"boolean", "byte", "double", "equals", "int", "long", "null", "short"}) {
            if (without.equals(possible)) {
                factoryMethodName1 = without + suffix;
                break;
            }
        }
        final String factoryMethodName = factoryMethodName1;

        final List<Method> publicStaticMethods = Arrays.stream(ParserTokens.class.getMethods())
                .filter(m -> MethodAttributes.STATIC.is(m) && JavaVisibility.PUBLIC == JavaVisibility.of(m))
                .collect(Collectors.toList());

        final List<Method> factoryMethods = publicStaticMethods.stream()
                .filter(m -> m.getName().equals(factoryMethodName) &&
                        m.getReturnType().equals(type))
                .collect(Collectors.toList());

        final String publicStaticMethodsToString = publicStaticMethods.stream()
                .map(Method::toGenericString)
                .collect(Collectors.joining(LineEnding.SYSTEM.toString()));
        this.checkEquals(
                1,
                factoryMethods.size(),
                () -> "Expected only a single factory method called " + CharSequences.quote(factoryMethodName) +
                        " for " + type + " on " + ParserTokens.class.getName() + " but got " + factoryMethods + "\n" + publicStaticMethodsToString
        );
    }

    @Test
    default void testAcceptStartParserTokenSkip() {
        final StringBuilder b = new StringBuilder();
        final List<ParserToken> visited = Lists.array();

        final T token = this.createToken();

        new FakeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                b.append("1");
                visited.add(t);
                return Visiting.SKIP;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("2");
                visited.add(t);
            }
        }.accept(token);
        this.checkEquals("12", b.toString());
        this.checkEquals(Lists.<Object>of(token, token), visited, "visited tokens");
    }

    @Test
    default void testIsNoisyGuess() {
        final T token = this.createToken();
        final String className = token.getClass().getSimpleName();
        this.checkEquals(
                className.contains("Whitespace") | className.contains("Symbol") | className.contains("Comment"),
                token.isNoise()
        );
    }

    @Test
    default void testPropertiesNeverReturnNull() throws Exception {
        this.allPropertiesNeverReturnNullCheck(this.createToken(), Predicates.never());
    }

    @Test
    default void testToString() {
        this.toStringAndCheck(this.createToken(), this.text());
    }

    default T createToken() {
        return this.createToken(this.text());
    }

    String text();

    T createToken(final String text);

    T createDifferentToken();

    // HashCodeEqualityTesting..........................................................................................

    @Override
    default T createObject() {
        return this.createToken();
    }

    // TypeNameTesting .................................................................................................

    @Override
    default String typeNamePrefix() {
        return "";
    }

    @Override
    default String typeNameSuffix() {
        return ParserToken.class.getSimpleName();
    }
}
