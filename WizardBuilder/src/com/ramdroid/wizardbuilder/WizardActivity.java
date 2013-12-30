package com.ramdroid.wizardbuilder;

/**
 *    Copyright 2012-2013 by Ronald Ammann (ramdroid)

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

/**
 * In this activity a ViewIndicator and a {@link WizardAdapter} is created.
 * The adapter holds a set of {@link WizardFragment} to allow the user to
 * swipe between the pages.
 *
 * The activity depends on the Android Support Library and ActionBarSherlock.
 *
 * Don't launch this activity directly. It will be called from {@link WizardBuilder}.
 */
public class WizardActivity extends SherlockFragmentActivity {

    private WizardPageSet pages;
    private int whatsNewId;
    private String title;
    private boolean indicatorBelow;
    private int backgroundImageId;
    private ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = this.getIntent().getExtras();
        if (data != null) {
            pages = data.getParcelable("pages");
            whatsNewId = data.getInt("whatsNewId");
            title = data.getString("title");
            indicatorBelow = data.getBoolean("indicatorBelow");
            backgroundImageId = data.getInt("backgroundImageId");
        }

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(indicatorBelow ? R.layout.helpwizard_indicator_below : R.layout.helpwizard_indicator_above);

        if (title != null && title.length() > 0) {
            setTitle(title);
        }

        // use action bar to jump back to calling activity (used on phones only)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // set the pager with an adapter
        WizardAdapter adapter = new WizardAdapter(getSupportFragmentManager());
        adapter.setValues(pages, whatsNewId);
        mPager = (ViewPager)findViewById(R.id.viewpager);
        mPager.setAdapter(adapter);

        // bind the title indicator to the adapter
        PageIndicator indicator = (PageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // instead of mPager.setOnPageChangeListener(mOnPageChangeListener);
        indicator.setOnPageChangeListener(mOnPageChangeListener);

        ViewGroup viewGroup = (ViewGroup) mPager.getParent();
        if (viewGroup != null) {
            try {
                viewGroup.setBackgroundResource(backgroundImageId);
            }
            catch (Resources.NotFoundException e) {
            }
        }

        mPager.setOnTouchListener(new View.OnTouchListener() {
            float oldX = 0, newX = 0, sens = 5;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = event.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        newX = event.getX();
                        if (Math.abs(oldX - newX) < sens) {
                            int nextItem = mPager.getCurrentItem() + 1;
                            if (nextItem >= mPager.getAdapter().getCount()) finish();
                            else mPager.setCurrentItem(nextItem);
                            return true;
                        }
                        oldX = 0;
                        newX = 0;
                        break;
                }

                return false;
            }
        });
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home: {
            finish();
            return true;
        }
        default: {
            return super.onOptionsItemSelected(item);
        }
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        boolean lastPageOverscrolled;
        boolean isLastPage;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (isLastPage && position == mPager.getCurrentItem() && !lastPageOverscrolled) {
                isLastPage = false;
                lastPageOverscrolled = true;
                finish();
            } else {
                isLastPage = false;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mPager.getCurrentItem() == mPager.getAdapter().getCount() - 1) {
                isLastPage = true;
            }
        }
    };
}
