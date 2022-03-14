# Vector Graphics Editor
<img width="700" alt="Screen Shot 2021-11-28 at 14 42 52" src="https://user-images.githubusercontent.com/77321721/143773918-15e5f255-e3b9-4786-bcc6-bf7d8fce7179.png">

## FEATURES:
#### General
1. Modes: draw and select mode.
- Choose draw mode when drawing new shapes.
- Choose select mode when changing an old shape's color, fill, position.

2. Undo change and go back to up to 10 previous states.
    
3. Redo a state that has been undone.

4. Clear all shapes on canvas.
    Instructions: click the Clear button.

#### Draw mode
1. Shape types: 7 different shapes

2. Fill type: solid fill or outline.

3. Color: 5 color options.

4. Preserve aspect ratio while drawing the shape by holding down SHIFT.

#### Select mode
Select a shape on the canvas and change its color, fill, coordinate properties.

## Run the Program
1. Compile the program with terminal line\
cd src\
find main -name '*.java' > sources.txt\
javac @sources.txt

2. Run the program
java main.Main

## Run the Tests
cd src\
mkdir target\
find . -name '*.java' > sources.txt\
javac -d target -cp target:junit-platform-console-standalone-1.8.1.jar @sources.txt\
java -jar junit-platform-console-standalone-1.8.1.jar --class-path target --scan-class-path
