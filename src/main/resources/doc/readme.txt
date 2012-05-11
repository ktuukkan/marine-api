Java Marine API readme
Version @VERSION@ pre-alpha (b@BUILD_ID@)
<http://marineapi.sourceforge.net/>

Java Marine API is an NMEA 0183 library for accessing the data provided by 
electronic marine instruments such as GPS, sonar or autopilot.

SYSTEM REQUIREMENTS
 
 * Java 2 SE JRE/JDK 1.5 (1.6+ recommended)
 
 For serial port communication (choose one):
 
 * Java Communications API
   <http://www.oracle.com/technetwork/java/index-jsp-141752.html>
 
 * RXTX library
   <http://rxtx.qbang.org/>

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

The source code is freely available in project's SVN repository. See below URLs
and project homepage at SourceForge.net for more info.

SVN browsing and GNU tarball downloads:
<http://marineapi.svn.sourceforge.net/viewvc/marineapi/>

SVN checkout (no login required):
svn co https://marineapi.svn.sourceforge.net/svnroot/marineapi marineapi 

If you wish to contribute by adding new code or bug fixes, you may request for
SVN write access or provide patch file(s) via e-mail.

DEVELOPERS

* Kimmo Tuukkanen <ktuukkan@users.sourceforge.net>
  - project admin, lead developer

REFERENCES

The NMEA information for Java Marine API has been collected mainly from the
following resources (availability checked on 2011-11-22):

 * NMEA Revealed by Eric S. Raymond
   <http://catb.org/gpsd/NMEA.html>
    
 * The NMEA FAQ by Peter Bennett
   <http://vancouver-webpages.com/peter/nmeafaq.txt>
 
 * NMEA Sentence Information by Glenn Baddeley
   <http://home.mira.net/~gnb/gps/nmea.html>
 
 * NMEA Data by Dale DePriest
   <http://www.gpsinformation.org/dale/nmea.htm>
  
 * Wikipedia's NMEA article
   <http://en.wikipedia.org/wiki/NMEA>
   
 * The NMEA Information Sheet (issue 3, 25th Feb 2011) by Actisense
   <http://www.actisense.com/Downloads/TechTalk/NMEA%200183/The%20NMEA%200183%20Information%20Sheet.pdf>

Thanks to all above authors for making the information available. All
disclaimers and warnings concerning the accuracy of information in the above
documents apply equally to Java Marine API. When noticing or suspecting any
errors, please contact project author(s).
