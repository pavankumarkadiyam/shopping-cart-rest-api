package com.pavan.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pavan.shoppingcart.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

}
