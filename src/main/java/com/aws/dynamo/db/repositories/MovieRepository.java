package com.aws.dynamo.db.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.aws.dynamo.db.dto.Movie;

@EnableScan
public interface MovieRepository extends CrudRepository<Movie, String> {

}
