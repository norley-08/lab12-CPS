package com.tecsup.petclinic.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.dto.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.services.PetService;

/**
 * 
 * @author jgomezm
 *
 */
@RestController
public class PetController {

	private static final Logger logger 
		= LoggerFactory.getLogger(PetController.class);

	@Autowired
	private PetService service;
	 
	
	@GetMapping("/pets")
	public Iterable<Pet> getPets() {
		//
		return service.findAll();
	}

	
	@PostMapping("/pets")
	@ResponseStatus(HttpStatus.CREATED)
	Pet create(@RequestBody PetDTO newPet) {
		
		Pet pet = new Pet();
		pet.setName(newPet.getName());
		pet.setOwnerId(newPet.getOwnerId());
		pet.setTypeId(newPet.getTypeId());
		pet.setBirthDate(newPet.getBirthDate());
		
		logger.info("create:" +pet.toString());
		
		return service.create(pet);
	}
	
	
	
	/**
	 * Find by id
	 * 
	 * @param id
	 * @return
	 * @throws PetNotFoundException
	 */
	@GetMapping("/pets/{id}")
	ResponseEntity<Pet> findOne(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
		} catch (PetNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Save or update
	 * 
	 * @param newPet
	 * @param id
	 * @return
	 */
	@PutMapping("/pets/{id}")
	Pet saveOrUpdate(@RequestBody PetDTO newPet, @PathVariable Long id) {
		Pet pet = null;

		logger.info("saveOrUpdate:" + newPet.toString());

		try {
			pet = service.findById(id);
			pet.setName(newPet.getName());
			pet.setOwnerId(newPet.getOwnerId());
			pet.setTypeId(newPet.getTypeId());
			pet.setBirthDate(newPet.getBirthDate());
			service.update(pet);
		} catch (PetNotFoundException e) {
			pet = service.create(pet);
		}
		return pet;
	}

	/**
	 * 
	 * @param id
	 */
	@DeleteMapping("/pets/{id}")
	ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			service.delete(id);
			return new ResponseEntity<>("" + id, HttpStatus.OK);
		} catch (PetNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
