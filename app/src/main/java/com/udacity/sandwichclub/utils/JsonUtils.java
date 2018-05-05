package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private final static String name = "name";
    private final static String mainName = "mainName";
    private final static String alsoKnownAs = "alsoKnownAs";
    private final static String placeOfOrigin = "placeOfOrigin";
    private final static String description = "description";
    private final static String image = "image";
    private final static String ingredients = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        System.out.println(json);
        if (json == null) {
            return null;
        }

        JSONObject sandwichObject;

        try {
            sandwichObject = new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }

        JSONObject nameObject;
        nameObject = getNameJSONObject(sandwichObject);
        return new Sandwich(getMainName(nameObject), getAlsoKnownAs(nameObject), getPlaceOfOrigin
                (sandwichObject), getDescription(sandwichObject), getImage(sandwichObject),
                getIngredients(sandwichObject));
    }

    /**
     * Get the ingredients List from the JSON object
     *
     * @param sandwichObject the JSON Object which is parsed to get the ingredients list
     * @return Ingredients List
     */
    private static List<String> getIngredients(JSONObject sandwichObject) {
        List<String> ingredientsList = new ArrayList<>();
        if (sandwichObject.has(ingredients)) {
            try {
                JSONArray ingredientsArray = sandwichObject.getJSONArray(ingredients);
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredientsList.add(ingredientsArray.getString(i));
                }
            } catch (JSONException e) {
                Log.d(TAG, "imageUrl parsing failed" + e.getMessage());
            }
        }
        return ingredientsList;
    }

    /**
     * Get the image url from the JSON object
     *
     * @param sandwichObject the JSON Object which is parsed to get the image URL
     * @return image url string
     */
    private static String getImage(JSONObject sandwichObject) {
        String imageUrl = null;
        if (sandwichObject.has(image)) {
            try {
                imageUrl = sandwichObject.getString(image);
            } catch (JSONException e) {
                Log.d(TAG, "imageUrl parsing failed" + e.getMessage());
            }
        }
        return imageUrl;
    }

    /**
     * Get the description from the JSON object
     *
     * @param sandwichObject the JSON Object which is parsed to get the description
     * @return description string
     */
    private static String getDescription(JSONObject sandwichObject) {
        String descriptionString = "";
        if (sandwichObject.has(description)) {
            try {
                descriptionString = sandwichObject.getString(description);
            } catch (JSONException e) {
                Log.d(TAG, "description parsing failed" + e.getMessage());
            }
        }
        return descriptionString;
    }

    /**
     * Get the place of origin from the JSON object
     *
     * @param sandwichObject the JSON Object which is parsed to get the place of origin
     * @return origin string
     */
    private static String getPlaceOfOrigin(JSONObject sandwichObject) {
        String placeOfOriginString = "";
        if (sandwichObject.has(placeOfOrigin)) {
            try {
                placeOfOriginString = sandwichObject.getString(placeOfOrigin);
            } catch (JSONException e) {
                Log.d(TAG, "placeOfOrigin parsing failed" + e.getMessage());
            }
        }
        return placeOfOriginString;
    }

    /**
     * Get the alsoKnownAs list from the JSON object
     *
     * @param nameObject the JSON Object which is parsed to get the alsoKnownAs list
     * @return alsoKnownAs List
     */
    private static List<String> getAlsoKnownAs(JSONObject nameObject) {
        List<String> alsoKnownAsList = new ArrayList<>();
        if (nameObject != null && nameObject.has(alsoKnownAs)) {
            try {
                JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }
            } catch (JSONException e) {
                Log.d(TAG, "alsoKnownAsArray parsing failed" + e.getMessage());
            }
        }
        return alsoKnownAsList;
    }

    /**
     * Get the place of origin from the JSON object
     *
     * @param nameObject the JSON Object which is parsed to get the main name of the sandwich
     * @return main name string
     */
    private static String getMainName(JSONObject nameObject) {
        String mainNameString = "";
        if (nameObject != null && nameObject.has(mainName)) {
            try {
                mainNameString = nameObject.getString(mainName);
            } catch (JSONException e) {
                Log.d(TAG, "mainName parsing failed" + e.getMessage());
            }
        }
        return mainNameString;
    }

    /**
     * Get the name object from the JSON Object
     *
     * @param sandwichObject the JSON Object which is parsed to get the name object of the sandwich
     * @return name object which contains main name and also known as names of the sandwich
     */
    private static JSONObject getNameJSONObject(JSONObject sandwichObject) {
        if (sandwichObject.has(name)) {
            try {
                return sandwichObject.getJSONObject(name);
            } catch (JSONException e) {
                Log.d(TAG, "name parsing failed" + e.getMessage());
            }
        }
        return null;
    }
}
