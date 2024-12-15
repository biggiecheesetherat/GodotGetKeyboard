<p align="center">
  <img src="./icon.png"  height="100" alt="Godot-InternetConnectionStatePlugin Icon"/>
</p>

<h1 align="center">
  Godot Get Keyboard Plugin
</h1>

## About

This plugin allows checking for a keyboard (connected with Bluetooth or charging port) in Godot 4.

## Features

- On-demand check

## Requirements 

- Godot 4.2 or higher
- Android 7.0 or higher
  - Use Gradle build
  - Enable Access to Keyboard (if asked)


## Installation

- Open the `AssetLib` tab in Godot with your project open.
- Search for "Android Internet Connection State Plugin" and install the plugin by Mero.
- Once the download completes, deselect "example" if you don't need the demo test scene.
- Open project settings -> plugins, and enable the plugin "AndroidInternetConnectionStatePlugin".
- Done!


## Usage
  
> Note: the plugin registers itself as *Autoload* under the name "AndroidInternetConnectionStatePlugin".

> Note: don't forget to check "Requirements" section.

1. Install the plugin
2. Enable the plugin in Godot (Project -> Project Settings -> Plugins Tab)
3. Obtain the plugin instance:
```
var _getKeys = Engine.get_singleton("AndroidInternetConnectionStatePlugin")
```
4. Check connection:
```
# return bool (true if connected, otherwise - false)
_getKeys.hasPhysicalKeyboard()
```


## Warning

The plugin itself cannot determine whether the user has **Internet access** or not! It can only check the connection status.
> For example: if the user is connected to Wi-Fi but does not have actual Internet access, in this case the plugin will return the connection status as **true**.


## Extra

You can find implementation assets of the plugin in the Android Studio Assets folder, feel free to explore. 
