package base.util;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import template.bean.BaseTemplate;
import template.bean.SectionTemplate;
import template.config.TemplateConfig;

public class TemplateParse {
    private static Element templateStyleElement = null;

    public static void initTemplateStyle(Context context){
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("template_styles.xml");
            templateStyleElement = getDocumentElement(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void initTemplateStyle(Context context, String styleXml){
        try {
            InputStream inputStream = null;
            File file = new File(styleXml);
            if(file.exists())
                inputStream = new FileInputStream(file);
            if(inputStream != null)
                templateStyleElement = getDocumentElement(inputStream);
            else {
                inputStream = context.getAssets().open(styleXml);
                templateStyleElement = getDocumentElement(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initTemplateStyle(Context context, InputStream styleXml){
        templateStyleElement = getDocumentElement(styleXml);
    }

    public static Element getTemplateStyleElement(){
        return templateStyleElement;
    }

    public static TemplateList parseTemplateFile(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            File file = new File(fileName);
            if(file.exists())
                inputStream = new FileInputStream(file);
            if(inputStream != null)
                return parseStream(inputStream);
            inputStream = context.getAssets().open(fileName);
            return parseStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static TemplateList parseStream(InputStream inputStream) {
        return parseElement(getDocumentElement(inputStream));
    }

    public static TemplateList parseElement(Element rootElement) {
        return parseElement(rootElement, null);
    }

    public static TemplateList parseElement(Element rootElement, SectionTemplate sectionTemplate) {
        TemplateList templates = new TemplateList();
        if (rootElement == null) {
            return templates;
        }

        try {
            NodeList nodeList = rootElement.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (!(node instanceof Element)) {
                    continue;
                }
                Element element = (Element) node;

                String tagName = element.getTagName();
                Class<? extends BaseTemplate> templateClass = TemplateConfig.getTemplateByTag(tagName);
                if (templateClass != null) {
                    BaseTemplate template = templateClass.newInstance();
                    template.parseElement(element);
                    Element e = (Element) element.getParentNode();
                    template.parentName = e.getAttribute("name");
                    if (sectionTemplate != null) {
                        template.setSectionTemplate(sectionTemplate);
                    }
                    templates.add(template);

                    if ("section".equals(element.getTagName())) {
                        templates.addAll(parseElement(element, (SectionTemplate) template));
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return templates;
    }

    public static Element getDocumentElement(InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            return doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
