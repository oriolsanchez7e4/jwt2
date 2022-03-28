package com.example.jwt.model.serveis;


import com.example.jwt.model.entitats.Employee;
import com.example.jwt.model.repositoris.RepositoriEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeiEmployee {
    private final RepositoriEmployee repositoriEmployee;

    public List<Employee> llistarEmployee(){
        return repositoriEmployee.findAll();
    }

    public Employee consultarPerId(Long id){
        return repositoriEmployee.findById(id).orElse(null);
    }

    public Employee eliminarEmployee(Long id){
        Employee res=repositoriEmployee.findById(id).orElse(null);
        if(res!=null) repositoriEmployee.deleteById(id);
        return res;
    }

    public Employee afegirEmployee(Employee v){
        return repositoriEmployee.save(v);
    }

    /** si existeix el employee el modifica (el torna a gravar), altrament retorna null*/
    public Employee modificarEmployee(Employee v){
        Employee res=null;
        if(repositoriEmployee.existsById(v.getId())) res=repositoriEmployee.save(v);
        return res;
    }


}
