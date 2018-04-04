package com.k2data.platform.gen.rest;

import com.k2data.platform.gen.rest.service.GenService;
import com.k2data.platform.gen.rest.utils.GenRestUtils;
import com.k2data.platform.gen.rest.utils.GenUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipOutputStream;

/**
 * Created by guanxine on 18-4-3.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = getConfig();
        Scanner scanner = new Scanner(System.in);

        Configuration conf = getConfig();

        /*

projectName=k2platform-rest-demo1
serviceName=rest-demo1
servicePort=28084
mavenGroupId=com.k2data.platform
mavenArtifactId=k2platform-rest-demo1
mavenVersion=0.0.1-SNAPSHOT

#package
package=com.k2data.platform.demo1.rest
author=guanxine


         */
        String answer = null;

        System.out.print("project name: (k2platform-rest-demo)");
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "k2platform-rest-demo";
        }
        conf.addProperty("projectName", answer);


        System.out.print("service name: (rest-demo)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "rest-demo";
        }
        conf.addProperty("serviceName", answer);

        System.out.print("service port: (8080)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "8080";
        }
        conf.addProperty("servicePort", Integer.parseInt(answer));


        System.out.print("maven group id: (com.k2data.platform)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "com.k2data.platform";
        }
        conf.addProperty("mavenGroupId", answer);


        System.out.print("maven artifact id: (k2platform-rest-demo)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "k2platform-rest-demo";
        }
        conf.addProperty("mavenArtifactId", answer);


        System.out.print("maven version: (0.0.1-SNAPSHOT)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "0.0.1-SNAPSHOT";
        }
        conf.addProperty("mavenVersion", answer);


        System.out.print("package: (com.k2data.platform.rest.demo)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
           answer = "com.k2data.platform.rest.demo";
        }
        conf.addProperty("package", answer);

        System.out.print("class: (Demo)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "Demo";
        }
        conf.addProperty("classNames", answer);

        System.out.print("author: (guanxine)");
        answer = null;
        if(!scanner.hasNextLine() || StringUtils.isEmpty(answer = scanner.nextLine())) {
            answer = "guanxine";
        }
        conf.addProperty("author", answer);

        System.out.print("Is this ok? (yes)");

        answer = null;
        if(scanner.hasNextLine()
                && StringUtils.isNotEmpty(answer = scanner.nextLine())
                && (!"yes".equalsIgnoreCase(answer) || !"y".equalsIgnoreCase(answer))) {
            System.err.println("Aborted.");
            return;
        }

        System.out.println("Gen code to " +  System.getProperty("user.dir") + "/" + conf.getString("projectName"));
        genCode(conf, System.getProperty("user.dir"));
    }

    public static void genCode(Configuration config, String path) throws IOException {
//        File file = new File("./demo");
//        file.createNewFile();
//        OutputStream outputStream = new FileOutputStream(file);
//        ZipOutputStream zip = new ZipOutputStream(outputStream);
        GenRestUtils.generatorCode(config, path);
//        IOUtils.closeQuietly(outputStream);
    }


    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new RuntimeException("Failed to load generator.properties", e);
        }
    }
}
