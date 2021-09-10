package com.web.uz;

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
            .importPackages("com.web.uz");

        noClasses()
            .that()
            .resideInAnyPackage("com.web.uz.service..")
            .or()
            .resideInAnyPackage("com.web.uz.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.web.uz.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
