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
        System.out.print("project name: ");
        if(scanner.hasNextInt()) {
            System.out.println("num:" + scanner.nextInt() + ",");
        }

//        String answerString = null;
//        int answerInt = 8080;
//
//        System.out.print("project name: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("project name should not be empty!");
//            return;
//        }
//        conf.addProperty("projectName", answerString);
//
//        System.out.print("service name: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("service name should not be empty!");
//            return;
//        }
//        conf.addProperty("serviceName", answerString);
//
//
//        System.out.print("service port: (8080)");
//        if(!scanner.hasNextLine() || (answerInt = scanner.nextInt()) == null) {
//            System.err.println("service port should not be empty!");
//            return;
//        }
//        conf.addProperty("servicePort", answerInt);
//
//
//        System.out.print("maven group id: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("maven group id should not be empty!");
//            return;
//        }
//        conf.addProperty("mavenGroupId", answerString);
//
//
//        System.out.print("maven artifact id: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("maven artifact id should not be empty!");
//            return;
//        }
//        conf.addProperty("mavenArtifactId", answerString);
//
//
//        System.out.print("maven version: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("maven version should not be empty!");
//            return;
//        }
//        conf.addProperty("mavenVersion", answerString);
//
//
//        System.out.print("package: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("package should not be empty!");
//            return;
//        }
//        conf.addProperty("package", answerString);
//
//        System.out.print("author: ");
//        if(!scanner.hasNextLine() || StringUtils.isEmpty(answerString = scanner.nextLine())) {
//            System.err.println("author should not be empty!");
//            return;
//        }
//        conf.addProperty("author", answerString);
    }

    public static void genCode(Configuration config) throws IOException {
        File file = new File("./demo");
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        GenRestUtils.generatorCode(zip, config);
        IOUtils.closeQuietly(zip);
    }


    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new RuntimeException("Failed to load generator.properties", e);
        }
    }
}
