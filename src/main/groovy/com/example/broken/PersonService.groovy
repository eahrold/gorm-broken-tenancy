package com.example.broken

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.multitenancy.Tenants
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional


@Service(Person)
abstract class PersonService {


    @CurrentTenant
    @Transactional(readOnly = true)
    Person getFirst() {
        Person.first()
    }

    @Transactional(readOnly = true)
    Person getFirstDirect() {
        Person.findByTenantId((String)Tenants.currentId())
    }

    @Transactional
    @CurrentTenant
    abstract Person save(Person p)
}
