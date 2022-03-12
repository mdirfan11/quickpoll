package com.mdtech.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.mdtech.quickpoll.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long>{

}
