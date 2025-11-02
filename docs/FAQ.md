<span id="top"></span>

# FAQ

File written as of 2 Nov 2025 - commit ab28ae38

<hr />

## How do I create a custom world file?
Refer to the [World File Specifications](world_file_specs.md) for detailed instructions on creating a custom world file.
You will need both the JSON file with valid assets (including a texture atlas with defined regions) and a Screen java file
extending BaseGameScreen. See LibraryScreen.java or OutdoorScreen.java in com.mathochist.mazegame.Screens.Game
for example boilerplate for this.<br>
Note: the game will crash if the world file is invalid, missing assets, or you have invalid world transitions.
You can always use our existing texture atlases for testing your world files before implementing your own assets.
There is a specially created PlaceHolder.atlas file just for that scenario in the /assets/maps/library directory.

## Where can I find example world files?
Example world files are located in the /assets/maps/ directory of the project.
See LibraryScreen.java or OutdoorScreen.java in com.mathochist.mazegame.Screens.Game
for boilerplate for the Java implementation.

## How do I add new entities or tiles to the game?
To add new entities, you will need to create a new class extending MapEntity. (Make sure the constructor matches
the existing ones so the reflection-based instantiation works.)
You can then reference this new class in your world file under the entities section.
<br><br>
To add new tiles, you will need to update the texture atlas with the new tile graphics and define the regions in the 
map file. Refer to [World File Specifications](world_file_specs.md) for details on how to define tiles. You can then 
add the tile in the map matrix of your world file.

## My sprite is not rendering even though I call SpriteBatch.draw(). What gives?
The game uses a custom rendering buffer so that all sprites are drawn in the correct order based on their Y position.
Simply create a RenderObject with your sprite and sprite buffer and add it to the RenderBuffer. For example:
```java
renderBuffer.addToBuffer(new RenderObject(getSprite(), screenBatch, zIndex));
```
A renderBuffer handle can be accessed from almost any render function or from the BaseGameScreen object (current screen object).
Remember your sprite should be drawn on the correct z-index layer for its tile y-position otherwise it may be occluded by other sprites.
The player is always added to the buffer last so will always be on top of other sprites at the same z-index.
<br><br>
If you are not rendering a sprite (then im a bit worried) you may need to do something more exotic. Look at how the debug layer is rendered you
may need to add to your screen render method on a screen by screen basis.
<br>
Note: HUD objects are not included in the render buffer and should be drawn directly to the hud SpriteBatch in the hud render method.
They are always drawn on top of the game world.

## How should I add keyboard inputs?
The game uses a keybuffer system to handle keyboard inputs which are defined from the BaseGameScreen class.
It is preferable you use this system to handle key presses. See Player.java in com.mathochist.mazegame.Entities for an example of how to use the keybuffer.
See JavaDoc for KeyBuffer class for more details.
<br><br>
You can use the built in key input system if you choose though e.g.
```java
if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
    // do something
}
```
However, this is not recommended as it may lead to input conflicts or missed inputs.

## How do I access the debug layer?
Press F3 to toggle the debug layer on and off on any game screen.

## Help, how do I develop in LibGDX?
Refer to the official [LibGDX documentation](https://libgdx.com/wiki/) <br>
Refer to this YT series for a good introduction: [LibGDX Tutorial for Beginners](https://www.youtube.com/playlist?list=PLLwCf-qdpyEnB_FO_1HkUFh7smwGNjAaC) <br>
Refer to this GitHub repo for packing textures into an atlas: [Texture Packer GUI](https://github.com/crashinvaders/gdx-texture-packer-gui) <br>
Refer to this GitHub repo for example building a simple LibGDX project: [LibGDX Example Project](https://github.com/libgdx/libgdx-demo-pax-britannica/tree/master)

## How do I run the game?
Lord, I hope you're not the one developing this. Refer to readme.md for instructions on how to set up and run the game.



<hr /> 

<a href="#top">Back to Top</a>
