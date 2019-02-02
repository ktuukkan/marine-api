/**
 * NMEA 0183 sentence parsers. Notice that all parsers, excluding the common
 * base class are visible only within the package scope and not meant to be used
 * directly. Instead, use {@link net.sf.marineapi.nmea.parser.SentenceFactory}
 * and {@link net.sf.marineapi.nmea.io.SentenceReader} classes. This approach is
 * selected to enable registering additional parsers at runtime, by exending the
 * {@link net.sf.marineapi.nmea.parser.SentenceParser} and registering the new
 * parser in the factory.
 */
package net.sf.marineapi.nmea.parser;