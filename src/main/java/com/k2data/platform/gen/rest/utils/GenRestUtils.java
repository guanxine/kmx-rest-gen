package com.k2data.platform.gen.rest.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public class GenRestUtils {

    public static String getTemplateProjectPath() {
        return System.getProperty("user.dir") + "/src/main/resources/template";
    }

    static class PathLists {
        List<String> projectPaths = new ArrayList<>();
        List<String> classPaths = new ArrayList<>();



        public List<String> getProjectPaths() {
            return projectPaths;
        }

        public void setProjectPaths(List<String> projectPaths) {
            this.projectPaths = projectPaths;
        }

        public List<String> getClassPaths() {
            return classPaths;
        }

        public void setClassPaths(List<String> classPaths) {
            this.classPaths = classPaths;
        }
    }

    private static PathLists getTemplatePathsInProject(File filePath, PathLists pathLists) {
        File[] files = filePath.listFiles();
        if(files == null){
            return pathLists;
        }
        for(File f:files){
            if(f.isDirectory()){
                getTemplatePathsInProject(f,pathLists);
            }else{
                String path = f.getPath();
                path = "template" + path.substring(GenRestUtils.getTemplateProjectPath().length(), path.length());
                if(path.contains("${className}")) {
                  pathLists.getClassPaths().add(path);
                }
                else {
                    pathLists.getProjectPaths().add(path);
                }

            }
        }
        return pathLists;
    }

    public static PathLists getTemplates(){
        return getTemplatePathsInProject(new File(GenRestUtils.getTemplateProjectPath()), new PathLists());
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Configuration config, String destDir) {

        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String projectName = config.getString("projectName");
        String serviceName = config.getString("serviceName");
        String packageName = config.getString("package");
        Map<String, Object> map = new HashMap<>();
        map.put("projectName", projectName);
        map.put("package", packageName);

        map.put("mavenGroupId", config.getString("mavenGroupId"));
        map.put("mavenArtifactId", config.getString("mavenArtifactId"));
        map.put("mavenVersion", config.getString("mavenVersion"));

        map.put("serviceName", serviceName);
        map.put("servicePort", config.getInt("servicePort"));

        map.put("author", config.getString("author"));
        map.put("datetime", DateUtils.format(new Date()));

        String clasNames = config.getString("classNames");
        String[] split = clasNames.split(",");
        List<String> strings = Arrays.asList(split);
        map.put("classNames", strings);

        VelocityContext context = new VelocityContext(map);
        PathLists templates = getTemplates();
        for (String template : templates.getProjectPaths()) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            try {

                String fileName = getFileName(config.getString("serviceName"), template, projectName, config.getString("package"), "");

                File file = new File(destDir + fileName);
                if (file.exists()) {
                    file.delete();
                }
                File parentDir = file.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                file.createNewFile();

                if (fileName.endsWith(".sh")) {
                    file.setExecutable(true);
                }

                IOUtils.write(sw.toString(), new FileOutputStream(file), "UTF-8" );
            } catch (IOException e) {
                throw new RuntimeException("Failed to render template: " + template, e);
            }
        }


        for (String className : split) {
            map.put("className", className);
            map.put("className", className);
            map.put("lowerclass", className.toLowerCase());
            VelocityContext contextClass = new VelocityContext(map);
            for (String template : templates.getClassPaths()) {
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, "UTF-8" );
                tpl.merge(contextClass, sw);

                try {

                    String fileName = getFileName(serviceName, template, projectName, config.getString("package"), className);

                    File file = new File(destDir + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    File parentDir = file.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }

                    file.createNewFile();

                    if (fileName.endsWith(".sh")) {
                        file.setExecutable(true);
                    }

                    IOUtils.write(sw.toString(), new FileOutputStream(file), "UTF-8" );
                } catch (IOException e) {
                    throw new RuntimeException("Failed to render template: " + template, e);
                }
            }
        }
    }

    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    public static String getFileName(String serviceName, String template, String projectName, String packageName, String className) {
        if(template.lastIndexOf("vm") != -1) {
            String substring = template.substring("template".length(), template.length() - 3);
            return substring.replace("${projectName}", projectName)
                    .replace("${packageName}", packageName.replace(".", File.separator))
                    .replace("${serviceName}", serviceName)
                    .replace("${className}", className);
        }
        return "";
    }
}
