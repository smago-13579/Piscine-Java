#1. Create directory:
    mkdir target
#2. Compile files to the 'target' directory:
    javac -d target src/java/edu/school21/printer/app/App.java src/java/edu/school21/printer/logic/Display.java
#3. Copy resources:
    cp -r src/resources ./target/resources
#4. Create jar file:
    jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .
#5. Run program:
    java -jar target/images-to-chars-printer.jar . 0
