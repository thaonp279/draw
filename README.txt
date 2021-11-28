_______________________________
FEATURES:
_______________________________
General
1. Modes
    Choose from 2 modes: draw and select mode.
        - Choose draw mode when drawing new shapes.
        - Choose select mode when changing their fill and color or their coordinates.
    Instructions:
        - Toggle the Select button.
        - Select mode is by default off.

2. Undo
    Undo change and go back to up to 10 previous states.
    Instructions: click the Undo button.
    
3. Redo
    Redo a state that has been undone. Can redo up to 10 undone states until a new change is made.
    Instructions: click the Redo button.

4. Clear
    Clear all shapes on canvas.
    Instructions: click the Clear button.

_______________________________
Draw mode
1. Shape types:
    Choose from 7 different shapes including:
        - 4 standard shapes (line, rectangle, ellipse, diagonal cross).
        - 3 extension shapes (heart, triangle, moon).
    Instructions:
        - Select one of the shape types by clicking on its button on the toolbar.
        - Start drawing by clicking on the canvas and dragging the mouse to any 4 directions.

2. Fill type:
    Choose from 2 fill types: solid fill or outline.
    Instructions:
        - By default, fill is off.
        - To enable, click on the Fill button. Click again to disable.

3. Color:
    Choose from 5 color options.
    Instructions:
        - Toggle through the color options  by clicking on the color button.

4. Aspect ration (extension):
    Preserve aspect ratio while drawing the shape.
    Instructions: hold down the SHIFT key while drawing.
_______________________________    
Select mode (extension)
Select a shape on the canvas and change its color, fill, coordinate properties.
Instructions:
    - Click on the shape.
    - Toggle the Fill or Color buttons to change their property.
    - Drag the shape to a new location on the canvas.

_______________________________
RUN THE PROGRAM
_______________________________
1. Compile the program with terminal line
cd src
find main -name '*.java' > sources.txt
javac @sources.txt

2. Run the program
java main.Main

3. Draw something :)
4. Close the window to stop the program
_______________________________
RUN THE TESTS
_______________________________
cd src
mkdir target
find . -name '*.java' > sources.txt
javac -d target -cp target:junit-platform-console-standalone-1.8.1.jar @sources.txt
java -jar junit-platform-console-standalone-1.8.1.jar --class-path target --scan-class-path