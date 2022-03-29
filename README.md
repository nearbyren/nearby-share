# JitPack 步骤

步骤一 项目根目录 build.gradle 添加

classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'


------------------------------------------------------------------------------------------------------------------------


步骤二 项目module目录 build.gradle 添加

plugins { //... id 'maven-publish' }

afterEvaluate { publishing { publications { release(MavenPublication) { from components.release
groupId = 'ren.nearby.ren.lib.http' artifactId = 'http' version = '1.0.0' } } } }

# ARouter kotlin 步骤

### 基础步骤

    app -> module  分别在  plugin  kapt  dependencies 添加如下：

```
plugins {
    id 'kotlin-kapt'
}


kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
}



dependencies {
   
   implementation 'com.alibaba:arouter-api:1.5.2'
   kapt 'com.alibaba:arouter-compiler:1.5.2'

}

```

### 常用步骤二

```
  application 初始化
  
  
        //初始化koin
        if (BuildConfig.DEBUG) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()// 打印日志
            ARouter.openDebug()// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)// 尽可能早，推荐在Application中初始化


    //activity 标记路径方式 至少要两级 xx/xx
    @Route(path = "/share/xxx")
    class RecommendAct : BaseActivityKot() { 
        //取参数方式
        @Autowired(name = "lr")
        lateinit var lr: String
    }
    
    
    
   //无参数启动路由    
  ARouter.getInstance().build("/share/xxx").navigation()
  
  //有参数启动路由    
  ARouter.getInstance().build("/share/xxx").withString("lr", "lr").withString("job", "job").navigation()
  
  
  
添加混淆规则(如果使用了Proguard)
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider




    Scheme方式

    Uri uri = getIntent().getData();
    ARouter.getInstance().build(uri).navigation();
    
    
    
// 更新 build.gradle, 添加参数 AROUTER_GENERATE_DOC = enable
// 生成的文档路径 : build/generated/source/apt/(debug or release)/com/alibaba/android/arouter/docs/arouter-map-of-${moduleName}.json
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName(), AROUTER_GENERATE_DOC: "enable"]
            }
        }
    }
}
```






