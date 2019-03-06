package com.trackorjargh.apirestcontrolers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trackorjargh.grafics.Grafics;
import com.trackorjargh.javaclass.Gender;
import com.trackorjargh.javarepository.GenderRepository;

@RestController
@RequestMapping("/api")
public class ApiGenderController {
	
	private final GenderRepository genderRepository;
	
	public ApiGenderController(GenderRepository genderRepository) {
		super();
		this.genderRepository = genderRepository;
	}


	@RequestMapping(value = "/generos/grafico", method = RequestMethod.GET)
	public List<Grafics> getGraphicGende() {
		List<Grafics> listGende = new ArrayList<>();

		int sumGende = 0;
		List<Gender> arrayGende = genderRepository.findAll();
		for (int x=0; x < arrayGende.size(); x++) {		
			sumGende += arrayGende.get(x).getFilms().size();
			sumGende += arrayGende.get(x).getBooks().size();
			sumGende += arrayGende.get(x).getShows().size();
			
			listGende.add(new Grafics(arrayGende.get(x).getName(), sumGende));

		}
		
		listGende.sort((l1, l2) -> {return (int)(l1.getPoints() - l2.getPoints());});
		return listGende;
	}

}
