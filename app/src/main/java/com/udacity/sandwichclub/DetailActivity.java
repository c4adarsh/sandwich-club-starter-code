package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mAlsoKnownAsHeaderTextView;
    private TextView mIngredientsHeaderTextView;
    private TextView mPlaceOfOriginHeaderTextView;
    private TextView mDescriptionHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeIDs();
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.error)
                .into(ingredientsIv);

        populateUI(sandwich);
        setTitle(sandwich.getMainName());

    }

    /**
     * This method finises this activity when back arrow is pressed
     * @param item clicked menu item
     * @return true as we have handled the menu clcik
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(android.R.id.home == item.getItemId()){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializing all the IDs that are in xml to be referenced in the java code
     */
    private void initializeIDs() {
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mAlsoKnownAsHeaderTextView = findViewById(R.id.also_known_header_tv);
        mIngredientsHeaderTextView = findViewById(R.id.ingredients_header_tv);
        mPlaceOfOriginHeaderTextView = findViewById(R.id.origin_header_tv);
        mDescriptionHeaderTextView = findViewById(R.id.description_header_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param sandwich  used to populate the UI.
     */
    private void populateUI(Sandwich sandwich) {

        if (sandwich.getIngredients().size() > 0) {
            mIngredientsTextView.setText("");
            StringBuilder displayIngredients = new StringBuilder();
            for (String eachIngredient : sandwich.getIngredients()) {
                displayIngredients.append(eachIngredient);
                displayIngredients.append("\n");
            }
            mIngredientsTextView.setText(displayIngredients.subSequence(0,displayIngredients.length()-2));
        } else {
            mIngredientsTextView.setVisibility(View.GONE);
            mIngredientsHeaderTextView.setVisibility(View.GONE);
        }

        if (sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder displayAlsoKnownAs = new StringBuilder();
            for (String eachIngredient : sandwich.getAlsoKnownAs()) {
                displayAlsoKnownAs.append(eachIngredient);
                displayAlsoKnownAs.append("\n");
            }
            mAlsoKnownAsTextView.setText(displayAlsoKnownAs.subSequence(0,displayAlsoKnownAs.length()-2));
        } else {
            mAlsoKnownAsTextView.setVisibility(View.GONE);
            mAlsoKnownAsHeaderTextView.setVisibility(View.GONE);
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().trim().isEmpty()) {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            mPlaceOfOriginTextView.setVisibility(View.GONE);
            mPlaceOfOriginHeaderTextView.setVisibility(View.GONE);
        }
        if (sandwich.getDescription() != null && !sandwich.getDescription().trim().isEmpty()) {
            mDescriptionTextView.setText(sandwich.getDescription());
        } else {
            mDescriptionTextView.setVisibility(View.GONE);
            mDescriptionHeaderTextView.setVisibility(View.GONE);
        }
    }
}
