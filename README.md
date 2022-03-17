# Developer test assignment
Create a small smart home application, able to steer devices such as lights, roller shutters, or heaters.
Your project must be accessible on a Git project.

## Application description
• Fetch and parse the file http://storage42.com/modulotest/data.json
• On the home page, list the devices, and make them filterable by product type
• List the devices of one or more selected device types
• Allow deleting any devices
• Create a steering page for each device type
• Lights: Mode ON/OFF and intensity management (0 - 100)
• Roller shutters: Set position using a vertical slider (0 - 100)
• Heaters: Mode ON/OFF and set the temperature with a step of 0.5 degrees (min: 7°, max 28°)
• A user profile page where we can update all the informations. Fields must be handled with an error management (email pattern, empty field, ...)
## Required points
• Kotlin
• MVVM
• Jetpack components
• Each device must have its own class
• Each device state (mode, intensity...) must be kept within the app life cycle, even if the app is killed.
• Using external libraries is allowed but must be justified

## Significant points
• Translating the app (french & english)
• Unit tests / UI tests

## Bonus points
• Sexy UI / Animations / MotionLayout / Darkmode
• Rx
• Proguard
• DI



## Credits
• Design - https://dribbble.com/shots/6655071-Smart-Home-App/attachments
• SVG icons - https://www.svgrepo.com

## Screenshot
<img align="left" width="296" height="576" src="https://github.com/react1ve/ModuloTech/blob/master/screen1.jpg">
<img align="left" width="296" height="576" src="https://github.com/react1ve/ModuloTech/blob/master/screen1_with_filter.jpg">
<img align="left" width="296" height="576" src="https://github.com/react1ve/ModuloTech/blob/master/screen2.jpg">
<img align="left" width="296" height="576" src="https://github.com/react1ve/ModuloTech/blob/master/screen3.jpg">
<img align="left" width="296" height="576" src="https://github.com/react1ve/ModuloTech/blob/master/screen4.jpg">
<img align="left" width="296" height="576" src="https://github.com/react1ve/ModuloTech/blob/master/all_screens.jpg">


## Tech stack
- Clean Architecture
- MVVM
- Dependency Injection (Koin)
- Kotlin Coroutines + Flow
- Git
- Material Design
- Retrofit
- LiveData
- ViewBinding
- Navigation Component
- Multi module
- Single Activity Concept
- Android Architecture Components
- Android Jetpack Components
- *CustomViews
- SOLID, DRY, KISS principles
