package de.leidenheit.core;

import de.leidenheit.core.constraint.Constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// V === Variable
// D === Domain
public class ConstraintSatisfaction<V, D> {

    private final List<V> variables;
    private final Map<V, List<D>> domains;
    private final HashMap<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    public ConstraintSatisfaction(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;

        for (final var variable : this.variables) {
            this.constraints.put(variable, new ArrayList<>());
        }
    }

    public void addConstraint(Constraint<V, D> constraint) {
        for (final var variable: constraint.getVariables()) {
            final var variableConstraint = this.constraints.get(variable);
            if (variableConstraint != null) {
                variableConstraint.add(constraint);
            }
        }
    }

    public Map<V, D> solve() {
        return this.solve(Collections.emptyMap());
    }

    private Map<V, D> solve(Map<V, D> state) {
        if (this.variables.size() == state.size()) {
            // recursive traversal finished
            return state;
        }
        final var variable = this.variables.stream()
                .filter(v -> !state.containsKey(v))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Expected the state to contain variable but found nothing."));
        for (var domain : this.domains.get(variable)) {
            final var localState = new HashMap<V, D>(state);
            localState.put(variable, domain);

            if (isSatisfied(variable, localState)) {
                return solve(localState);
            }
        }
        // not able to solve
        return Collections.emptyMap();
    }

    private boolean isSatisfied(V variable, Map<V, D> localState) {
        return this.constraints.get(variable).stream().allMatch(c -> c.isSatisfied(localState));
    }
}

