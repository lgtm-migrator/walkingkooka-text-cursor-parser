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
import walkingkooka.visit.Visiting;

import java.time.OffsetTime;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class OffsetTimeParserTokenTest extends ValueParserTokenTestCase<OffsetTimeParserToken> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final OffsetTimeParserToken token = this.createToken();

        new FakeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("2");
            }

            @Override
            protected void visit(final OffsetTimeParserToken t) {
                assertSame(token, t);
                b.append("3");
            }
        }.accept(token);
        this.checkEquals("132", b.toString());
    }

    @Override
    public OffsetTimeParserToken createToken(final String text) {
        return OffsetTimeParserToken.with(OffsetTime.MAX, text);
    }

    @Override
    public String text() {
        return "OffsetTime.MAX";
    }

    @Override
    public OffsetTimeParserToken createDifferentToken() {
        return OffsetTimeParserToken.with(OffsetTime.MIN, "OffsetTime.MIN");
    }

    @Override
    public Class<OffsetTimeParserToken> type() {
        return OffsetTimeParserToken.class;
    }
}
