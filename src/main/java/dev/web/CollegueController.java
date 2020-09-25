package dev.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.dto.CollegueDto;
import dev.entites.Collegue;
import dev.service.CollegueService;

@RestController
@RequestMapping("collegues") // ressource "collegues"
public class CollegueController {

	private CollegueService collegueService;

	public CollegueController(CollegueService collegueService) {
		super();
		this.collegueService = collegueService;
	}

	@GetMapping
	public List<String> listCollegue(@RequestParam String nom) {
		return collegueService.getByNom(nom).stream().map(Collegue::getMatricule).collect(toList());

	}

	/**
	 * url : [SERVER]/collegue/getMatricule?nom={nom}
	 * 
	 * @return un json de tous les matricules en bdd
	 */
	@GetMapping("{matricule}")
	public ResponseEntity<?> findByMatricule(@PathVariable String matricule) {
		Optional<Collegue> byMatricule = collegueService.getByMatricule(matricule);

		if (byMatricule.isPresent()) {
			return ResponseEntity.ok(byMatricule.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * url : [SERVER]/collegue
	 * 
	 * @param collegueDto un objet collegueDto au format json
	 * @return un objet collegueDto au format json
	 */
	@PostMapping
	public ResponseEntity<?> newCollegue(@RequestBody CollegueDto collegueDto) {
		Collegue newCollegue = collegueService.addCollegue(collegueDto.getNom(), collegueDto.getPrenom(),
				collegueDto.getEmail(), collegueDto.getDateNaissance(), collegueDto.getPhotoUrl());

		return ResponseEntity.ok(newCollegue);
	}

	/**
	 * 
	 * @param collegueDto un objet collegueDto au format json
	 * @return un objet collegueDto au format json
	 */
	@PutMapping
	public ResponseEntity<?> editUser(@RequestBody CollegueDto collegueDto) {
		Collegue editCollegue = collegueService.updateCollegue(collegueDto.getId(), collegueDto.getMatricule(),
				collegueDto.getNom(), collegueDto.getPrenom(), collegueDto.getEmail(), collegueDto.getDateNaissance(),
				collegueDto.getPhotoUrl());
		return ResponseEntity.ok(editCollegue);
	}

	/**
	 * 
	 * @param id id du collegue a remove
	 * @return une String
	 */
	@DeleteMapping
	public ResponseEntity<?> remUser(@RequestParam Integer id) {
		return ResponseEntity.ok(collegueService.remUser(id));

	}

}
