package com.semantalytics.stardog.kibble.strings.string;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.apache.commons.lang3.StringUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class ContainsIgnoreCase extends AbstractFunction implements StringFunction {

    protected ContainsIgnoreCase() {
        super(2, StringVocabulary.containsIgnoreCase.stringValue());
    }

    private ContainsIgnoreCase(final ContainsIgnoreCase containsIgnoreCase) {
        super(containsIgnoreCase);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String sequence = assertStringLiteral(values[0]).stringValue();
        final String searchSequence = assertStringLiteral(values[1]).stringValue();

        return literal(StringUtils.containsIgnoreCase(sequence, searchSequence));
    }

    @Override
    public ContainsIgnoreCase copy() {
        return new ContainsIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.containsIgnoreCase.name();
    }
}
