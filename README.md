[![Build Status](https://travis-ci.org/ktuukkan/marine-api.png)](https://travis-ci.org/ktuukkan/marine-api)

Java Marine API
===============

Java Marine API is an [NMEA 0183](http://en.wikipedia.org/wiki/NMEA_0183) parser library for Java. The goal of this project is to enable easy access to data provided by various electronic marine devices such as GPS, sonar or weather instruments.

NMEA sentences can be read from any source that is accessed with input stream, eg. serial port, file, TCP/IP socket etc. The plain ASCII data is converted to event/listener model with interfaces and parser implementations for selected sentences. Custom parsers may also be integrated by extending the provided base classes. In addition to data input, output is also supported by allowing the modification of sentence contents with data formatting and validation.

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

Project was published originally in Sourceforge.net and main web page, javadocs and downloads are still there but the old SVN repository is no longer updated.

sf.net:
http://marineapi.sf.net/
http://sf.net/projects/marineapi/

downloads:
https://sourceforge.net/projects/marineapi/files/Releases/
