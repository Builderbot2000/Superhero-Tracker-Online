package ca.sfu.cmpt213.a3.onlinesuperherotracker.model;

import java.util.ArrayList;

/** A utility object that allows the Json save file to be casted by Gson onto a java object.
 */
class SavesObject {

    /** ArrayList representation as a field of the saved hero list in the Json save file.
     */
    ArrayList<Superhero> savesArray;

    SavesObject(ArrayList<Superhero> savesArray) {
        this.savesArray = savesArray;
    }
}
