WizardBuilder
=============

Provides a simple interface to a customizeable wizard as seen in <a href="https://play.google.com/store/apps/details?id=com.ramdroid.appquarantine" target="_blank">App Quarantine</a>.

Here are some use cases:

- Introduction screen when app is launched for the first time
- Show off new features when app is updated
- Provide tutorial screens for certain features

![Screenshot](https://github.com/ramdroid/WizardBuilder/raw/master/Screenshot.png "Example")

There is a new option to launch the wizard in an unintrusive way from the action bar.
A colored 'news' icon is shown to launch the wizard. Once the user has read it (e.g.
used one of the two wizard buttons) then the icon disappears. It will reappear again
if you push some new content (increase the whatsNewId).

![Screenshot](https://github.com/ramdroid/WizardBuilder/raw/master/Screenshot2.png "Launcher")

Dependencies
============

In order to use WizardBuilder you need to add the following libraries:

- <a href="http://actionbarsherlock.com/" target="_blank">ActionBarSherlock</a>
- <a href="http://viewpagerindicator.com/" target="_blank">ViewPagerIndicator</a>

Usage
=====

In your project you first create the wizard:

    WizardPage firstPage = new WizardPage.Builder()
            .setImageId(R.drawable.robot)
            .setDescriptionId(R.string.robot)
            .build();
    
    WizardPage secondPage = new WizardPage.Builder()
            .setImageId(R.drawable.alien)
            .setDescriptionId(R.string.alien)
            .setButtonTextId(R.string.dosomething)
            .build();
    
    WizardBuilder wizard = new WizardBuilder.Builder(this, "Test")
            .setWhatsNewId(42)
            .addPage(firstPage)
            .addPage(secondPage)
            .setListener(new Listener())
            .build();
            
You can now launch it directly:

    wizard.show();
    
If you want to launch it from the ActionBar instead then you need to inherit your activity from 'WizardLauncherActivity' and add an icon to your ActionBar menu. Then add this line (typically from onCreate of your activity):

    addWizardLauncher(R.id.news, createWizard());
    
Finally in the listener you can do some action when the user has clicked a button in a wizard page:
            
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
    

License
=======

WizardBuilder is licensed under the Apache License. Feel free to use it in your free or paid Android apps. Please add a little note in your license info screen that you are using WizardBuilder and if possible include a link to this Github page. Vice versa you can send me a message so I can mention your app here as well.
