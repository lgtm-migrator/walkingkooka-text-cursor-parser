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

package walkingkooka;

import org.junit.jupiter.api.Test;

public final class ToStringBuilderAppenderCharScalarTest extends ToStringBuilderAppenderScalarTestCase<ToStringBuilderAppenderCharScalar, Character> {

    @Test
    public void testValueWithQuotes() {
        final ToStringBuilder b = ToStringBuilder.empty();
        b.enable(ToStringBuilderOption.QUOTE);
        b.value('a');
        b.label(LABEL1);
        b.value('z');

        this.buildAndCheck(b, "'a' " + LABEL1 + "='z'");
    }

    @Test
    public void testValueWithEscape() {
        final ToStringBuilder b = ToStringBuilder.empty();
        b.enable(ToStringBuilderOption.QUOTE);
        b.enable(ToStringBuilderOption.ESCAPE);

        b.value('\n');
        b.label(LABEL1);
        b.value('z');

        this.buildAndCheck(b, "'\\n' " + LABEL1 + "='z'");
    }

    @Override
    void append(final ToStringBuilder builder, final Character value) {
        builder.append((char) value);
    }

    @Override
    void value(final ToStringBuilder builder, final Character value) {
        builder.value((char) value);
    }

    @Override
    Character defaultValue() {
        return 0;
    }

    @Override
    Character value1() {
        return 'a';
    }

    @Override
    Character value2() {
        return 'z';
    }

    @Override
    String defaultAppendToString(final Character value) {
        return this.defaultValueToString(value);
    }

    @Override
    String defaultValueToString(final Character value) {
        return "\0";
    }

    @Override
    String value1ToString() {
        return "a";
    }

    @Override
    String value2ToString() {
        return "z";
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ToStringBuilderAppenderCharScalar> type() {
        return ToStringBuilderAppenderCharScalar.class;
    }
}
