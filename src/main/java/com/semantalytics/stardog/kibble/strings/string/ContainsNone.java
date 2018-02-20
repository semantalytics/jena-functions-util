package com.semantalytics.stardog.kibble.strings.string;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.apache.commons.lang3.StringUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class ContainsNone extends AbstractFunction implements StringFunction {

    protected ContainsNone() {
        super(2, StringVocabulary.containsNone.stringValue());
    }

    private ContainsNone(final ContainsNone containsNone) {
        super(containsNone);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
      final String string = assertStringLiteral(values[0]).stringValue();
      final String invalidChars = assertStringLiteral(values[1]).stringValue();
      
      return literal(StringUtils.containsNone(string, invalidChars));
    }

    @Override
    public ContainsNone copy() {
        return new ContainsNone(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.containsNone.name();
    }
}
