package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/donuts")
public class ControllerOfDonuts {

    private final DonutRepository repository;
    public ControllerOfDonuts(DonutRepository repository){
        this.repository = repository;
    }
    @PostMapping("")
    public Donut createRoute(@RequestBody Donut donut){
        return this.repository.save(donut);
    }
    @GetMapping("/{id}")
    public Optional showRoute(@PathVariable Long id){
        Optional idNumber = this.repository.findById(id);
        return idNumber;
    }
    @PatchMapping("/{id}")
    public Donut updateRoute(@PathVariable Long id,
                             @RequestBody Donut donut) {
        if(this.repository.existsById(id)){
            Donut thisDonut = this.repository.findById(id).get();
            thisDonut.setName(thisDonut.getName());
            thisDonut.setExpiration(thisDonut.getExpiration());
            thisDonut.setTopping(thisDonut.getTopping());
            return this.repository.save(thisDonut);
        }
        else return this.repository.save(donut);

    }
    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id){
        this.repository.deleteById(id);
    }
    @GetMapping("")
    public Iterable<Donut> indexOrListRoute(){
        return this.repository.findAll();
    }

}
