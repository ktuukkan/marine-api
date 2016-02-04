Java Marine API [![Build Status](https://travis-ci.org/ktuukkan/marine-api.png)](https://travis-ci.org/ktuukkan/marine-api)
---------------

Java Marine API is an [NMEA 0183](http://en.wikipedia.org/wiki/NMEA_0183) parser library for Java. See project [webpage](http://ktuukkan.github.io/marine-api/) for more info and documentation.

Also, basic support for decoding [AIS messages](https://en.wikipedia.org/wiki/Automatic_Identification_System) is under development and included in latest snapshots for preview. As this is quite a large new feature, all feedback and contribution is much appreciated!

**Usage**

The API has been designed generic and easy to use instead for particular purpose. The NMEA stream translates into event/listener model. For example,

    SentenceReader reader = new SentenceReader(new FileInputStream(file)); // any stream will do
    reader.addSentenceListener(this); // "this" extends or implements sentence listener
    reader.start();

reads through a file and passes all or filtered sentences to listeners:

    public void sentenceRead(GGASentence gga) {
        System.out.println(gga.getPosition());
    }

See [examples](https://github.com/ktuukkan/marine-api/tree/master/src/main/java/net/sf/marineapi/example) for more.

**Snapshots**

Maven snapshots are deployed to [Central Repository](https://search.maven.org/) every now and then. Notice that although they should be mostly stable, they are still work-in-progress.

* [Snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/net/sf/marineapi/marineapi/)

Notice that you may need to [tweak your Maven settings](https://gist.github.com/ktuukkan/8cf2de1e915185118c60) to enable snapshot dependencies.

**Sourceforge**

Project was originally published in Sourceforge.net, hence the net.sf package naming.

* [Project page](http://sf.net/projects/marineapi/)
* [Downloads](https://sourceforge.net/projects/marineapi/files/Releases/)
