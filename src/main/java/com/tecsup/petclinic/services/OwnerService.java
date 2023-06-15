package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

public interface OwnerService {
	
	Owner create(Owner owner);

	
	Owner update(Owner owner);

	void delete(Long id) throws OwnerNotFoundException;

	Owner findById(long id) throws OwnerNotFoundException;

	
	List<Owner> findByFirst_name(String first_name);
	


	Iterable<Owner> findAll();

}
