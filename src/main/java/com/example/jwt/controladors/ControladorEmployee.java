package com.example.jwt.controladors;


import com.example.jwt.model.entitats.Employee;
import com.example.jwt.model.serveis.ServeiEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/* CRUD Employee *******************************
- 1) llistar tots els employee
- 2) Afegir employee
- 3) Consultar un Employee per long id
- 4) Modificar Employee
- 5) Eliminar Employee per long id
 ***********************************************/


@RestController
@RequiredArgsConstructor
public class ControladorEmployee {
    private final ServeiEmployee serveiEmployee;

    @GetMapping("/employee")
    public ResponseEntity<?> consultarEmployee() {
        List<Employee> res = serveiEmployee.llistarEmployee();
        if (res != null) return ResponseEntity.ok(res);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/employee")
    public ResponseEntity<?> afegirEmployee(@RequestBody Employee e) {
        try {
            serveiEmployee.afegirEmployee(e);
            return new ResponseEntity<Employee>(e, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> consultarUnEmployee(@PathVariable long id) {
        Employee v = serveiEmployee.consultarPerId(id);
        if (v != null) {
            return ResponseEntity.ok(v);
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/employee")
    public ResponseEntity<?> modificarEmployee(@RequestBody Employee vmod){
        Employee res=serveiEmployee.modificarEmployee(vmod);
        if(res!=null) return ResponseEntity.ok(res);
        else return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> eliminarEmployee(@PathVariable long id){
        Employee res=serveiEmployee.eliminarEmployee(id);
        if(res!=null){
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }

}
