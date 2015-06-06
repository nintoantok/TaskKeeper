package gyri.taskkeeper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import gyri.taskkeeper.taskHelpers.TaskAdapter;
import gyri.taskkeeper.taskfragments.DetailFragment;
import gyri.taskkeeper.taskfragments.TaskListFragment;


public class MainActivity extends ActionBarActivity implements TaskListFragment.Callback{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public  static Boolean _TwoPaneUI;
    public static final int COL_TASK_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.task_detail_container)!= null){

            _TwoPaneUI = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.task_detail_container, new DetailFragment())
                        .commit();
            }

        } else {
            _TwoPaneUI = false;
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent _AboutIntent = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(_AboutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(Long _TaskRowId) {

        if(_TwoPaneUI){
            Bundle _args = new Bundle();
            _args.putLong(DetailFragment.TASK_ID_KEY,_TaskRowId);

            DetailFragment _DtFrg = new DetailFragment();
            _DtFrg.setArguments(_args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_container , _DtFrg)
                    .commit();
        } else {
            Log.d(LOG_TAG, "Single Pane UI seting Intent");
            Intent _DetailViewIntent = new Intent(getApplicationContext(),DetailActivity.class);
            _DetailViewIntent.putExtra(DetailFragment.TASK_ID_KEY,_TaskRowId);
            startActivity(_DetailViewIntent);
        }

    }
}
