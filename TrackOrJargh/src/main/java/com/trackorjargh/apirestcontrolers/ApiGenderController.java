package com.trackorjargh.apirestcontrolers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackorjargh.grafics.NumberItemByGende;
import com.trackorjargh.javaclass.Book;
import com.trackorjargh.javaclass.Film;
import com.trackorjargh.javaclass.Gender;
import com.trackorjargh.javaclass.Shows;
import com.trackorjargh.javarepository.GenderRepository;

@RestController
@RequestMapping("/api")
public class ApiGenderController {

	private static final Logger log = LoggerFactory.getLogger(ApiGenderController.class);

	private final GenderRepository genderRepository;

	public ApiGenderController(GenderRepository genderRepository) {
		super();
		this.genderRepository = genderRepository;
	}
	
	public interface showGenders extends Gender.ListInformation, Film.NameFilmAndType, Book.NameBookAndType, Shows.NameFilmAndType {
	}
	
	@RequestMapping(value = "/generos", method = RequestMethod.GET)
	@JsonView(showGenders.class)
	public List<Gender> getGenders() {
		return genderRepository.findAll();
	}

	@RequestMapping(value = "/generos/grafico", method = RequestMethod.GET)
	public List<NumberItemByGende> getGraphicGende() {
		List<NumberItemByGende> listGende = new ArrayList<>();

		int sumGende;
		for (Gender gende : genderRepository.findAll()) {
			sumGende = 0;
			sumGende += gende.getFilms().size();
			sumGende += gende.getBooks().size();
			sumGende += gende.getShows().size();

			listGende.add(new NumberItemByGende(gende.getName(), sumGende));
		}

		log.info("Genres JSON: \n {}", graph2json(listGende));
		return listGende;
	}

	public static String graph2json(List<?> graphs) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(graphs);
		} catch (Exception e) {
			return null;
		}
	}
}
