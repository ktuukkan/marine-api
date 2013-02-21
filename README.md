Java Marine API "!https://travis-ci.org/ktuukkan/marine-api.png!":https://travis-ci.org/ktuukkan/marine-api
===============

Java Marine API is an NMEA 0183 parser library for Java. The goal of this project is to enable easy access to data provided by various marine instruments, such as GPS, sonar or autopilot. NMEA sentences can be read from any source that is accessed with input stream, eg. serial port, file, TCP/IP socket etc. The plain ASCII data is converted to events that can be listened in your application. Interfaces and parser implementations for selected sentences are provided and you can also implement and integrate your own by extending the provided base classes.

Currently the following sentences are supported:

    BOD - Bearing from origin to destination
    DBT - Water depth below transducer in meters, feet and fathoms.
    DPT - Water depth in meters with offset to transducer.
    GGA - GPS fix data
    GLL - Current geographic position and time
    GSA - Precision of GPS fix
    GSV - Detailed GPS satellite data
    HDG - Heading with magnetic deviation and variation
    HDM - Magnetic heading in degrees
    HDT - True heading in degrees
    MTA - Air temperature in degrees Celcius
    MTW - Water temperature in degrees Celcius
    MWV - Wind speed and angle
    RMB - Recommended minimum navigation information "type B"
    RMC - Recommended minimum navigation information "type C"
    RTE - GPS route data with list of waypoints
    VHW - Water speed and heading
    VTG - Course and speed over ground
    WPL - Destination waypoint location and ID
    ZDA - UTC time and date with local time offset

In addition to data input, library also supports output by allowing the modification of sentence contents with data formatting and validation.

The project has just recently been migrated in Github from Sourceforge. The old site, javadocs and downloads are still there:

Old site:
http://marineapi.sourceforge.net/

Downloads:
https://sourceforge.net/projects/marineapi/files/Releases/
