package com.example.broken

import grails.gorm.MultiTenant
import grails.gorm.annotation.Entity

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Person implements MultiTenant<Person> {

    @Id
    @GeneratedValue
    Long id;

    Long version;

    @NotNull
    String name;

    Number age;

    String tenantId;

}
