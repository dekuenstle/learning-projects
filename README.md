Learning Projects
=====================

Recently, while cleaning up my computer, 
I have stumbled across projects created while learning computer programming aged 15 to 20.
Some of them are collected in this repository.

Don't expect brilliant clean code. Still, most of them are interesting or even fun (games included!).
Before release, I just did a minor cleanup.

Browser
--------

You can try all these projects simply in your browser without any addition installation.

### Brainfuck Interpreter

Interprete the esoteric programming language [Brainfuck](https://en.wikipedia.org/wiki/Brainfuck) 
directly in your browser.

[Start in your browser](https://dekuenstle.github.io/learning-projects/brainfuck_interpreter/index.html)

### Delaunay Triangulation 

Nicely span contiguous triangles by a set of points and the [Delaunay triangulation](https://en.wikipedia.org/wiki/Delaunay_triangulation).

[Start in your browser](https://dekuenstle.github.io/learning-projects/delaunay_triangulation/index.html)

### Forrestwalk 3D

Walk through scenic random generated forrest at night. Using WebGL and [Three.js](https://threejs.org/).

[Start in your browser](https://dekuenstle.github.io/learning-projects/forrestwalk_3d/index.html)

Graphics are made by Christian Lodroner.

### ID Number (German)

The number on German ID cards can be generated. 
Visit [Wikipedia (German!)](https://de.wikipedia.org/wiki/Ausweisnummer) for more information.

[Start in your browser](https://dekuenstle.github.io/learning-projects/id_number/index.html)

### Key Frames Animation

Simple library for key frame animation of html styles.
Simply define start, stop (or intermediate) states together your easing schema.

[Start in your browser](https://dekuenstle.github.io/learning-projects/keyframe_animation/index.html).

### Ludum Dare 29

A small game for the [Ludum Dare 29: Beneath the Surface](http://ludumdare.com/compo/ludum-dare-29/) game competition.

[Start in your browser](https://dekuenstle.github.io/learning-projects/ludumdare_29/index.html)

### Parallax

A background object moving slower than the foreground has an illusion of depth.
The [effect](https://en.wikipedia.org/wiki/Parallax_scrolling) was used heavily in computer games,
but got also quite hip for web sites.

[Start in your browser](https://dekuenstle.github.io/learning-projects/parallax/index.html)

### Radar Chart D3

A 180 degree [radar chart](https://en.wikipedia.org/wiki/Radar_chart) implemented 
using the beautiful [d3 library](https://d3js.org/).

[Start in your browser](https://dekuenstle.github.io/learning-projects/radar_chart_d3/index.html).

### Sinus Wave

Draw a beautiful sinus wave in your browser window. Not more, not less.

[Start in your browser](https://dekuenstle.github.io/learning-projects/sinus_wave/index.html)

Java
-----

Most of these projects can simply be executed if you have java installed on your computer (most likely you have!).

### Zombie Game

A tower. Zombies. Survive.

Move turret with the mouse, fire by left-click.
Press keys for additional options: *p* for pause, *space* open shop, *w* change weapon (once buyed).

Simply [download the game file](https://github.com/dekuenstle/learning-projects/blob/master/zombie_game/game.jar?raw=true) and start it by double clicking or via command line `java -jar ./zombie_game/game.jar`.

This one of the older but bigger projects, which I really like. 
Graphics and parts of the code are by Christian Lodroner.

You can also compile the code yourself if you want to:
```
# Download and go to source directory
git clone https://github.com/dekuenstle/learning-projects.git
cd ./zombie_game/src

# Compile java code
javac TowerDefense/Game/Program.java

# Run game
java TowerDefense.Game.Program
```


Lua
----

Install the 2d game framework [LÖVE](https://love2d.org/) to use this projects.
You also should download this repository.

### Boid Simulation

This is one of my favourites: Flock movements of birds can be simulated, 
if each bird follows few simple rules. Read more about [Boids](https://en.wikipedia.org/wiki/Boids).

Run by [downloading the .love file](https://github.com/dekuenstle/learning-projects/blob/master/boid_simulation/boid.love?raw=true)
and double clicking *boid.love* or in the command line `love ./boid_simulation/boid.love`

### Ludum Dare 26

A small game for the [Ludum Dare 26: Minimalism](http://ludumdare.com/compo/ludum-dare-26/) game competition.
Run by [downloading the .love file](https://github.com/dekuenstle/learning-projects/blob/master/ludumdare_26/game.love?raw=true) and double clicking *game.love* or in the command line `love ./ludumdare_26/game.love`


C
--

Here you have to compile the code yourself. 
We assume you are on a *nix system (macOS, Linux) with installed *gcc*.

### Math formula

Parse and evaluate a math formula string. Supports even parenthesis.

Change the formula you want to evaluate in `./formula_parser/main.c`.

```
# download and change to directory
git clone https://github.com/dekuenstle/learning-projects.git
cd formula_parser

# compile
gcc -I. -lm -o math main.c math_parser.c

# make executable
chmod u+x math

# execute
./math
```

About
-------

### License

Everything in this repository is released under the [MIT license](./LICENSE).
You are free to use, reuse, change and publish it.

### Author

[David-Elias Künstle](https://github.com/dekuenstle/)
