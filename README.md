android-color-selector
======================

Simple and customizable color selector component for Android.

Screenshots
-----------

Screenshot on Android 4.4 using the default styles:

![ScreenShot](/screenshots/screenshot.png)

Usage
-----

Integrating the component to your existing project is very simple. Probably the easiest approach is (using Eclipse):

1. Clone and import the project into your workspace
2. Reference the project as a library from your application
3. Check out the example below how to take it into use

For more information on using Android library projects, see:

http://developer.android.com/tools/projects/projects-eclipse.html

Example
-------

```java
ColorSelectorDialog dialog = new ColorSelectorDialog.Builder(this)
	.color(selectedColor)
	.build();				
dialog.addColorSelectorListener(new ColorSelectorListener() {
	@Override
	public void onColorSelected(int color) {
		// ...
	}
});
dialog.show();
```