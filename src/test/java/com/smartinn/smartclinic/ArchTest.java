package com.smartinn.smartclinic;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.smartinn.smartclinic");

        noClasses()
            .that()
            .resideInAnyPackage("com.smartinn.smartclinic.service..")
            .or()
            .resideInAnyPackage("com.smartinn.smartclinic.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.smartinn.smartclinic.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
