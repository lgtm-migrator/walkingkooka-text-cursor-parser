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

public final class ToStringBuilderAppenderShortScalarTest extends ToStringBuilderAppenderScalarTestCase<ToStringBuilderAppenderShortScalar, Short> {

    @Test
    public void testValueHexWholeNumber() {
        final ToStringBuilder b = this.builder();
        b.enable(ToStringBuilderOption.HEX_WHOLE_NUMBERS);

        b.value((short) 1);

        this.buildAndCheck(b, "0001");
    }

    @Override
    void append(final ToStringBuilder builder, final Short value) {
        builder.append((short) value);
    }

    @Override
    void value(final ToStringBuilder builder, final Short value) {
        builder.value((short) value);
    }

    @Override
    Short defaultValue() {
        return 0;
    }

    @Override
    Short value1() {
        return 123;
    }

    @Override
    Short value2() {
        return 456;
    }

    @Override
    String defaultAppendToString(final Short value) {
        return this.defaultValueToString(value);
    }

    @Override
    String defaultValueToString(final Short value) {
        return "0";
    }

    @Override
    String value1ToString() {
        return "123";
    }

    @Override
    String value2ToString() {
        return "456";
    }

    // ClassTesting......................................................................................................

    @Override
    public Class<ToStringBuilderAppenderShortScalar> type() {
        return ToStringBuilderAppenderShortScalar.class;
    }
}
