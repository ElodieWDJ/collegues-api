package dev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.entites.Collegue;
import dev.repository.CollegueRepo;

@Service
public class CollegueService {

	private CollegueRepo collegueRepo;

	public CollegueService(CollegueRepo collegueRepo) {

		this.collegueRepo = collegueRepo;
	}

	/**
	 * 
	 * @return une liste d'objet de tous les collegues
	 */
	public List<Collegue> getList() {
		return collegueRepo.findAll();
	}

	/**
	 * 
	 * @param id identifiant du collegue recherché
	 * @return un objet Collegue correspondant au collegue
	 */
	public Optional<Collegue> getById(Integer id) {
		return collegueRepo.findById(id);
	}

	/**
	 * 
	 * @param matricule le matricule recherché
	 * @return un objet Collegue correspondant au matricule
	 */
	public Optional<Collegue> getByMatricule(String matricule) {
		return collegueRepo.findByMatricule(matricule);
	}

	/**
	 * 
	 * @param nom le nom recherché
	 * @return une liste d'objet Collegue correspondant au nom
	 */
	public List<Collegue> getByNom(String nom) {
		return collegueRepo.findAllBynom(nom);
	}

	/**
	 * 
	 * @param nom le nom recherché
	 * @return une liste de string de tous les matricules correspondant au nom
	 */
	public List<String> getAllMatricules() {
		return collegueRepo.findMatricule();
	}

	/**
	 * 
	 * @param id l'identifiant rechercher
	 * @return un optional contenant le matricule correspondant a l'id
	 */
	public Optional<String> getMatriculesById(Integer id) {
		return collegueRepo.findMatriculeById(id);
	}

	/**
	 * 
	 * @param nom
	 * @return un optional contenant le matricule correspondant a un nom
	 */
	public Optional<String> getMatriculesByNom(String nom) {
		return collegueRepo.findMatriculeByNom(nom);
	}

	/**
	 * 
	 * @param matricule     le matricule du collegue à ajouter
	 * @param nom           le nom du collegue à ajouter
	 * @param prenom        le prenom du collegue à ajouter
	 * @param email         l'email du collegue à ajouter
	 * @param dateNaissance la date de naissance du collegue à ajouter (format sql)
	 * @param photoUrl      l'url de l'image du collegue à ajouter
	 * @return un objet collegue.
	 */
	@Transactional
	public Collegue addCollegue(String nom, String prenom, String email, LocalDate dateNaissance, String photoUrl) {

		return collegueRepo
				.save(new Collegue(UUID.randomUUID().toString(), nom, prenom, email, dateNaissance, photoUrl));
	}

	/**
	 * 
	 * @param id            l'id du Collegue
	 * @param matricule     le matricule du Collegue a metre a jour
	 * @param nom           le nom du Collegue a metre a jour
	 * @param prenom        le prenom du Collegue a metre a jour
	 * @param email         l'email du Collegue a metre a jour
	 * @param dateNaissance la date de naissance du Collegue a metre a jour
	 * @param photoUrl      l'url de l'image du Collegue a metre a jour
	 * @return un objet collegue
	 */
	public Collegue updateCollegue(Integer id, String matricule, String nom, String prenom, String email,
			LocalDate dateNaissance, String photoUrl) {
		Optional<Collegue> collegueToUpdate = this.getById(id);
		if (collegueToUpdate.isPresent()) {
			collegueToUpdate.get().setMatricule(matricule);
			collegueToUpdate.get().setNom(nom);
			collegueToUpdate.get().setPrenom(prenom);
			collegueToUpdate.get().setDateNaissance(dateNaissance);
			collegueToUpdate.get().setEmail(email);
			collegueToUpdate.get().setPhotoUrl(photoUrl);
		}
		return collegueRepo.save(collegueToUpdate.get());

	}

	/**
	 * 
	 * @param id id du collegue a supprimer
	 * @return un boolean
	 */
	public String remUser(Integer id) {

		Optional<Collegue> collegueToRemove = this.getById(id);
		if (collegueToRemove.isPresent()) {
			collegueRepo.delete(collegueToRemove.get());
			return "Suppression reussi";
		}
		return "id non trouvé";
	}

}
