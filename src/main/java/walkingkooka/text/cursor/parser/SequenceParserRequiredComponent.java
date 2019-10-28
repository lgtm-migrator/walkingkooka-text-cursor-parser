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

import walkingkooka.text.cursor.TextCursor;

import java.util.Optional;

final class SequenceParserRequiredComponent<C extends ParserContext> extends SequenceParserComponent<C> {

    static <C extends ParserContext> SequenceParserRequiredComponent<C> with(final Parser<C> parser) {
        return new SequenceParserRequiredComponent<>(parser);
    }

    private SequenceParserRequiredComponent(final Parser<C> parser) {
        super(parser);
    }

    @Override
    final Optional<ParserToken> parse(final TextCursor cursor, final C context) {
        return this.parser.parse(cursor, context);
    }

    @Override
    boolean abortIfMissing() {
        return true;
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SequenceParserRequiredComponent;
    }

    @Override
    public final String toString() {
        return this.parser.toString();
    }
}
