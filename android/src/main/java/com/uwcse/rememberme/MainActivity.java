package com.uwcse.rememberme;

import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.*;

import android.graphics.Color;
import java.util.List;
import java.util.Stack;

/**
 * The main activity that is shown when our application is first launched. We specify this
 * through the AndroidManifest.xml file
 */
public class MainActivity extends Activity {

    /* Fields for our original UI design
    private Button mAddItemButton;
    private TextView mCurrentItem; */

    /** Holds references to Views defined in our layout file **/
    private ViewGroup mRootView;
    private EditText mInputEditText;
    private ListView mTodoList;

    /** A List to act as a datasource for the ArrayAdapter **/
    private List<String> mTodoItems;
    /** Our ArrayAdapter takes data items in our List<String> and adapts them
     * into Views that we define when we construct the array adapter **/
    private ArrayAdapter<String> mArrayAdapter;

    /** This code wasn't covered in the talk but simply lets us set an appropriate background color
     *  based on how many items we've added to our to-do list. The more items we have, the more red
     *  the background will seem */
    private static final int START_COLOR = Color.rgb(102, 204, 255);
    private static final int END_COLOR = Color.rgb(246, 95, 64);
    private double mInterpolationPercentage;

    /**
     * Called when Android is creating your activity. This is where most of your setup
     * code should go.
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hooks up our layout file to this activity */
        setContentView(R.layout.activity_main);
        // Disables the title of our application from being shown on the ActionBar
        getActionBar().setDisplayShowTitleEnabled(false);

        // Sets up references to appropriate Views in our layout file
        mTodoList = (ListView) findViewById(R.id.todo_list);
        mRootView = (ViewGroup) findViewById(R.id.root_view);

        // Sets up the data source backing our ListView
        mTodoItems = new Stack<>();
        // We define that each item in our data source will be injected into the TextView in the
        // layout/todolist_item.xml file
        mArrayAdapter = new ArrayAdapter<>(this, R.layout.todolist_item, mTodoItems);
        // Connects the adapter to the ListView
        mTodoList.setAdapter(mArrayAdapter);

        // This sets up some code to set the background of the LinearLayout in layout/activity_main.xml
        mInterpolationPercentage = 0.0;
        // Set's the background color of the LinearLayout, which is a blend between START_COLOR and
        // END_COLOR
        mRootView.setBackgroundColor(getInterpolatedColor(mInterpolationPercentage));

        /*
        Code for our original UI setup without the ActionBar

        mCurrentItem = (TextView) findViewById(R.id.current_item);
        mAddItemButton = (Button) findViewById(R.id.add_item);
        mInputEditText = (EditText) findViewById(R.id.input_todo);

        // An anonymous inner class was used instead of an inner class as it's unnecessary
        // to write a named class to represnt the behavior of this button; this is the only
        // time the code is going to be used.
        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mInputEditText.getText().toString();
                mCurrentItem.setText(input);
            }
        }); */
    }


    /**
     * Called when Android is ready to request for the ActionBar to be inflated.
     *
     * @return true if the ActionBar is to be displayed, false if otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mInputEditText = (EditText) menu.findItem(R.id.action_edit_input).getActionView();
        return true;
    }

    /**
     * Called when an action item is clicked
     * @param item The MenuItem instance corresponding to the item clicked
     * @return true if you have processed the event, false if it should be processed
     *         by android
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add: {
                String input = mInputEditText.getText().toString();
                // Removes text from the EditText after the user hits Add
                mInputEditText.setText("");
                addItemToList(input);
                return true;
            }
            case R.id.action_remove: {
                removeTopItem();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Adds a given string to the ListView and updates the background color
     * of the LinearLayout root view accordingly.
     * @param input The string to add to the ListView
     */
    public void addItemToList (String input) {
        mArrayAdapter.add(input);
        mInterpolationPercentage += 0.2;
        mRootView.setBackgroundColor(getInterpolatedColor(mInterpolationPercentage));
    }

    /**
     * Removes the top-most item from the ListView, i.e. the element at position 0 in
     * the ListView.
     *
     * If the ListView is empty, or empty after the top most item has been removed, then
     * display a Toast (a brief message to the user) commending him/her for his/her
     * awesomeness.
     */
    public void removeTopItem() {
        // Removes the topmost item, if the list is nonempty
        if (mArrayAdapter.getCount() > 0) {
            // Removes the item at position 0 of the adapter
            mArrayAdapter.remove(mArrayAdapter.getItem(0));
            mInterpolationPercentage -= 0.2;
            mRootView.setBackgroundColor(getInterpolatedColor(mInterpolationPercentage));
        }
        if (mArrayAdapter.getCount() == 0) {
            // Toasts are a great way to display a brief textual message to the user.
            // Toast.makeTest instantiates a toast with a given string and is displayed for a given length of time.
            // .show() is called on the instantiated Toast object to actually display it. Don't forget to call .show()
            // - it's a common bug!
            Toast.makeText(this, "Well done! You've saved a kitten by being productive!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Given a percentage, returns the interpolation between two RGB values. This is used
     * to create a color that is a percentage in-between two colors.
     *
     * @param percentage A percentage value from 0.0 to 1.0 where 0.0 results in a color that
     *                   will be entirely START_COLOR and 1.0 results in a color that will be
     *                   entirely END_COLOR
     * @return
     */
    private static int getInterpolatedColor(double percentage) {
        int red = interpolateComponent(Color.red(START_COLOR), Color.red(END_COLOR), percentage);
        int green = interpolateComponent(Color.green(START_COLOR), Color.green(END_COLOR), percentage);
        int blue = interpolateComponent(Color.blue(START_COLOR), Color.blue(END_COLOR), percentage);

        return Color.rgb(red, green, blue);
    }

    /**
     * Takes a staring and ending Red, Green or Blue component and determines the
     * respective component iterpolated with percentage weighting.
     *
     * @param start The starting component value
     * @param end The ending component value
     * @param percentage The percentage in-between the two values to
     * @return
     */
    private static int interpolateComponent(int start, int end, double percentage) {
        return (int) (start * (1.0 - percentage) + end * percentage);
    }

}
