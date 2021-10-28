package make;

import graph.DirectedGraph;
import graph.LabeledGraph;

/** A directed, labeled subtype of Graph that describes dependencies between
 *  targets in a Makefile.
 *  @author ROCKY LUBBERS
 */
class Depends extends LabeledGraph<Rule, Void> {
    /** An empty dependency graph. */
    Depends() {
        super(new DirectedGraph());
    }
}
