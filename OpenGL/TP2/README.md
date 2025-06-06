# Introduction to OpenGL

- Building
  * [Windows](#windows)
  * [Linux](#linux)
  * [MacOs](#macos)
- [Adding new files](#Adding-new-files-to-the-build-systems)

---

## Windows

### Prerequisites

* download and install the latest version of CMake

   * download here (choose "Windows x64 Installer:"): https://cmake.org/download/ 
   
   * !!! When installing make sure that the checkbox "ne pas ajouter cmake au PATH" is NOT checked
 

* if you don't have it already, download and install MS Visual Studio Community Edition (free for students): https://visualstudio.microsoft.com/downloads/

    * install instructions here: https://docs.microsoft.com/en-us/cpp/build/vscpp-step-0-installation?view=vs-2019
    
    * !!! install the "Desktop development with C++"
    
    * If you have VS already installed, you can go in **Tools** --> **Get Tools and Features...** to install "Desktop development with C++" if it is missing.


### Create the Visual Studio Solution. 

This step enables you to create the project file to load inside VS:

* unzip the code inside a folder. *Avoid to place the code in folders with spaces and accented characters*.

* open a Terminal and go to the directory containing the code.

* execute:

  * `md build`
  
  * `cd build`
  
  * `cmake -G "Visual Studio 16 2019" -A x64 -DCMAKE_BUILD_TYPE:STRING=Release ..`
  
  * `dir`
  
  > if you had a different version of VS installed (not the latest) you may need to adapt the string `Visual Studio 16 2019` to your version: e.g. Visual Studio 15 2017, Visual Studio 14 2015, Visual Studio 12 2013
  
* if everything went well you should find a file named `tp1.sln` inside the directory.


### Compile, build, execute 

* open `tp1.sln` inside VS either by double clicking on it or opening from inside VS

* build the solution (**Build Solution** from the **Build menu**)

* from the tp directory copy `freeglut\bin\x64\freeglut.dll` in `build\Release`

* execute the code:  

  * Select the project you want to run (e.g. `helloteapot`), right click on it and select **Set as Startup Project** 
  
  * On the menu bar, choose **Debug** --> **Start without debugging**.)

(see https://docs.microsoft.com/en-us/cpp/build/vscpp-step-2-build?view=vs-2019 for how to build, execute, etc)


### Editing the code

Edit the code according to the assignments that are given, rebuild the solution and execute. 

> !!! You need to run the cmake line only **once**

> !!! You need to copy the dll file only **once**.

---

## Linux

### Prerequisites

In order to develop with OpenGL some system packages are required (unless you are using the N7 machines):

```
sudo apt-get install libglu1-mesa-dev freeglut3-dev build-essential mesa-common-dev libxi-dev libxmu-dev automake
```

To build this code we use the CMake build system. You can install CMake from the system package manager but you need a recent version >= 3.10. Check the version that is provided by your linux distribution and if it is suitable usually you need to 

```
sudo apt-get install cmake
```

otherwise you can install the binaries from here (choose Linux x86_64): https://cmake.org/download/ 

Once downloaded, in order to install:
```
chmod +x cmake-X.YY.Z-Linux-x86_64.sh
sudo ./cmake-X.YY.Z-Linux-x86_64.sh --prefix=/usr/local/ --skip-license
```
  
### Build
 
To compile and build the code you do 

 ```
 mkdir build && cd build
 cmake ..
 make <name_file_without_cpp>
 ```

Also,

```
make all
```
builds everything, and

```
make clean
```
cleans everything.

Execute the code:

```
./helloteapot
```

### Editing the code

Edit the code as required and then

```
make  <name_file_without_cpp>
```

> !!! the cmake line has to be run only **once**

---

## macOS

### Prerequisites

In order to develop with OpenGL check if 

```
ls  /System/Library/Frameworks/
```
contains OpenGL and GLUT frameworks.
If not you need to install XCode  from the `Mac App Store`, see here for more details https://developer.apple.com/support/xcode/

Install the latest version of CMake by downloading https://github.com/Kitware/CMake/releases/download/v3.17.1/cmake-3.17.1-Darwin-x86_64.dmg

### Build
 
 Same as Linux.
 
### Editing the code
 
 Same as Linux.
 
---

## Adding new files to the build systems
 
 * Create the new file (helloteapot2.cpp, navigator.cpp)
 
 * [Windows only] close VS
 
 * Edit `CMakeLists.txt` and uncomment (remove the `#` at the beginning) the lines relevant to the file
 
 * in the terminal, from the `build` directory run `cmake ..`
 
 * [Windows only] reload the solution file, now a new `helloteapot2` or `navigator` target should appear. Build/execute as usual
 
 * [other os] build and execute as usual, i.e. `make navigator`, `./navigator` ...
 