package com.repitch.materialtest;

        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.support.v4.app.ActivityOptionsCompat;
        import android.support.v4.app.DialogFragment;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.app.NavUtils;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.MenuItem;
        import android.view.View;


public abstract class BaseActivity extends AppCompatActivity {

    protected Fragment mCurrentFragment;

    public void launchActivityWithSharedElement(Class activity, View sharedElement, Bundle args) {
        Intent intent = new Intent(this, activity);
        // Pass data object in the bundle and populate details activity.
        if (args != null) {
            intent.putExtras(args);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = null;
            options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, sharedElement, sharedElement.getTransitionName());
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void launchDialog(DialogFragment dialogFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialogFragment.show(ft, tag);
    }

    public void launchFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, tag)
//        ft.add(R.id.content_frame, fragment, fragment.getTag())
                .addToBackStack(tag)
                .commit();
    }

    public void launchFragmentNoBackStack(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, tag)
                .commit();
    }

    public void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("STATES", "onCreate by " + this.getClass());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
        Log.e("BACK", "back is pressed!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return goUp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean goUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // pop
            getSupportFragmentManager().popBackStack();
        } else {
            NavUtils.navigateUpFromSameTask(this);
        }
        shouldDisplayHomeUp();
        return true;
    }

    // methods for back stack toggle
    private final FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            shouldDisplayHomeUp();
        }
    };

    protected void showBackStackToggle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        shouldDisplayHomeUp();
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    public void shouldDisplayHomeUp() {
        boolean canBack;
        Log.e("BACK", "getParentAct: " + NavUtils.getParentActivityName(this));
        if (getClass().getName().equals(MainActivity.class.getName())) {
            // главный класс
            canBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
            Log.e("BACK", "This is MainMenuActivity!");
        } else {
            canBack = true;
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(canBack);
        }
    }

}