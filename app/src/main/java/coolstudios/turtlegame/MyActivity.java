package coolstudios.turtlegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        int i = 2;
        getActionBar().hide();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void storyMode (View view)
    {
        Intent storyGame = new Intent(this, StoryModeActivity.class);
        startActivity(storyGame);
    }

    public void survivalMode (View view)
    {
        Intent survivalGame = new Intent(this, SurvivalModeActivity.class);
        startActivity(survivalGame);
    }

    public void options (View view)
    {
        Intent optionPage = new Intent(this, OptionsActivity.class);
        startActivity(optionPage);
    }
}
