# FormTemplate
自定义表单
概念：
    根据自定义xml来实现类似与HTML中的输入框、单选、多选、日期、搜索、列表的界面展示
    根据JSON数据（内部实现采用Map）展示不同框的值
    获取对应Map数据转成JSON，可以用来接口提交
元素：
    表单文件： app工程assets文件夹下的xml文件就是自定义xml文件，节点名对应框类型，属性对应功能
    表单数据库配置文件：template的Module中的assets文件夹下的template_style.xml 用来组装搜索框的cusor语句
    表单业务功能配置文件：template的Module中的aseets文件夹下的template_business.xml 用来描述业务功能

实现思路：
    TemplateActivity类：提供客户端启动的Activity
    TemplateState类：采用状态模式简化Activity并提高了状态的扩展
    TemplateConfig类：表单静态库存放表单基本信息（如TemplateControl类的tag注解，与xml节点名相同）
    TemplateView类：自定义表单类（继承RecyclerView）
    TemplateAdapter类：实现RecyclerView的Adapter（onCreateViewHolder方法就是从TemplateConfig表单静态库中获取layput信息创建ViewHolder，
        onBindViewHolder方法也是从TemplateConfig表单静态库中获取对应的TemplateControl实例来绑定ViewHolder，此处采用MVC的模式）

    MVC模式：
        TemplateControl类：获取Template的属性，控制TemplateView、TemplateDialog的显示，监听并处理View、Holder事件，减少model、view的耦合
        TemplateView类：用来展示框的界面及事件
        TemplateDialog类：用来展示弹出框的界面及事件
        Template类：用来保存解析自定义表单xml文件的属性

    业务功能说明：


增加TemplateActivity：

