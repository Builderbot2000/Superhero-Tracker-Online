package ca.sfu.cmpt213.a3.onlinesuperherotracker.model;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The main handler for every background operation (I/O and others) requested by the menus and UI.
 * Only contain methods that do not initiate user interaction.
 * Methods only accepts pre-generated entries and executes the corresponding actions.
 */
public class Operator {

    /**
     * Relative file path to the Json saves file.
     * Generates the static file path on runtime, and change windows file dividers from "\" to "/".
     */
    private static final String filePath = System.getProperty("user.dir").replaceAll("\\\\", "/")
            + "/src/ca/sfu/cmpt213/assignment1/Saves.json";

    /**
     * Representation of the entire save file in a loaded state.
     */
    private SavesObject loadedData;

    /**
     * Parses the Json saves file to Java object for local manipulation in session.
     * If no Json file is existent, one is created.
     */
    public Operator() {
        File file = new File(filePath);
        Gson gson = new Gson();
        try {
            JsonElement savesElement = JsonParser.parseReader(new FileReader(file));
            loadedData = gson.fromJson(savesElement.getAsJsonObject(), SavesObject.class);
        } catch (FileNotFoundException exc) {
            loadedData = new SavesObject(new ArrayList<>(0));
        }
    }

    /**
     * Searches for a specific hero in the database and loads it into scope.
     *
     * @param serialNumber Serial number of the hero that is to be loaded from database.
     * @return A Superhero instance with the specified serial number.
     */
    public Superhero load(int serialNumber) {
        int index = serialNumber - 1;
        try {
            return this.loadedData.savesArray.get(index);
        }
        catch (IndexOutOfBoundsException exc){
            System.out.println("Invalid Input! (Serial Number Out Of Bounds)");
            return null;
        }
    }

    /**
     * Saves the input hero into the database
     * Overwrites the previous saved object with matching names if necessary.
     *
     * @param hero Any instance of the Superhero class, will be saved into the database at end of array.
     */
    public void save(Superhero hero) {
        for (int index = 0; index < loadedData.savesArray.size(); index++) {
            if (loadedData.savesArray.get(index).getName().equals(hero.getName())) {
                loadedData.savesArray.remove(index);
                System.out.println("Overwritten.");
                break;
            }
        }

        loadedData.savesArray.add(hero);
        System.out.println(hero.getName() + " is saved to database.");
    }

    /**
     * Saves all data changes made in the current session
     */
    void saveData() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
            Gson gson = new Gson();
            gson.toJson(loadedData, writer);
            System.out.println("All saved.");
        } catch (IOException exc) {
            System.out.println("Save failed.");
            new Throwable().printStackTrace();
        }
    }

    /**
     * Removes an entry from the database
     *
     * @param serialNumber The serial number of entry to be removed from the database.
     */
    public void remove(int serialNumber) {
        if (loadedData.savesArray != null && loadedData.savesArray.size() != 0) {
            int index = serialNumber - 1;
            if (index < loadedData.savesArray.size() && index >= 0) {
                String target = loadedData.savesArray.remove(index).getName();
                System.out.println(target + " is removed from database.");
            } else System.out.println("Invalid Input! (Serial Number Out Of Bounds)");
        } else System.out.println("Database is empty, please add more stuff.");
    }

    /**
     * Prints the list of hero in database line by line in a formatted fashion.
     */
    public void list() {
        if (loadedData.savesArray != null && loadedData.savesArray.size() != 0) {
            System.out.println("<List of Superheros>");
            for (int index = 0; index < loadedData.savesArray.size(); index++) {
                Superhero hero = loadedData.savesArray.get(index);
                int serialNumber = index + 1;
                System.out.println(serialNumber + ". [" + hero.getName() + "] -" +
                        " Superpower: " + hero.getSuperpower() +
                        ", Height: " + hero.getHeight() +
                        ", Number of Civilians Saved: " + hero.getCiviliansSaved());
            }
        } else {
            System.out.println("Database is empty, please add more stuff.");
        }
    }

    /**
     * Displays the top 3 hero who has saved the most civilians
     * Ranked from highest to lowest in terms of saveCivilians field value.
     * Operates based on a shuffle sort pseudo-algorithm.
     */
    public void top3() {
        if (loadedData.savesArray != null && loadedData.savesArray.size() >= 3) {
            Superhero placeHolder = new Superhero(0,"", "", 0, 0);
            Superhero top1 = placeHolder, top2 = placeHolder, top3 = placeHolder;

            for (Superhero hero : loadedData.savesArray) {
                int score = hero.getCiviliansSaved();

                if (score >= 0 && score >= top1.getCiviliansSaved()) {
                    top3 = top2;
                    top2 = top1;
                    top1 = hero;
                } else if (score >= 0 && score >= top2.getCiviliansSaved()) {
                    top3 = top2;
                    top2 = hero;
                } else if (score >= 0 && score >= top3.getCiviliansSaved()) {
                    top3 = hero;
                }
            }

            if (top1.getName().equals("") || top2.getName().equals("") || top3.getName().equals("")) {
                System.out.println("The superheros have not saved enough civilians.");
            } else {
                System.out.println("<Top 3 Civilian Savers>");
                System.out.println("First Place: " + top1.getName() + " saved " + top1.getCiviliansSaved() + " civilians");
                System.out.println("Second Place: " + top2.getName() + " saved " + top2.getCiviliansSaved() + " civilians");
                System.out.println("Third Place: " + top3.getName() + " saved " + top3.getCiviliansSaved() + " civilians");
            }
        } else {
            System.out.println("There is not enough superheros in the list. Please add more.");
        }
    }

    /**
     * Dumps the data fields of all hero on database list with toString().
     */
    public void dump() {
        if (loadedData.savesArray != null && loadedData.savesArray.size() != 0) {
            System.out.println("<Debug Dump of Superheros>");
            for (Superhero hero : loadedData.savesArray) {
                System.out.println(hero.toString());
            }
        } else {
            System.out.println("Database is empty, please add more stuff.");
        }
    }
}
