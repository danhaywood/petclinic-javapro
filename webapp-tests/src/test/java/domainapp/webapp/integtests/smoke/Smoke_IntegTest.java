package domainapp.webapp.integtests.smoke;

import java.util.List;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.causeway.applib.services.wrapper.InvalidException;
import org.apache.causeway.applib.services.xactn.TransactionService;

import domainapp.modules.petowner.dom.petowner.PetOwner;
import domainapp.modules.petowner.dom.petowner.PetOwnerRepository;
import domainapp.modules.petowner.dom.petowner.PetOwners;
import domainapp.modules.petowner.fixture.PetOwner_persona;
import domainapp.webapp.integtests.WebAppIntegTestAbstract;

class Smoke_IntegTest extends WebAppIntegTestAbstract {

    @Inject PetOwners menu;
    @Inject PetOwnerRepository petOwnerRepository;
    @Inject TransactionService transactionService;

    @BeforeEach
    void fixture() {
        fixtureScripts.runPersonas(PetOwner_persona.values());
    }

    @Test
    void create_pet_owner() {

        // when
        final List<PetOwner> petOwnersBefore = wrap(menu).listAll();

        // then
        assertThat(petOwnersBefore).hasSize(10);


        // when
        final PetOwner fred = wrap(menu).create("Fred", null, null, null);
        transactionService.flushTransaction();

        // then
        final List<PetOwner> petOwnersAfter = wrap(menu).listAll();
        assertThat(petOwnersAfter).hasSize(11);
        assertThat(petOwnersAfter).contains(fred);
    }

    @Test
    void update_name() {

        // given
        final PetOwner jamal = petOwnerRepository.findByName(PetOwner_persona.JAMAL.getName());

        // when
        final String newName = "Jamal Washington Jr.";
        wrap(jamal).updateName(newName);
        transactionService.flushTransaction();

        // then
        assertThat(wrap(jamal).getName()).isEqualTo(newName);
    }

    @Test
    void update_name_invalid() {
        // given
        final PetOwner jamal = petOwnerRepository.findByName(PetOwner_persona.JAMAL.getName());

        // when, then
        assertThatThrownBy(() -> wrap(jamal).updateName("Jamal Washington !!!"))
                .isInstanceOf(InvalidException.class)
                .hasMessage("Character '!' is not allowed.");
    }

    @Test
    void delete_pet_owner() {

        // given
        final PetOwner fred = wrap(menu).create("Fred", null, null, null);
        transactionService.flushTransaction();

        final List<PetOwner> petOwnersBefore = wrap(menu).listAll();
        assertThat(petOwnersBefore).hasSize(11);

        // when
        wrap(fred).delete();
        transactionService.flushTransaction();

        // then
        final List<PetOwner> petOwnersAfter = wrap(menu).listAll();
        assertThat(petOwnersAfter).hasSize(10);
    }


    @Test
    void notes() {

        // given
        final PetOwner fred = wrap(menu).create("Fred", null, null, null);
        transactionService.flushTransaction();

        // when
        wrap(fred).setNotes("These are some notes");
        transactionService.flushTransaction();

        // then
        assertThat(wrap(fred).getNotes()).isEqualTo("These are some notes");

    }

}


