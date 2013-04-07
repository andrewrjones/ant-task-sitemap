ant-task-sitemap
================

This provides an [Ant](http://ant.apache.org/) task to create an XML Sitemap, as used by search engines.

SYNOPSIS
--------

```xml
<target name="generate_sitemap" description="generates the sitemap">
    <taskdef name="sitemap" classname="uk.co.arjones.ant.task.Sitemap" />
    <sitemap url="http://andrew-jones.com" destdir="${BUILD_DIR}" lastmod="now" gzip="yes">
        <fileset dir="${BUILD_DIR}">
            <include name="**.htm"/>
            <include name="**.html"/>
            <exclude name="google*"/>
        </fileset>
    </sitemap>
</target>
```

DESCRIPTION
-----------

I use Ant to package more than Java, including some static websites. Using this task, I can automatically generate an [XML Sitemap](http://en.wikipedia.org/wiki/Sitemaps).

### PARAMETERS
<table border="1" cellpadding="2" cellspacing="0">
  <tr>
    <td valign="top"><b>Attribute</b></td>
    <td valign="top"><b>Description</b></td>
    <td align="center" valign="top"><b>Required</b></td>
  </tr>
  <tr>
    <td valign="top">destdir</td>
    <td valign="top">Where we write the sitemap to.</td>
    <td align="center" valign="top">Yes</td>
  </tr>
  <tr>
    <td valign="top">gzip</td>
    <td valign="top">Produce a gzip sitemap?</td>
    <td align="center" valign="top">No - default is "false"</td>
  </tr>
  <tr>
    <td valign="top">index</td>
    <td valign="top">The index page of the website, which should just be written as the URL. This is a regex.</td>
    <td align="center" valign="top"><code>index.*</code></td>
  </tr>
  <tr>
    <td valign="top">lastmod</td>
    <td valign="top">If specified, we can try to generate a <code>lastmod</code> entry for the file. By passing in <code>now</code>, we will write the <code>lastmod</code> as the time when we build. By passing in <code>fromfile</code>, we will take the last modified time of the file.</td>
    <td align="center" valign="top">Don't write <code>lastmod</code> time.</td>
  </tr>
  <tr>
    <td valign="top">url</td>
    <td valign="top">The URL of the website.</td>
    <td align="center" valign="top">Yes</td>
  </tr>
</table>

### PARAMETERS SPECIFIED AS NESTED ELEMENTS
<table border="1" cellpadding="2" cellspacing="0">
  <tr>
    <td valign="top"><b>Attribute</b></td>
    <td valign="top"><b>Description</b></td>
    <td align="center" valign="top"><b>Required</b></td>
  </tr>
  <tr>
    <td valign="top">fileset</td>
    <td valign="top">Where should we look for files to add to the sitemap? This is the same as any other <a href="http://ant.apache.org/manual/Types/fileset.html">FileSet</a>.</td>
    <td align="center" valign="top">Yes</td>
  </tr>
</table>

INSTALL
-------

[Download](https://github.com/andrewrjones/ant-task-sitemap/downloads) the latest jar from GitHub and put it in your class path.

You will also need [SitemapGen4j](http://code.google.com/p/sitemapgen4j/) on your path.

TODO
----
- Implement change frequency
- Implement priority
- Generate text sitemap
- Generate ROR sitemap

Build status
------------

[![Build Status](https://secure.travis-ci.org/andrewrjones/ant-task-sitemap.png)](http://travis-ci.org/andrewrjones/ant-task-sitemap)
