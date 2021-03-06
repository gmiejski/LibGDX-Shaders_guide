LibGDX-Shaders_guide
====================

This is a simple tutorial series made for myself as an introduction to Graphics course on AGH univesity.

Together with mateusz-ant with who I completed another project using Libgdx together with shaders completed this tutorial series with simple descriptions. Mayby it will lead others to better understanding of what is happening in this code :)

General things about those tutorials:

* you need java 8 to launch examples
* examples needs gradle to build and launch, as libgdx itself needs it
* there are 7 parts, each touching different topic

Libgdx-Shaders_project
----------------------

If you would like to see how our final project looked like, take  a look here:

https://bitbucket.org/gmiejski/libgdx-shaders_project

Simple ship-based game with hitting boxes and flying in 3D space!

Structure
----------------------
* all shaders files reside in core/assets - this is where the first magic happens
* all libgdx files reside in core/src - this is where the second magic happens
* all examples can be run from corresponding desktop/src files


Running
----------------------
You can run those examples from IntelliJ, simply right click on selected file at desktop/src/... and click run.

Or launch them as a normal Java program, as those are simply running public static void main(...) functions

Contribution
----------------------
If you would like to contribute with next tutorial covering more topics - simply contact me on mail:
grz.miejski@gmail.com

Other sources of knowledge
----------------------
We're not covering everything in here as we don't want to copy knowledge from other sources.

Here are some links that helped us a lot in understanding this topic, so you should read them too when the time comes:

* [http://blog.xoppa.com](http://web.archive.org/web/20140101003556/http://blog.xoppa.com/?) - a must-read for everyone learning LibGDX. Contains very comprehensible, step-by-step tutorials
* [https://github.com/libgdx/libgdx/wiki/Shaders](https://github.com/libgdx/libgdx/wiki/Shaders) - describes what shaders are and responsibilities of vertex and fragment shaders - must read!
* [http://blog.db-in.com/cameras-on-opengl-es-2-x/](http://blog.db-in.com/cameras-on-opengl-es-2-x/) - great tutorial explaining matrix operations - must read!
* [http://www.learnopengles.com/](http://www.learnopengles.com/) - great source of OpenGL and LibGDX tutorials, especially for Android developers. Provides an explanation of [lighting](http://www.learnopengles.com/android-lesson-two-ambient-and-diffuse-lighting/)  


Special thanks
----------------------
Thanks to [mateusz-ant](https://github.com/mateusz-ant/) for his general help on those tutorials and during our project creation for university course :)