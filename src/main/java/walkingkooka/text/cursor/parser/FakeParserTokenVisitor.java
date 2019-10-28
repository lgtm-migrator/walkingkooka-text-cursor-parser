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

import walkingkooka.test.Fake;
import walkingkooka.visit.Visiting;

public class FakeParserTokenVisitor extends ParserTokenVisitor implements Fake {

    @Override
    protected void visit(final LocalDateParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LocalDateTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LocalTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LongParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final OffsetDateTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final OffsetTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Visiting startVisit(final RepeatedParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final RepeatedParserToken token) {
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SequenceParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final SequenceParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final SingleQuotedParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final SignParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final StringParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ZonedDateTimeParserToken token) {
        throw new UnsupportedOperationException();
    }
}
