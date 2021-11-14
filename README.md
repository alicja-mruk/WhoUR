# WhoUr

Project has been created to practice Composable architecture and to implement Google Ad Mob.

## Folder structure
 - data
	- di (hilt modules)
	- models (data classes)
	- repository
		- datasource ( local and remote dataSources)
		- datastore
		- exceptions (custom exceptions)
		- usecases
	- utils (network, parsing utils)
- ui
	- composables (small composable views)
	- screens (composable screens)
	- theme
	- utils (view utils)

## Application flow

1. Splash screen is shown
2. If user is logged in, is redirected to Category List Screen. Otherwise to Login Screen.
3. Category List Screen contains multiple categories fetched from Firebase Firestore.
4. Each Category has got even number of items inside. Choosing items is quiz-like. After finishing quiz, user got a message with summarizing description.

## Used libraries 
- Android composable
- Navigation compose
- Material Design
- Lifecycles
- Coil
- Hilt
- Firebase SDK (Firestore)
- AdMob
- Datastore
- Moshi
- Coroutines
- Timber
