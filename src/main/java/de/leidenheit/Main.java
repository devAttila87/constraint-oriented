package de.leidenheit;

import de.leidenheit.core.Colour;
import de.leidenheit.core.ConstraintSatisfaction;
import de.leidenheit.core.constraint.CityColourConstraint;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

    /* Constraint Oriented Programming based on the explanation of "RawCoding".

        -> Return a distinct colour from a set of colours (RGB) for a node.
        Berlin(R)
        |     \
        |      \
        |       \
        |        \
        |         \
        |          \
        |           \
        |            \
        |             \
        |              \
        Leipzig(G)------Dresden(B)------Riesa(R)
        |               /\              |
        |              /  \             |
        |             /    \            |
        |            /      \           |
        |           /        \          |
        |          /          \         |
        |         /            \        |
        |        /              \       |
        |       /                \      |
        Chemnitz(R)               Zittau(G)
     */

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String BERLIN = "Berlin";
    private static final String ZITTAU = "Zittau";
    private static final String RIESA = "Riesa";
    private static final String DRESDEN = "Dresden";
    private static final String LEIPZIG = "Leipzig";
    private static final String CHEMNITZ = "Chemnitz";

    public static void main(String[] args) {
        // variables
        final var cities = List.of(BERLIN, DRESDEN, LEIPZIG, RIESA, CHEMNITZ,ZITTAU);

        // domain
        final var domains = new HashMap<String, List<Colour>>();
        for (String city : cities) {
            domains.put(city, Arrays.stream(Colour.values()).toList());
        }

        // constraints
        final var cs = new ConstraintSatisfaction<String, Colour>(cities, domains);
        cs.addConstraint(new CityColourConstraint(BERLIN, DRESDEN));
        cs.addConstraint(new CityColourConstraint(DRESDEN, LEIPZIG));
        cs.addConstraint(new CityColourConstraint(LEIPZIG, BERLIN));

        cs.addConstraint(new CityColourConstraint(LEIPZIG, CHEMNITZ));
        cs.addConstraint(new CityColourConstraint(CHEMNITZ, DRESDEN));

        cs.addConstraint(new CityColourConstraint(DRESDEN, RIESA));
        cs.addConstraint(new CityColourConstraint(RIESA, ZITTAU));
        cs.addConstraint(new CityColourConstraint(ZITTAU, DRESDEN));

        // solve
        final var solution = cs.solve();
        for (final Map.Entry<String, Colour> entry : solution.entrySet()) {
            LOGGER.info(() -> MessageFormat.format("{0}[{1}]", entry.getKey(), entry.getValue()));
        }
    }
}