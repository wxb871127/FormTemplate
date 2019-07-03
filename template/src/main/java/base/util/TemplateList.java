package base.util;

import java.util.ArrayList;

import template.bean.BaseTemplate;

public class TemplateList extends ArrayList<BaseTemplate> {

    private static final long serialVersionUID = 1L;

    public TemplateList subList(Class<? extends BaseTemplate> cls) {
        TemplateList templates = new TemplateList();
        for (BaseTemplate template : this) {
            if (cls.isInstance(template)) {
                templates.add(template);
            }
        }

        return templates;
    }

    public BaseTemplate getTemplate(String name) {
        for (BaseTemplate template : this) {
            if (name.equals(template.name)) {
                return template;
            }
        }
        return null;
    }
}
