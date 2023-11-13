package de.leidenheit.core.constraint;

import de.leidenheit.core.Colour;

import java.util.List;
import java.util.Map;

public class CityColourConstraint extends Constraint<String, Colour> {

    public CityColourConstraint(String... variables) {
        super(List.of(variables));
    }

    @Override
    public boolean isSatisfied(Map<String, Colour> state) {
        final var place1 = getVariables().get(0);
        final var place2 = getVariables().get(1);

        if (!state.containsKey(place1) || !state.containsKey(place2)) {
            return true;
        }
        return state.get(place1) != state.get(place2);
    }
}
