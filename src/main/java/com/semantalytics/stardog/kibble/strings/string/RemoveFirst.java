package com.semantalytics.stardog.kibble.strings.string;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.apache.commons.lang3.StringUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public final class RemoveFirst extends AbstractFunction implements StringFunction {

    protected RemoveFirst() {
        super(2, StringVocabulary.removeFirst.stringValue());
    }

    private RemoveFirst(final RemoveFirst removeFirst) {
        super(removeFirst);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();
        final String regex = assertStringLiteral(values[1]).stringValue();

        return literal(StringUtils.removeFirst(string, regex));
    }

    @Override
    public RemoveFirst copy() {
        return new RemoveFirst(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.removeFirst.name();
    }
}
