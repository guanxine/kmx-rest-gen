package com.k2data.platform.gen.rest.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenRestUtils {

    public static String getTemplateProjectPath() {
        return System.getProperty("user.dir") + "/src/main/resources/template";
    }

    public static void main(String[] args) {
        String relativelyPath=System.getProperty("user.dir");
        String resourcesPath = relativelyPath + "";
        List<String> paths1 = getTemplatePathsInProject(new File(GenRestUtils.getTemplateProjectPath()), new ArrayList<String>());
        for (String path : paths1){
            System.out.println(path);
        }


        String path = "/home/guanxine/work/git/k2platform-gen/src/main/resources/template/${projectName}/src/main/resources/${serviceName}-spring-cxf.xml.vm";

        String substring = path.substring(GenRestUtils.getTemplateProjectPath().length(), path.length() - 3);
        String replace = substring.replace("${projectName}", "k2platform-file-rest");
        System.out.println(replace.replace("${serviceName}", "file-rest"));
        System.out.println(replace.replace("${serviceName1}", "file-rest"));
    }

    private static List<String> getTemplatePathsInProject(File filePath, List<String> filePaths) {
        File[] files = filePath.listFiles();
        if(files == null){
            return filePaths;
        }
        for(File f:files){
            if(f.isDirectory()){
                getTemplatePathsInProject(f,filePaths);
            }else{
                String path = f.getPath();
                filePaths.add("template" + path.substring(GenRestUtils.getTemplateProjectPath().length(), path.length()));
            }
        }
        return filePaths;
    }

    public static List<String> getTemplates(){
        return getTemplatePathsInProject(new File(GenRestUtils.getTemplateProjectPath()), new ArrayList<String>());
    }

    /**
     * 生成代码
     */
    public static void generatorCode(ZipOutputStream zip, Configuration config) {

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
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        List<String> templates = getTemplates();
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            try {

                String fileName = getFileName(template, projectName, config.getString("package"), config.getString("serviceName"));
                System.out.println(template + ":" + fileName);
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("Failed to render template: " + template, e);
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

    public static String getFileName(String template, String projectName, String packageName, String serviceName) {
        if(template.lastIndexOf("vm") != -1) {
            String substring = template.substring("template".length(), template.length() - 3);
            return substring.replace("${projectName}", projectName)
                    .replace("${packageName}", packageName)
                    .replace("${serviceName}", serviceName);
        }
        return "";
    }
}
