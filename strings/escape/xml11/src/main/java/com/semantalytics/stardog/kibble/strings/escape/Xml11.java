package com.semantalytics.stardog.kibble.strings.escape;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.apache.commons.text.StringEscapeUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class Xml11 extends AbstractFunction implements StringFunction {

    protected Xml11() {
        super(1, EscapeVocabulary.csv.stringValue());
    }

    private Xml11(final Xml11 xml11) {
        super(xml11);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(StringEscapeUtils.escapeCsv(string));
    }

    @Override
    public Xml11 copy() {
        return new Xml11(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EscapeVocabulary.csv.name();
    }
}