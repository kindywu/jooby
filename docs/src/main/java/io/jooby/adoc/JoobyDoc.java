package io.jooby.adoc;

import io.jooby.Jooby;
import org.apache.commons.io.FileUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JoobyDoc {
  public static final Object VERSION = "2.0.0-Alpha1";

  public static void main(String[] args) throws IOException {
    Asciidoctor asciidoctor = Asciidoctor.Factory.create();

    Attributes attributes = new Attributes();
    attributes.setAttribute("docinfo", "shared");
    attributes.setAttribute("joobyVersion", VERSION);
    //:source-language: asciidoc
    //:imagesdir: images
    //:source-highlighter: highlightjs
    //:highlightjs-theme: agate
    //:favicon: images/favicon96.png
    //:love: &#9825;
    //:javadoc: https://static.javadoc.io/io.jooby/jooby/{joobyVersion}/io/jooby/
    attributes.setAttribute("title", "jooby: do more! more easily!!");
    attributes.setAttribute("toc", "left");
    attributes.setAttribute("toclevels", "3");
    attributes.setAttribute("sectanchors", "");
    attributes.setAttribute("sectlinks", "");
    attributes.setAttribute("sectnums", "");
    attributes.setAttribute("sectnumlevels", "3");
    attributes.setAttribute("linkattrs", "");
    attributes.setAttribute("nofooter", "");
    attributes.setAttribute("idprefix", "");
    attributes.setAttribute("idseparator", "-");
    attributes.setAttribute("icons", "font");
    attributes.setAttribute("description", "The modular micro web framework for Java");
    attributes.setAttribute("keywords",
        "Java, Modern, Micro, Web, Framework, Reactive, Lightweight, Microservices");
    attributes.setAttribute("imagesdir", "images");
    attributes.setAttribute("source-highlighter", "highlightjs");
    attributes.setAttribute("highlightjs-theme", "agate");
    attributes.setAttribute("favicon", "images/favicon96.png");
    attributes.setAttribute("love", "&#9825;");

    Path basedir = Paths.get(System.getProperty("user.dir"), "docs");
    Path outdir = basedir.resolve("out");

    Options options = new Options();
    options.setBackend("html");

    options.setAttributes(attributes);
    options.setBaseDir(basedir.toAbsolutePath().toString());
    options.setDocType("book");
    options.setToDir(outdir.getFileName().toString());
    options.setMkDirs(true);
    options.setDestinationDir("out");
    options.setSafe(SafeMode.SAFE);

    /** Wipe out directory: */
    FileUtils.cleanDirectory(outdir.toFile());

    /** Copy /images and /js: */
    copyFile(outdir,
        // images
        basedir.resolve("images"),
        // js
        basedir.resolve("js")
    );

    asciidoctor.convertFile(basedir.resolve("index.adoc").toFile(), options);
  }

  private static void copyFile(Path out, Path... dirs) throws IOException {
    for (Path dir : dirs) {
      FileUtils.copyDirectory(dir.toFile(), out.resolve(dir.getFileName().toString()).toFile());
    }
  }
}
