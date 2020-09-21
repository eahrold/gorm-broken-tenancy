package com.example.broken

import grails.gorm.multitenancy.Tenants
import grails.gorm.transactions.Rollback
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import javax.inject.Inject

@MicronautTest(transactional = false)
class TenancySpec extends Specification {

    final static String TENANT_1_ID = "acme-inc";
    final static String TENANT_2_ID = "pretend-com";

    @Inject
    PersonService personService;


    @Rollback
    void 'test tenants works when first() is used'() {
        when:

        def tenant1Person1 = Tenants.withId(TENANT_1_ID) {
            def p = new Person(name: "John Doe", age: 19)
            personService.save(p)
        }

        def tenant2Person1 = Tenants.withId(TENANT_2_ID) {
            def p = new Person(name: "Jane Smith", age: 22)
            personService.save(p)
        }

        then:
        def tenant1firstPerson = Tenants.withId(TENANT_1_ID) {
            personService.getFirst()
        }
        tenant1firstPerson.tenantId == TENANT_1_ID
        tenant1firstPerson.name == tenant1Person1.name
        tenant1firstPerson.age == tenant1Person1.age


        def tenant2firstPerson = Tenants.withId(TENANT_2_ID) {
            personService.getFirst()
        }
        tenant2firstPerson.tenantId == TENANT_2_ID
        tenant2firstPerson.name == tenant2Person1.name
        tenant2firstPerson.age == tenant2Person1.age
    }

    @Rollback
    void 'test tenants hack actually succeeds'() {
        when:

        def tenant1Person1 = Tenants.withId(TENANT_1_ID) {
            def p = new Person(name: "John Doe", age: 19)
            personService.save(p)
        }

        def tenant2Person1 = Tenants.withId(TENANT_2_ID) {
            def p = new Person(name: "Jane Smith", age: 22)
            personService.save(p)
        }

        then:
        def tenant1firstPerson = Tenants.withId(TENANT_1_ID) {
            personService.getFirstDirect()
        }
        tenant1firstPerson.tenantId == TENANT_1_ID
        tenant1firstPerson.name == tenant1Person1.name
        tenant1firstPerson.age == tenant1Person1.age


        def tenant2firstPerson = Tenants.withId(TENANT_2_ID) {
            personService.getFirstDirect()
        }
        tenant2firstPerson.tenantId == TENANT_2_ID
        tenant2firstPerson.name == tenant2Person1.name
        tenant2firstPerson.age == tenant2Person1.age

    }

}