package domainapp.webapp.application.fixture.scenarios;

import jakarta.inject.Inject;

import org.apache.causeway.testing.fixtures.applib.fixturescripts.FixtureScript;
import org.apache.causeway.testing.fixtures.applib.modules.ModuleWithFixturesService;

import domainapp.modules.visit.fixture.Visit_persona;

public class DomainAppDemo extends FixtureScript {

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChildren(this, moduleWithFixturesService.getTeardownFixture());
        transactionService.flushTransaction();
        ec.executeChild(this, new Visit_persona.PersistAll());
    }

    @Inject ModuleWithFixturesService moduleWithFixturesService;

}
