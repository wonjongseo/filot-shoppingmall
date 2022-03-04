package com.filot.filotshop.config;

import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person,String> {
}
