WizardBuilder
=============

Provides a simple interface to a customizeable wizard as seen in <a href="https://play.google.com/store/apps/details?id=com.ramdroid.appquarantine" target="_blank">App Quarantine</a>.

Here are some use cases:

- Introduction screen when app is launched for the first time
- Show off new features when app is updated
- Provide tutorial screens for certain features

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
            
And in the listener you can do some action when the user has clicked a button in a wizard page:
            
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