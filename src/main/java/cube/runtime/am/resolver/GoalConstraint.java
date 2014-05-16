package cube.runtime.am.resolver;

import cube.runtime.archetype.ResolutionStrategy;

/**
 * User: debbabi
 * Date: 9/20/13
 * Time: 5:28 PM
 */
public class GoalConstraint extends Constraint {

    ResolutionStrategy resolutionStrategy = ResolutionStrategy.Find;


    private boolean optional=false;

    public GoalConstraint(ResolutionGraph resolutionGraph, String archetypePropertyName, ResolutionStrategy resolutionStrategy, Variable subject, Variable object) {
        super(resolutionGraph, archetypePropertyName, subject, object);
        this.resolutionStrategy = resolutionStrategy;

    }

    public ResolutionStrategy getResolutionStrategy() {
        return resolutionStrategy;
    }

    public void setResolutionStrategy(ResolutionStrategy resolutionStrategy) {
        this.resolutionStrategy = resolutionStrategy;
    }


    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
}
