package de.leidenheit.core.constraint;

import java.util.List;
import java.util.Map;

// V === Variable
// D === Domain
public abstract class Constraint<V, D> {
    private final List<V> variables;

    protected Constraint(List<V> variables) {
        this.variables = variables;
    }

    public abstract boolean isSatisfied(Map<V, D> state);

    public List<V> getVariables() {
        return variables;
    }
}
