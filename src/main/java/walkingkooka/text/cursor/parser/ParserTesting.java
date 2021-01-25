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

import walkingkooka.datetime.DateTimeContext;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.test.Testing;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursorSavePoint;
import walkingkooka.text.cursor.TextCursors;

import java.math.MathContext;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Mixin that includes numerous helpers to assist parsing and verifying the outcome for success and failures.
 */
public interface ParserTesting extends Testing {

    // parseAndCheck....................................................................................................

    default <CC extends ParserContext> TextCursor parseAndCheck(final Parser<CC> parser,
                                                                final CC context,
                                                                final String cursorText,
                                                                final ParserToken token,
                                                                final String text) {
        return this.parseAndCheck(parser, context, cursorText, token, text, "");
    }

    default <CC extends ParserContext> TextCursor parseAndCheck(final Parser<CC> parser,
                                                                final CC context,
                                                                final String cursorText,
                                                                final ParserToken token,
                                                                final String text,
                                                                final String textAfter) {
        return this.parseAndCheck(parser, context, TextCursors.charSequence(cursorText), token, text, textAfter);
    }

    default <CC extends ParserContext> TextCursor parseAndCheck(final Parser<CC> parser,
                                                                final CC context,
                                                                final TextCursor cursor,
                                                                final ParserToken token,
                                                                final String text) {
        return this.parseAndCheck(parser, context, cursor, Optional.of(token), text, "");
    }

    default <CC extends ParserContext> TextCursor parseAndCheck(final Parser<CC> parser,
                                                                final CC context,
                                                                final TextCursor cursor,
                                                                final ParserToken token,
                                                                final String text,
                                                                final String textAfter) {
        return this.parseAndCheck(parser, context, cursor, Optional.of(token), text, textAfter);
    }

    default <CC extends ParserContext> TextCursor parseAndCheck(final Parser<CC> parser,
                                                                final CC context,
                                                                final TextCursor cursor,
                                                                final Optional<ParserToken> token,
                                                                final String text,
                                                                final String textAfter) {
        Objects.requireNonNull(token, "token");
        Objects.requireNonNull(text, "text");

        final TextCursorSavePoint before = cursor.save();
        final Optional<ParserToken> result = this.parse(parser, cursor, context);

        final CharSequence consumed = before.textBetween();

        final TextCursorSavePoint after = cursor.save();
        cursor.end();

        final String textRemaining = after.textBetween().toString();
        if (false == token.equals(result)) {
            checkEquals(
                    token,
                    result,
                    () -> "text:\n" + CharSequences.quoteAndEscape(consumed.length() == 0 ?
                            after.textBetween() :
                            consumed) + "\nunconsumed text:\n" + textRemaining
            );
        }

        assertEquals(text, consumed, "incorrect consumed text");
        assertEquals(text, result.map(ParserToken::text).orElse(""), "token consume text is incorrect");
        assertEquals(textAfter, textRemaining, "Incorrect text after match");

        after.restore();
        return cursor;
    }

    // parseFailAndCheck................................................................................................

    default <CC extends ParserContext> TextCursor parseFailAndCheck(final Parser<CC> parser,
                                                                    final CC context,
                                                                    final String cursorText) {
        return this.parseFailAndCheck(parser, context, TextCursors.charSequence(cursorText));
    }

    default <CC extends ParserContext> TextCursor parseFailAndCheck(final Parser<CC> parser,
                                                                    final CC context,
                                                                    final TextCursor cursor) {
        final TextCursorSavePoint before = cursor.save();
        final Optional<ParserToken> result = this.parse(parser, cursor, context);
        if(result.isPresent()) {
            final TextCursorSavePoint after = cursor.save();

            this.checkEquals(
                    null,
                    result.orElse(null),
                    () -> "Expected no token from parsing text consumed:\n" + before.textBetween() + "\ntext left: " + after.textBetween()
            );
        }
        return cursor;
    }

    // asserting ParserToken with pretty dump support...................................................................

    default void checkEquals(final Optional<? extends ParserToken> expected,
                             final Optional<? extends ParserToken> actual,
                             final Supplier<String> message) {
        assertEquals(
                ParserTestingPrettyDumper.dump(expected.orElse(null)),
                ParserTestingPrettyDumper.dump(actual.orElse(null)),
                message
        );
    }

    default void checkEquals(final List<ParserToken> expected,
                             final List<ParserToken> actual,
                             final Supplier<String> message) {
        assertEquals(
                expected.stream().map(ParserTestingPrettyDumper::dump).collect(Collectors.joining()),
                actual.stream().map(ParserTestingPrettyDumper::dump).collect(Collectors.joining()),
                message
        );
    }

    default void checkEquals(final ParserToken expected,
                             final ParserToken actual,
                             final Supplier<String> message) {
        assertEquals(
                ParserTestingPrettyDumper.dump(expected),
                ParserTestingPrettyDumper.dump(actual),
                message
        );
    }

    // parseThrowsEndOfText.............................................................................................

    default <CC extends ParserContext> void parseThrowsEndOfText(final Parser<CC> parser,
                                                                 final CC context,
                                                                 final String cursorText,
                                                                 final int column,
                                                                 final int row) {
        final TextCursor cursor = TextCursors.charSequence(cursorText);
        cursor.end();

        this.parseThrows(parser, context, cursor, endOfText(column, row));
    }

    default <CC extends ParserContext> void parseThrows(final Parser<CC> parser,
                                                        final CC context,
                                                        final TextCursor cursor,
                                                        final String messagePart) {
        final ParserException expected = assertThrows(ParserException.class, () -> this.parse(parser, cursor, context));

        final String message = expected.getMessage();
        assertEquals(true, message.contains(messagePart), () -> "Message: " + message + " missing " + messagePart);
    }

    // parse............................................................................................................

    default <CC extends ParserContext> Optional<ParserToken> parse(final Parser<CC> parser,
                                                                   final TextCursor cursor,
                                                                   final CC context) {
        Objects.requireNonNull(parser, "parser");
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(cursor, "cursor");

        final Optional<ParserToken> result = parser.parse(cursor, context);
        assertNotNull(result, () -> "parser " + parser + " returned null result");
        return result;
    }

    default DateTimeContext dateTimeContext() {
        return DateTimeContexts.locale(Locale.ENGLISH, 20);
    }

    default DecimalNumberContext decimalNumberContext() {
        return DecimalNumberContexts.american(MathContext.DECIMAL32);
    }

    default String endOfText(final int column, final int row) {
        return "End of text at (" + column + "," + row + ")";
    }
}
