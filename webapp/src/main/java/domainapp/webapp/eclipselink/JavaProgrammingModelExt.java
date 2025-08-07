package domainapp.webapp.eclipselink;

import jakarta.inject.Inject;

import org.springframework.stereotype.Component;

import org.apache.causeway.core.metamodel.context.MetaModelContext;
import org.apache.causeway.core.metamodel.facetapi.FeatureType;
import org.apache.causeway.core.metamodel.facetapi.MetaModelRefiner;
import org.apache.causeway.core.metamodel.facets.FacetFactoryAbstract;
import org.apache.causeway.core.metamodel.progmodel.ProgrammingModel;

@Component
public class JavaProgrammingModelExt implements MetaModelRefiner {
    @Override
    public void refineProgrammingModel(ProgrammingModel pm) {

        var step1 = ProgrammingModel.FacetProcessingOrder.C2_AFTER_METHOD_REMOVING;
        var mmc = pm.getMetaModelContext();

        pm.addFactory(step1, new JpaRemoveWeavedMethodsFacetFactory(mmc), ProgrammingModel.Marker.JPA);

    }
}

/**
 * Removes all methods with prefix "_persistence_set" (as introduced by EclipseLink weaver).
 */
class JpaRemoveWeavedMethodsFacetFactory extends FacetFactoryAbstract {

    @Inject
    public JpaRemoveWeavedMethodsFacetFactory(final MetaModelContext mmc) {
        super(mmc, FeatureType.OBJECTS_ONLY);
    }

    @Override
    public void process(final ProcessClassContext context) {

        getClassCache()
                .streamPublicMethods(context.getCls())
                .filter(method->method.name().startsWith("_persistence_set"))
                .forEach(context::removeMethod);

    }

}



