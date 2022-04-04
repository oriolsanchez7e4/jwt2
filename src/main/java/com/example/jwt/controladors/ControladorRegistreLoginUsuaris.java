package com.example.jwt.controladors;


import com.example.jwt.model.entitats.Usuari;
import com.example.jwt.model.entitats.UsuariConsultaDTO;
import com.example.jwt.model.serveis.ServeiUsuari;
import com.example.jwt.seguretat.jwt.JwtProvider;
import com.example.jwt.seguretat.jwt.LoginPassword;
import com.example.jwt.seguretat.jwt.UsuariJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorRegistreLoginUsuaris {
    private final ServeiUsuari serveiUsuaris;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;

    /*
    {
    "username":"Oriol",
    "password":"password",
    "avatar":"http://imatge.com"
    }

    token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQ5MDkxMTk5LCJleHAiOjE2NDkxNzc1OTksImZ1bGxuYW1lIjoiT3Jpb2wiLCJyb2xlcyI6IlVTRVIifQ.vzRNdTMSMpUhmJjhf-BbFbLOXMfiazAWFBGN8K4-R_UnXY_8fPn14KaW6XC65zqjGFVdekzVHgpgLvUvB7zLbA

    Afegeix id automàticament
     */
    @PostMapping("/usuaris")
    public ResponseEntity<?> nouUsuari(@RequestBody Usuari nouUsuari) {
        try {
            Usuari res = serveiUsuaris.crearNouUsuari(nouUsuari);
            UsuariConsultaDTO usu = new UsuariConsultaDTO(res.getUsername(), res.getAvatar(), res.getRol());
            return new ResponseEntity<UsuariConsultaDTO>(usu, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            //per si intentem afegir 2 usuaris amb el mateix username, saltarà excepció
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/usuaris")
    public ResponseEntity<?> llistarUsuarisDTO() {
        List<Usuari> res = serveiUsuaris.llistarUsuaris();
        List<UsuariConsultaDTO> aux = new ArrayList();
        for (Usuari usu : res) {
            aux.add(new UsuariConsultaDTO(usu.getUsername(), usu.getAvatar(), usu.getRol()));
        }
        if (res.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(aux);
    }



    @PostMapping("/login")
    public ResponseEntity<UsuariJwt> login(@RequestBody LoginPassword userPassword)
    {
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPassword.getUsername(),userPassword.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        Usuari usu=(Usuari)auth.getPrincipal();
        String jwtToken = tokenProvider.generateToken(auth);
        UsuariJwt usu2=new UsuariJwt(usu.getUsername(),usu.getAvatar(),usu.getRol(),jwtToken);
        //es retorna userName, Avatar, Rol i Token
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usu2);
    }


    @GetMapping("/login")
    public UsuariConsultaDTO login(@AuthenticationPrincipal Usuari usu){
        UsuariConsultaDTO usu2=new UsuariConsultaDTO(usu.getUsername(),usu.getAvatar(),usu.getRol());
           return usu2;
    }


//    {
//        "username": "Montse",
//        "avatar": null,
//        "rol": "USER",
//        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.
//        eyJzdWIiOiIzMyIsImlhdCI6MTY0OTA5MTA2MiwiZXhwIjoxNjQ5MTc3NDYyLCJmdWxsbmFtZSI6Ik1vbnRzZSIsInJvbGVzIjoiVVNFUiJ9.
//        6kWT1sh-i2mEZLPFEiJ6UE7sjBc-4FeADtvUeLAa-c0vYmslEhKdnecd1fD_7pk3CtUbNgrerrStyaBP4chpFA"
//    }



}
