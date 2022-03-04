package com.filot.filotshop.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
class PersonTest {

    @Autowired
    private PersonRedisRepository repo;

    @Test
    void redisTest (){
        Person person = new Person("park", 20);

        Person save = repo.save(person);
        System.out.println("save = " + save);
        Optional<Person> byId = repo.findById(person.getId());
        System.out.println("byId.get() = " + byId.get());
        long count = repo.count();
        System.out.println("count = " + count);

        repo.delete(person);
    }

}