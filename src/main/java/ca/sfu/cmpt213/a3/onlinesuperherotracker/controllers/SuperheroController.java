package ca.sfu.cmpt213.a3.onlinesuperherotracker.controllers;

import ca.sfu.cmpt213.a3.onlinesuperherotracker.model.Superhero;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The implemented REST API and controller for this SpringBoot project.
 * Enables interaction with a database of superheros.
 * Implements basic interface functions of GET, POST, DELETE, and PUT.
 * Contains a method to produce a top3 rank chart for the number of civilians saved by hero.
 */
@RestController
public class SuperheroController {

    private ArrayList<Superhero> heroList = new ArrayList();
    private AtomicLong nextId = new AtomicLong();

    // Quiz Question Addon
    @GetMapping("/superhero/{id}")
    public Superhero listHero(@PathVariable("id") long id) {
        return heroList.get(indexById(id));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello welcome to Superhero Tracker Online!";
    }

    @GetMapping("/listAll")
    public List<Superhero> listAll() {
        return heroList;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero add(@RequestBody Superhero hero) {
        if (hero.getHeightInCm() < 0 || hero.getCivilianSaveCount() < 0) throw new IllegalArgumentException();
        hero.setId(nextId.incrementAndGet());
        heroList.add(hero);
        return hero;
    }

    @PostMapping("/remove/{id}")
    public ArrayList<Superhero> remove(@PathVariable("id") long id) {
        heroList.remove(indexById(id));
        return heroList;
    }

    @PostMapping("/update/{id}")
    public Superhero update(@PathVariable("id") long id, @RequestBody Superhero hero) {
        if (hero.getHeightInCm() < 0 || hero.getCivilianSaveCount() < 0) throw new IllegalArgumentException();
        heroList.set(indexById(id),hero);
        return hero;
    }

    @GetMapping("/listTop3")
    public ArrayList<Superhero> top3() {
        ArrayList<Superhero> cloneHeroList = (ArrayList<Superhero>)heroList.clone();
        Collections.sort(cloneHeroList);
        if (cloneHeroList.size() < 3) throw new NotEnoughSuperheroesException();
        if (cloneHeroList.get(2).getCivilianSaveCount() <= 0) throw new NotEnoughSuperheroesException();
        ArrayList<Superhero> top3 = new ArrayList<>();
        top3.add(cloneHeroList.get(0));
        top3.add(cloneHeroList.get(1));
        top3.add(cloneHeroList.get(2));
        return top3;
    }

    /**
     * Finds a hero of specified id within the heroList.
     * Throws SuperheroNotFoundException if no hero of such id is found.
     * @param id Id of the desired hero.
     * @return The hero.
     */
    public int indexById(long id) {
        int targetIndex = 0;
        boolean found = false;
        for (Superhero hero : heroList) {
            if (hero.getId() == id) {
                found = true;
                break;
            }
            targetIndex++;
        }
        if (!found) throw new SuperheroNotFoundException();
        return targetIndex;
    }

    // Exception handlers

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid values.")
    public void IllegalArgumentExceptionHandler() { }

    @ExceptionHandler({ SuperheroNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Request ID not found.")
    public void superheroNotFoundExceptionHandler() { }

    @ExceptionHandler({ NotEnoughSuperheroesException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Insufficient qualified superhero count to create top3 list.")
    public void notEnoughSuperheroesExceptionHandler() { }
}
