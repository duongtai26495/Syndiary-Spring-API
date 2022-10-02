package com.duongtai.estore.services.impl;

import com.duongtai.estore.configs.Snippets;
import com.duongtai.estore.entities.ResponseObject;
import com.duongtai.estore.entities.Role;
import com.duongtai.estore.repositories.RoleRepository;
import com.duongtai.estore.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<ResponseObject> saveNewRole(Role role) {
        if(roleRepository.getRoleByName(role.getName())!=null){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(Snippets.FAILED,String.format(Snippets.ROLE_EXIST,role.getName()),null)
            );
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Snippets.SUCCESS,String.format(Snippets.CREATE_ROLE_SUCCESS,role.getName()),roleRepository.save(role))
            );
        }

    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }
}
