package com.ramdroid.wizardbuilder;

/**
 *    Copyright 2012 by Ronald Ammann (ramdroid)

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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * This class allows you to launch WizardBuilder in an unintrusive way from the ActionBar.
 */
public class WizardLauncherActivity extends SherlockFragmentActivity {

    protected int mMenuResourceId = 0;
    protected WizardBuilder mBuilder;

    protected void addWizardLauncher(int menuResourceId, WizardBuilder builder) {
        mMenuResourceId = menuResourceId;
        mBuilder = builder;
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (mMenuResourceId > 0 && mBuilder != null) {
            MenuItem menuItemLauncher = menu.findItem(mMenuResourceId);
            if (menuItemLauncher != null) {
                menuItemLauncher.setVisible(mBuilder.canLaunch());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == mMenuResourceId && mBuilder != null) {
            mBuilder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}