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
package walkingkooka.text.cursor.parser.function;

import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.text.cursor.parser.BigIntegerParserToken;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.text.cursor.parser.SequenceParserToken;

import java.util.function.BiFunction;

/**
 * A collection of factory methods to create functions for use by {@link Parsers#
 */
public final class ParserBiFunctions implements PublicStaticHelper {

    /**
     * {@see PrefixedNumberParserTokenBiFunction}
     */
    public static <C extends ParserContext> BiFunction<SequenceParserToken, C, BigIntegerParserToken> prefixedNumber() {
        return PrefixedNumberParserTokenBiFunction.get();
    }

    /**
     * Stop creation.
     */
    private ParserBiFunctions() {
        throw new UnsupportedOperationException();
    }
}
