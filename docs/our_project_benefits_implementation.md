<span id="top>"></span>

# Our Project Benefits Implementation

File written as of 2 Nov 2025 - commit ab28ae38

<hr />

## Overview
This document outlines perks in choosing our project from a implementation standpoint.
For use by Part-2 team developers and presentation.
All features described here are implemented in the codebase as of commit d3681900.
All features already have examples of being used in the code and can be referenced there.

## World System
Our project features a robust world system that supports dynamic loading and unloading of world segments, ensuring optimal performance and resource management.
Worlds are defined using JSON files, allowing for easy editing and customization.
Essentially every world can be created with only a JSON file and the relevant tilesets and assets.

<span id="world_file"></span>
### World File
Refer to the [World File Specifications](world_file_specs.md) for a comprehensive breakdown of the world file structure and required fields.

### Tile-Entity System
Our project utilises a tile and entity system that allows for modular world development and easy scalability.
Tiles are defined in the <a href="#world_file">World File</a> in a bitmap format and can be resized to your world scale
Tile based coordinates are used throughout the world file, and the world generation in the code. We also have easy-to-use
helper functions that can translate tile-coordinates to pixel-coordinates for you.
<br><br>
In our project we also have an entity system that can be defined in the <a href="#world_file">World File</a> for more
demanding parts of the world like NPCs and world objects (e.g. bookshelves, chairs, etc.) and we supply extra easy-to-use
helper functions to place and manage these entities in the world, and interact with them.
NPC entities can also be defined with dialogue trees and quest interactions with our custom <a href="#speech">speech system</a>.

### World Generation
World generation is handled by both the <a href="#world_file">World File</a> and the BaseGameScreen abstract class, where
you can define a new screen for your world that handles specific logic and rendering for that world.
The world generation process reads the world file and constructs the world based on the defined tiles, entities, and properties.
This modular approach allows for easy creation of new worlds by simply defining a new world file and implementing a corresponding screen class.

## HUD System
Our project includes a flexible HUD (Heads-Up Display) system that allows for dynamic display in-game
Since we have planned a lot of complex hud interactions like being able to put in checkin codes, unlock items, and
a score system, we have made the hud system very modular and easy to use.

<span id="speech"></span>
### Speech System
Our project includes a comprehensive speech system that allows for dynamic dialogue interactions with NPCs. You could
also use it to display help text and other HUD based items. With the speech system, you can define dialogue trees,
speech bubbles, and other text-based interactions that are unique to your world.

## Rendering System
Our project features an advanced but easy-to-use rendering system that supports various graphical effects and optimizations.

### Rendering Pipeline
We have implemented a custom rendering pipeline where you can define different layers for rendering using a z-index system.
This allows for precise control over the rendering order of tiles and entities. You can also creatively
use this system to render entities behind tiles or outside of their collision bounds.

### Shaders
We have implemented a shader system that allows for custom graphical effects to be applied to tiles and entities.
In part-1 we only used this to create a vignette effect, but in part-2 you could expand on this to create more complex effects
and reuse the UV code that took us ages to figure out! (because of the viewports)

## Game Engine
Our project is built on LibGDX which is a solid, open-source, community driven game engine that provides a 
wide range of features and tools for game development. Don't believe us? Mindustry, Space Haven, and many other games are built with libgdx!
Using LibGDX allows us to leverage its capabilities for cross-platform development, efficient rendering, and input handling.
LibGDX is really easy to learn and you'll love it!

<hr /> 

<a href="#top">Back to Top</a>
