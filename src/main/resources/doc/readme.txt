Java Marine API - README
Version @VERSION@ pre-alpha (b@BUILD_ID@)
<http://ktuukkan.github.io/marine-api/>

Java Marine API is an NMEA 0183 library for accessing the data provided by 
electronic marine instruments such as GPS, echo sounder and weather instruments.

SYSTEM REQUIREMENTS
 
 * Java 2 SE JRE/JDK 1.5 (1.6+ recommended)
 
 For serial port communication (choose one):
 
 * Java Communications API
   <http://www.oracle.com/technetwork/java/index-jsp-141752.html>
 
 * RXTX library
   <http://rxtx.qbang.org/>

 * Neuron Robotics Java Serial Library
   <http://code.google.com/p/nrjavaserial/>

LICENSING

Java Marine API is free software: you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

Java Marine API is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
for more details.

You should have received a copy of the GNU Lesser General Public License
along with Java Marine API. If not, see <http://www.gnu.org/licenses/>.

DISCLAIMER

Java Marine API is not official NMEA software or related to National Marine
Electronics Association <http://www.nmea.org/>. The interpretation of NMEA
sentences for this library is based entirely on freely available resources in
the Internet. Thus, it is NOT guaranteed that the library would conform and
follow the standard accurately.

SOURCE CODE

The source code is freely available in Github.

Browse code at Github.com:
<https://github.com/ktuukkan/marine-api>

Repository URL (read-only):
git://github.com/ktuukkan/marine-api.git

If you wish to contribute new code or bug fixes, please fork and send pull
requests in Github.

DEVELOPERS

* Kimmo Tuukkanen <kimmo.tuukkanen@gmail.com>
  - project admin, lead developer

REFERENCES

The NMEA 0183 information for Java Marine API has been acquired from following
documents publicly available in the Internet (URLs checked on 2013-09-08):

 * NMEA Revealed by Eric S. Raymond
   <http://catb.org/gpsd/NMEA.html>

 * The NMEA FAQ by Peter Bennett
   <http://vancouver-webpages.com/peter/nmeafaq.txt> (not found)
   <http://www.eoss.org/pubs/nmeafaq.htm> (archived older version)
 
 * NMEA Sentence Information by Glenn Baddeley
   <http://home.mira.net/~gnb/gps/nmea.html>
 
 * NMEA Data by Dale DePriest
   <http://www.gpsinformation.org/dale/nmea.htm>
  
 * NMEA 0183 article in Wikipedia
   <http://en.wikipedia.org/wiki/NMEA_0183>
   
 * The NMEA Information Sheet (issue 3, 25th Feb 2011) by Actisense
   <http://www.actisense.com/products/nmea-0183/opto-4/downloads-opto-4.html>

 * PB100 WeatherStation Technical Manual by Airmar 
   <http://www.airmartechnology.com/uploads/installguide/PB100TechnicalManual_rev1.007.pdf>

All warnings concerning the accuracy of information in above documents apply
equally to Java Marine API.
