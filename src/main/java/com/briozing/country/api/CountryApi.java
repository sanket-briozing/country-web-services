package com.briozing.country.api;

import com.briozing.country.models.CountryRequestVO;
import com.briozing.country.models.CountryResponseVO;
import com.briozing.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/country")
public class CountryApi {
    CountryService countryService;

    public CountryApi(@Autowired CountryService countryService){
        this.countryService=countryService;
    }

    @PostMapping(value="/add",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryResponseVO> addCountry(@RequestBody CountryRequestVO countryRequestVO){
        CountryResponseVO countryResponseVO= countryService.addCountry(countryRequestVO);
        return new ResponseEntity<>(countryResponseVO, HttpStatus.OK);
    }

    @GetMapping(value="/findByName/{countryName}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryResponseVO> findByName(@PathVariable String countryName){
        System.out.println("Country Name : " + countryName);
        HttpStatus status=HttpStatus.OK;
        CountryResponseVO countryResponseVO=null;
        try {
            countryResponseVO = countryService.findCountryByName(countryName);
        }catch(Exception e){
            status= HttpStatus.NOT_FOUND;
            countryResponseVO=null;
        }
        return new ResponseEntity<>(countryResponseVO, status);
    }

    @GetMapping(value="/findById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryResponseVO> findById(@PathVariable String id){
        System.out.println("Country Id : " + id);
        HttpStatus status= HttpStatus.OK;
        CountryResponseVO countryResponseVO = null;
        try{
            countryResponseVO= countryService.findCountryById(id);
        }catch(Exception e){
            status = HttpStatus.NOT_FOUND;
            countryResponseVO = null;
        }
        return new ResponseEntity<>(countryResponseVO,status);
    }

    @PutMapping(value = "/update/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryResponseVO> update(@RequestBody CountryRequestVO countryRequestVO,@PathVariable String id){
        HttpStatus status= HttpStatus.OK;
        CountryResponseVO countryResponseVO = null;
        try {
            countryResponseVO = countryService.update(id, countryRequestVO);
            if(countryResponseVO == null){
                status = HttpStatus.NOT_FOUND;
            }
        }catch(Exception e){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(countryResponseVO,status);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        HttpStatus status= HttpStatus.CREATED;
        String message = "Deleted";
        try{
            int deletedId = countryService.delete(id);
            if(deletedId==0){
                status = HttpStatus.NOT_FOUND;
                message ="Record Not Found";
            }
        }catch (Exception e){
            status=HttpStatus.NOT_FOUND;
            message="Record Not Found";
        }
        return new ResponseEntity<>(message,status);
    }

    @GetMapping(value="/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryResponseVO>> findAll(){
        HttpStatus status= HttpStatus.OK;
        List<CountryResponseVO> countryResponseVO = null;
        try{
            countryResponseVO= countryService.getAllCountries();
        }catch(Exception e){
            status = HttpStatus.NOT_FOUND;
            countryResponseVO = null;
        }
        return new ResponseEntity<>(countryResponseVO,status);
    }

    @GetMapping(value="/findAllCountries",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryResponseVO>> findAllCountries(){
        HttpStatus status= HttpStatus.OK;
        List<CountryResponseVO> countryResponseVO = null;
        try{
            countryResponseVO= countryService.findAllCountries();
        }catch(Exception e){
            status = HttpStatus.NOT_FOUND;
            countryResponseVO = null;
        }
        return new ResponseEntity<>(countryResponseVO,status);
    }

}

