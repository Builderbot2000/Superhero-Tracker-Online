package ca.sfu.cmpt213.a3.onlinesuperherotracker.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class SuperheroController {

    @GetMapping("/greeter")
    public String greeter() {
        return "Hello welcome to Superhero Tracker Online!";
    }

    @GetMapping("/listAll")
    public JsonArray listAll() {
        return new JsonArray();
    }

    @PostMapping("/add")
    public void add(JsonObject hero) {

    }

    @PostMapping("/remove/id")
    public JsonArray remove(@PathVariable("id") long id) {
        return new JsonArray();
    }

    @PostMapping("/update/id")
    public JsonObject update(@PathVariable("id") long id, JsonObject hero) {
        return new JsonObject();
    }

    @GetMapping("/listTop3")
    public JsonArray top3() {
        return new JsonArray();
    }
}
