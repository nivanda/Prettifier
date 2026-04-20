
# Itinerary Prettifier

This is a CLI based tool that converts hard to read itinerary text files into more human readable text files.


## Installing and running the project

### Required software/packages
```
1. Java Development Kit 22+
2. git
```

### Installation
Clone the project with this command in the terminal:

```
git clone https://github.com/nivanda/Prettifier.git
```
then:
```
cd Prettifier/
```
### Usage
The tool requires 3 arguments to work properly
```
java Prettifier.java [path to input] [path for output] [path to airport lookup]
```
So an example usage would be:
```
java Prettifier.java data/input.txt data/output.txt data/airport-lookup.csv
```
# Important Notes
1) The tool will not work with the default airport lookup file since the default file is utterly broken and the encoding is partially corrupted. Since how the program handles characters in the airport lookup file, the program won't accept any lookups with non english alphabet characters used. So french habibis and chinese comrades have to pack it up and use some other tool.

2) There is a ```BonusContent``` boolean at the top of Prettifier class. This will be false by default to ensure the program runs up to code. Please change that boolean to true (and remember to save afterward) to unlock bonus content. Please don't file bug reports relating to the program acting not up to code WHEN THE AFFORMENTIONED BOOLEAN IS SET TO TRUE!!!

# Bonus Content
The tool has some bonus features that you can access by switching the ```BonusContent``` boolean at the top of Prettifier class to ```true```

The bonus features currently added are:
```
1) -linebreakGen [path where to write file] argument | Run the program with this argument and path to generate an input file with the \v \r \f line break characters baked in.
2) ^ and ~ markers for ICAO/IATA codes | Add these characters in front of the codes the same way as you would add * symbol to specifi city name to get returned.
--------------------------------
^ = iso_country/Country ISO code
--------------------------------
~ = coordinates
--------------------------------
```