package com.aws.dynamo.db.web;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.aws.dynamo.db.dto.Movie;
import com.aws.dynamo.db.service.MovieSearchService;

@RestController
@RequestMapping("/movies")
public class DynamoDBController {

	@Autowired
	private MovieSearchService service;

	@GetMapping(value = "/retrieveAllMovies")
	public ResponseEntity<List<Movie>> retrieveAllMovkes() {

		List<Movie> movies = service.findAllMovies();
		System.out.println("movies:" + movies);
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}

	@PostMapping(value = "/insert")
	public ResponseEntity<String> insert() {
		Movie movie = new Movie();
		movie.setFilmid(UUID.randomUUID().toString());
		movie.setActors(Arrays.asList("a", "b"));
		movie.setRated("top rated");
		movie.setTitle("KGF");
		movie.setYear(2021);
		PutItemResult result = service.insertMovie(movie);
		System.out.println("result:" + result);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
}
