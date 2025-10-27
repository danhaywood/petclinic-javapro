package domainapp.webapp.eclipselink;


import jakarta.inject.Named;

import org.apache.causeway.core.metamodel.CausewayModuleCoreMetamodel;

import org.springframework.stereotype.Component;

import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.core.metamodel.CausewayModuleCoreMetamodel;
import org.apache.causeway.core.metamodel.services.classsubstitutor.ClassSubstitutorAbstract;

@Component
@Named(CausewayModuleCoreMetamodel.NAMESPACE + ".ClassSubstitutorJpa")
@jakarta.annotation.Priority(PriorityPrecedence.MIDPOINT)
public class ClassSubstitutorJpa extends ClassSubstitutorAbstract {

    public ClassSubstitutorJpa() {
        ignoreEclipseLinkJpaInternal();
    }

    protected void ignoreEclipseLinkJpaInternal() {
        ignorePackage("org.eclipse.persistence");
    }

}

