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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.Sets;

import java.util.Optional;

public final class ToStringBuilderAppenderIterableVectorTest extends ToStringBuilderAppenderVectorTestCase<ToStringBuilderAppenderIterableVector, Iterable<?>> {

    private final static Object VALUE = "xyz123";

    // Iterable.........................................................................................................

    @Test
    public final void testLabelValueIncludesDefault() {
        final ToStringBuilder b = this.builder();
        b.label(LABEL1);
        b.value(this.iterable(false, (byte) 0, (short) 0, 0, 0L, 0.0f, 0.0, ""));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + false + VALUE_SEPARATOR +
                        (byte) 0 + VALUE_SEPARATOR +
                        (short) 0 + VALUE_SEPARATOR +
                        0 + VALUE_SEPARATOR +
                        0L + VALUE_SEPARATOR +
                        0.0f + VALUE_SEPARATOR +
                        0.0 + VALUE_SEPARATOR +
                        "");
    }

    @Test
    public final void testQuotesValueCharSequence() {
        final ToStringBuilder b = this.builder();
        b.enable(ToStringBuilderOption.QUOTE);
        b.value(this.iterable("ABC", 'z'));

        this.buildAndCheck(b, "\"ABC\"" + VALUE_SEPARATOR + "\'z\'");
    }

    @Test
    public final void testLabelEmptyIterableLabelIterable() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(this.iterable(1, 2, 3));

        b.label(LABEL2);
        b.value(this.iterable());

        b.label(LABEL3);
        b.value(this.iterable(7, 8, 9));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + 1 + VALUE_SEPARATOR + 2 + VALUE_SEPARATOR + 3 + SEPARATOR +
                        LABEL3 + LABEL_SEPARATOR + 7 + VALUE_SEPARATOR + 8 + VALUE_SEPARATOR + 9);
    }

    // List..............................................................................................................

    @Test
    public final void testLabelEmptyList() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(Lists.empty());

        b.label(LABEL2);
        b.value(VALUE);


        this.buildAndCheck(b,
                LABEL2 + LABEL_SEPARATOR + VALUE);
    }

    @Test
    public final void testLabelList() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(Lists.of(LABEL2, VALUE));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + LABEL2 + VALUE_SEPARATOR + VALUE);
    }

    @Test
    public final void testLabelListLabelEmptyListLabelList() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(Lists.of(1, 2, 3));

        b.label(LABEL2);
        b.value(Lists.empty());

        b.label(LABEL3);
        b.value(Lists.of(7, 8, 9));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + 1 + VALUE_SEPARATOR + 2 + VALUE_SEPARATOR + 3 + SEPARATOR +
                        LABEL3 + LABEL_SEPARATOR + 7 + VALUE_SEPARATOR + 8 + VALUE_SEPARATOR + 9);
    }

    // Object[]..............................................................................................................

    @Test
    public final void testLabelEmptyObject() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(array());

        b.label(LABEL2);
        b.value(VALUE);


        this.buildAndCheck(b,
                LABEL2 + LABEL_SEPARATOR + VALUE);
    }

    @Test
    public final void testLabelObject() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(array(LABEL2, VALUE));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + LABEL2 + VALUE_SEPARATOR + VALUE);
    }

    @Test
    public final void testLabelObjectLabelEmptyObjectLabelObject() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(array(1, 2, 3));

        b.label(LABEL2);
        b.value(array());

        b.label(LABEL3);
        b.value(array(7, 8, 9));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + 1 + VALUE_SEPARATOR + 2 + VALUE_SEPARATOR + 3 + SEPARATOR +
                        LABEL3 + LABEL_SEPARATOR + 7 + VALUE_SEPARATOR + 8 + VALUE_SEPARATOR + 9);
    }

    private Object[] array(final Object... elements) {
        return elements;
    }

    // Set..............................................................................................................

    @Test
    public final void testLabelSetLabelEmptySetLabelSet() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(Sets.of(1, 2, 3));

        b.label(LABEL2);
        b.value(Sets.empty());

        b.label(LABEL3);
        b.value(Sets.of(7, 8, 9));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + 1 + VALUE_SEPARATOR + 2 + VALUE_SEPARATOR + 3 + SEPARATOR +
                        LABEL3 + LABEL_SEPARATOR + 7 + VALUE_SEPARATOR + 8 + VALUE_SEPARATOR + 9);
    }

    // Map..............................................................................................................

    @Test
    public final void testLabelEmptyMap() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(Maps.empty());

        b.label(LABEL2);
        b.value(VALUE);


        this.buildAndCheck(b,
                LABEL2 + LABEL_SEPARATOR + VALUE);
    }

    @Test
    public final void testLabelMap() {
        final ToStringBuilder b = this.builder();

        b.label(LABEL1);
        b.value(Maps.of(LABEL2, VALUE));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR + LABEL2 + LABEL_SEPARATOR + VALUE);
    }

    @Test
    public void testLabelMapMultipleEntries() {
        final ToStringBuilder b = this.builder();
        b.label(LABEL1);
        b.value(Maps.of("A", 1, "B", 2, "C", 3));

        this.buildAndCheck(b, LABEL1 + LABEL_SEPARATOR + "A" + LABEL_SEPARATOR + 1 + VALUE_SEPARATOR + "B" + LABEL_SEPARATOR + 2 + VALUE_SEPARATOR + "C" + LABEL_SEPARATOR + 3);
    }

    @Test
    public final void testLabelMapLabelEmptyMapLabelMap() {
        final ToStringBuilder b = this.builder();

        final String key1 = "key1";
        final Object value1 = 1;

        final String key2 = "key2";
        final Object value2 = 2;

        final String key3 = "key3";
        final Object value3 = 3;

        b.label(LABEL1);
        b.value(Maps.of(key1, value1, key2, value2));

        b.label(LABEL2);
        b.value(Maps.empty());

        b.label(LABEL3);
        b.value(Maps.of(key3, value3));

        this.buildAndCheck(b,
                LABEL1 + LABEL_SEPARATOR +
                        key1 + LABEL_SEPARATOR + value1 + VALUE_SEPARATOR +
                        key2 + LABEL_SEPARATOR + value2 + SEPARATOR +
                        LABEL3 + LABEL_SEPARATOR +
                        key3 + LABEL_SEPARATOR + value3);
    }

    // more.............................................................................................................

    @Test
    public void testValueIncludesDefault() {
        final ToStringBuilder b = this.builder();
        b.label(LABEL1);
        b.value(new Object[]{false, (byte) 0, (short) 0, 0, 0L, 0.0f, 0.0, ""});

        this.buildAndCheck(b, LABEL1 + LABEL_SEPARATOR +
                false + VALUE_SEPARATOR +
                (byte) 0 + VALUE_SEPARATOR +
                (short) 0 + VALUE_SEPARATOR +
                0 + VALUE_SEPARATOR +
                0L + VALUE_SEPARATOR +
                0.0f + VALUE_SEPARATOR +
                0.0 + VALUE_SEPARATOR +
                "");
    }

    @Test
    public void testBooleanWrapperArrayEnabledOneZeroIgnored() {
        final ToStringBuilder b = this.builder();
        b.enable(ToStringBuilderOption.ONE_AND_ZERO_BOOLEAN_ARRAYS);

        b.label(LABEL1);
        b.value(new Boolean[]{true, true, false, false, true});

        this.buildAndCheck(b, LABEL1 + LABEL_SEPARATOR + true + VALUE_SEPARATOR + true + VALUE_SEPARATOR + false + VALUE_SEPARATOR + false + VALUE_SEPARATOR + true);
    }

    @Test
    public void testQuotes() {
        final ToStringBuilder b = this.builder();
        b.enable(ToStringBuilderOption.QUOTE);
        b.value(new Object[]{"ABC", 'z'});

        this.buildAndCheck(b, "\"ABC\"" + VALUE_SEPARATOR + "\'z\'");
    }

    @Test
    public void testSurroundOptionalQuoted() {
        final ToStringBuilder b = this.builder();
        b.enable(ToStringBuilderOption.QUOTE);

        b.surroundValues("((", "))");
        b.value(new Object[]{Optional.of("1a"), 2});

        this.buildAndCheck(b, "((\"1a\"" + VALUE_SEPARATOR + "2))");
    }

    @Test
    public void testSurroundOptionalUnquoted() {
        final ToStringBuilder b = this.builder();
        b.disable(ToStringBuilderOption.QUOTE);

        b.surroundValues("((", "))");
        b.value(new Object[]{Optional.of("1a"), 2});

        this.buildAndCheck(b, "((1a" + VALUE_SEPARATOR + "2))");
    }

    @Test
    public void testSurroundBoolean() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{true, false});

        this.buildAndCheck(b, "((" + true + VALUE_SEPARATOR + false + "))");
    }

    @Test
    public void testSurroundByte() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{Byte.valueOf("0"), Byte.MAX_VALUE});

        this.buildAndCheck(b, "((" + 0 + VALUE_SEPARATOR + Byte.MAX_VALUE + "))");
    }

    @Test
    public void testSurroundShort() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{Short.valueOf("0"), Short.MAX_VALUE});

        this.buildAndCheck(b, "((" + 0 + VALUE_SEPARATOR + Short.MAX_VALUE + "))");
    }

    @Test
    public void testSurroundInteger() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{Integer.valueOf("0"), Integer.MAX_VALUE});

        this.buildAndCheck(b, "((" + 0 + VALUE_SEPARATOR + Integer.MAX_VALUE + "))");
    }

    @Test
    public void testSurroundLong() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{0L, Long.MAX_VALUE});

        this.buildAndCheck(b, "((" + 0 + VALUE_SEPARATOR + Long.MAX_VALUE + "))");
    }

    @Test
    public void testSurroundFloat() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{0f, 1.25f});

        this.buildAndCheck(b, "((" + 0f + VALUE_SEPARATOR + 1.25f + "))");
    }

    @Test
    public void testSurroundDouble() {
        final ToStringBuilder b = this.builder();
        b.surroundValues("((", "))");
        b.value(new Object[]{0.0, 1.25});

        this.buildAndCheck(b, "((" + 0.0 + VALUE_SEPARATOR + 1.25 + "))");
    }

    @Override
    final Iterable<?> defaultValue() {
        return null;
    }

    @Override
    final String defaultValueToString(final Iterable<?> value) {
        return "null";
    }

    @Override
    final Iterable<?> value1() {
        return this.iterable(1);
    }

    @Override
    final Iterable<?> value2() {
        return this.iterable(1, 22, "3abc");
    }

    private Iterable<?> iterable(final Object... values) {
        return Lists.of(values);
    }

    @Override
    void append(final ToStringBuilder builder, final Iterable<?> value) {
        builder.append(value);
    }

    @Override
    void value(final ToStringBuilder builder, final Iterable<?> value) {
        builder.value(value);
    }

    @Override final String value1ToString() {
        return "1";
    }

    @Override final String value2ToString(final String separator) {
        return 1 + separator + 22 + separator + "3abc";
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ToStringBuilderAppenderIterableVector> type() {
        return ToStringBuilderAppenderIterableVector.class;
    }
}
