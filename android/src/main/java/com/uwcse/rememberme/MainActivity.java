package com.uwcse.rememberme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

/**
 * The main activity that is shown when our application is first launched. We specify this
 * through the AndroidManifest.xml file
 */
public class MainActivity extends Activity {

    private Button mAddItemButton;
    private EditText mInputEditText;
    private TextView mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentItem = (TextView) findViewById(R.id.current_item);

        /* mAddItemButton = (Button) findViewById(R.id.add_item);
        / mInputEditText = (EditText) findViewById(R.id.input_todo);


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
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mInputEditText = (EditText) menu.findItem(R.id.action_edit_input).getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            String input = mInputEditText.getText().toString();
            mCurrentItem.setText(input);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
