# CPU Temps

This program takes a input file with temperatures readings of a number of CPU cores 
then produce the output file with least-squares approximation and interpolation functions 
for each core

## Installation
Note: When instruction says : Enter the command "some command", only use the part inside the quotation marks  
Example: Instruction says: Enter the command "cd C:\CPUTemps", cd C:\CPUTemps then should be typed  
  
Windows  
    1. Make sure the project folder directory is C:\CPUTemps  
    2. Open Command Prompt  
    3. Go into C:\CPUTemps directory (Enter the command: "cd C:\CPUTemps)  
    4. Enter the command "gradlew clean" to clean the project folder  
    5. Enter the command "gradlew build" to build the project  
    6. Enter the command "cd build\libs" to get to the executable jar file (CPUTemps_ttr.jar)  
  
## Usage
  
Windows  
    1. Copy the executable jar file (CPUTemps_ttr.jar) into preferred directory  
    2. Open Command Prompt  
    3. Go into the directory contains CPUTemps_ttr.jar  
    4. Enter the command "java -jar CPUTems_ttr.jar [temperatures_file]", temperatures_file is the path of the preferred input file  
    5. The output files for each core will be produced in same directory of CPUTemps_ttr.jar  

## Credits

Thomas J. Kennedy  
Thinh Tran  

## Resources
  
https://www.cs.odu.edu/~tkennedy/cs417/latest/Assts/project-cpu-temps/ 