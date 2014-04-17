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

    private ViewGroup mRootView;
    private EditText mInputEditText;
    private ListView mTodoList;
    private List<String> mTodoItems;
    private ArrayAdapter<String> mArrayAdapter;

    private static final int START_COLOR = Color.rgb(102, 204, 255);
    private static final int END_COLOR = Color.rgb(246, 95, 64);

    private double mInterpolationPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayShowTitleEnabled(false);

        mTodoList = (ListView) findViewById(R.id.todo_list);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mTodoItems = new Stack<>();
        mInterpolationPercentage = 0.0;
        mArrayAdapter = new ArrayAdapter<>(this, R.layout.todolist_item, mTodoItems);
        mTodoList.setAdapter(mArrayAdapter);

        mRootView.setBackgroundColor(getInterpolatedColor(mInterpolationPercentage));

        /*
        Code for our original UI setup without the ActionBar

        mCurrentItem = (TextView) findViewById(R.id.current_item);
        mAddItemButton = (Button) findViewById(R.id.add_item);
        mInputEditText = (EditText) findViewById(R.id.input_todo);


        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mInputEditText.getText().toString();
                mCurrentItem.setText(input);
            }
        }); */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mInputEditText = (EditText) menu.findItem(R.id.action_edit_input).getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add: {
                String input = mInputEditText.getText().toString();
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

    public void addItemToList (String input) {
        mArrayAdapter.add(input);
        mInterpolationPercentage += 0.2;
        mRootView.setBackgroundColor(getInterpolatedColor(mInterpolationPercentage));
    }

    public void removeTopItem() {
        if (mArrayAdapter.getCount() > 0) {
            mArrayAdapter.remove(mArrayAdapter.getItem(0));
            mInterpolationPercentage -= 0.2;
            mRootView.setBackgroundColor(getInterpolatedColor(mInterpolationPercentage));
        }
        if (mArrayAdapter.getCount() == 0) {
            Toast.makeText(this, "Well done! You've saved a kitten by being productive!", Toast.LENGTH_SHORT).show();
        }
    }

    private static int getInterpolatedColor(double percentage) {
        int red = interpolateComponent(Color.red(START_COLOR), Color.red(END_COLOR), percentage);
        int green = interpolateComponent(Color.green(START_COLOR), Color.green(END_COLOR), percentage);
        int blue = interpolateComponent(Color.blue(START_COLOR), Color.blue(END_COLOR), percentage);

        return Color.rgb(red, green, blue);
    }

    private static int interpolateComponent(int start, int end, double percentage) {
        return (int) (start * (1.0 - percentage) + end * percentage);
    }

}
