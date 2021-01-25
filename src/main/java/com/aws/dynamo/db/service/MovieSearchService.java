package com.aws.dynamo.db.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.aws.dynamo.db.dto.Movie;
import com.aws.dynamo.db.repositories.MovieRepository;

@Component
public class MovieSearchService {

	@Autowired
	private MovieRepository repo;

	@Autowired
	private AmazonDynamoDB dynamoDB;

	public List<Movie> findAllMovies() {

		return StreamSupport.stream(repo.findAll().spliterator(), true).collect(Collectors.toList());
	}

	public PutItemResult insertMovie(Movie movie) {

		DynamoDB dynamo = new DynamoDB(dynamoDB);
		Table table = dynamo.getTable("movie");
		PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("filmid", movie.getFilmid())
				.with("rated", movie.getRated()).with("title", movie.getTitle()).with("Actors", movie.getActors()));

		return outcome.getPutItemResult();
	}

}
