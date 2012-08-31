package com.ramdroid.wizardbuilder.test;

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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ramdroid.wizardbuilder.WizardBuilder;
import com.ramdroid.wizardbuilder.WizardPage;

/**
 * Simple sample activity that launches a wizard.
 */
public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void clickButton(View view) {

        // create a wizard page
        WizardPage firstPage = new WizardPage.Builder()
                .setImageId(R.drawable.robot)
                .setDescriptionId(R.string.robot)
                .build();

        // create another wizard page
        WizardPage secondPage = new WizardPage.Builder()
                .setImageId(R.drawable.alien)
                .setDescriptionId(R.string.alien)
                .setButtonTextId(R.string.dosomething)
                .build();

        // initialize a wizard builder
        WizardBuilder.Builder builder =  new WizardBuilder.Builder(this, "Test")
                .setWhatsNewId(42)
                .addPage(firstPage)
                .addPage(secondPage)
                .setListener(new Listener());

        // show always or show once?
        if (((CheckBox)findViewById(R.id.showAlways)).isChecked()) {
            builder.setShowAlways();
        }

        // build the wizard configuration
        WizardBuilder wizard = builder.build();

        // GO GO GO!
        if (!wizard.show()) {
            Toast.makeText(this, "Wizard has already been shown.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receive user feedback from the wizard.
     */
    class Listener implements WizardBuilder.WizardListener {

        @Override
        public void onClickedButton(int page) {
            Toast.makeText(MyActivity.this, "Do something on page " + page, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClickedDismissButton() {
            Toast.makeText(MyActivity.this, "Dismissed", Toast.LENGTH_SHORT).show();
        }
    }
}
