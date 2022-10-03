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

# maven构建方式

```
    //task sourceJar(type: Jar) {
//    from android.sourceSets.main.java.getSrcDirs() // 源码路径
//    archiveClassifier = "sources"
//}

// components.release 只有在配置完成之后，才能拿到值
afterEvaluate {
    publishing {
        repositories { RepositoryHandler handler ->
            handler.mavenLocal()
            handler.maven {
                url "${rootDir}/repo"
            }
            // 仓库用户名密码
            // handler.maven { MavenArtifactRepository mavenArtifactRepository ->
            //     // maven 仓库地址
            //     url 'http://xx.xxx.xx.xx:8081/repository/core/'
            //     // 访问仓库的 账号和密码
            //     credentials {
            //         username = "lr"
            //         password = "123456"
            //     }
            // }
        }
        publications { PublicationContainer publicationContainer ->
            // Creates a Maven publication called "release".
            release(MavenPublication) {
                // Applies the component for the release build variant.
                from components.release // 注释1:使用 Android Gradle 插件生成的组件，作为发布的内容
//                artifact sourceJar // 上传源码
                // Library Package Name (Example : "com.frogobox.androidfirstlib")
                // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
                groupId = 'ren.nearby.share_export'
                // Library Name / Module Name (Example : "androidfirstlib")
                // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
                artifactId = 'share-export'
                // Version Library Name (Example : "1.0.0")
                version = '1.0.0'

                //指定路径 share_export-release.aar
//                artifact "build/outputs/aar/${project.getName()}-release.aar"
            }
        }
    }
}
```

```
之前版本设置aar路径

Gradle 7.0+ 设置aar路径

方式一
        移除 repositories { flatDir { dirs 'libs' } }

        移除 implementation fileTree(dir: 'libs', include: ['*.jar'])

        implementation files('libs/AarLib_V1.0.0.aar')

方式二
//    implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
//    implementation fileTree(include: ['*.jar', "*.aar"], dir: 'libs/aars')


之前版本设置aar路径

方式一
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
    implementation fileTree(dir: 'libs', include: ['*.jar'])

    implementation(name: 'AarLib_V1.0.0', ext: 'aar')

方式二

    implementation fileTree(dir: "libs", include: [".jar", ".aar"])

```






# 动态依赖本地组件与远程依赖aar步骤

## 1.项目根目录创建module_dependency.json文件

{
"//useModuleAll": "true-全部使用本地依赖 false-根据useModule决定依赖",
"//useModule": "true-当前组件使用本地依赖 false-当前组件使用远程aar依赖",
"//module_name": "本地组件名称",
"//module_dir": "本地组件路径",
"//module_group": "远程aar依赖路径",
"useModuleAll": false,
"list": [
{
"useModule": false,
"module_name": "share_export",
"module_dir": "../nearby-share/share_export",
"module_group": "ren.nearby.share_export:share-export"
},
{
"useModule": false,
"module_name": "share_module",
"module_dir": "../nearby-share/share_module",
"module_group": "ren.nearby.share_module:share-module"
}
]
}
## 2.项目根目录创建module_dependency.gradle文件

```
import groovy.json.JsonSlurper
// 应用该文件的时候，执行该方法
includeAndSwitchModuleDep()

def includeAndSwitchModuleDep() {
try {
    // module依赖的配置信息文件路径，json格式
    def moduleDepConfigDir = "module_dependency.json"
    // 解析json配置
    def json = file(moduleDepConfigDir).getText()
    def jsonSlurper = new JsonSlurper()
    def obj = jsonSlurper.parseText(json)
    println "obj=$obj"
    // 遍历配置信息列表，找出 useModule==true 的模块，动态include
    if (obj.useModuleAll){
        obj.list.forEach{
        include ":${it.module_name}"
        project(":${it.module_name}").projectDir = file(it.module_dir)
        }
    }else {
        obj.list.forEach{
        if (it.useModule) {
        include ":${it.module_name}"
        project(":${it.module_name}").projectDir = file(it.module_dir)
        }
    }
}
    // aar依赖 与 module依赖 动态切换
    switchToModuleDependency(obj)
        } catch (Exception e) {
        e.printStackTrace()
    }
}

def switchToModuleDependency(Object moduleDepObj){
// projectsEvaluated方法是所有module构建完成后执行的回调
gradle.projectsEvaluated {
gradle.rootProject.subprojects {
println "project name is $it"

            project.configurations.all { Configuration ->
                resolutionStrategy {
                    dependencySubstitution {
                        if (moduleDepObj.useModuleAll){
                            moduleDepObj.list.forEach {
                                substitute module(it.module_group) with project(":${it.module_name}")
                            }
                        }else {
                            moduleDepObj.list.forEach {
                                if (it.useModule) {
                                    substitute module(it.module_group) with project(":${it.module_name}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
```
## 3.在module gradle添加远程依赖库
implementation('ren.nearby.share_export:share-export:1.0.0')


## 4.应用动态本地module或者远程方式在项目根目录下settings.gradle配置
try {
apply from: 'module_dependency.gradle'
} catch (Exception e) {
e.printStackTrace()
}




# 'maven-publish' GitHubPackages 构建 远程aar maven 仓库步骤

## 1.在module目下创建maven.gradle文件

apply plugin: 'maven-publish'

task sourceJar(type: Jar) {
println("上传代码到maven仓库源码路径：${android.sourceSets.main.java.getSrcDirs()}")
from android.sourceSets.main.java.getSrcDirs() // 源码路径
archiveClassifier = "sources"
}

afterEvaluate {
publishing {
// 配置maven仓库 仓库地址可配置多个 执行publish任务时 会把aar包发步到所有指定的maven仓库
repositories { RepositoryHandler handler ->

            if (!isRemoteAar.toBoolean()) {
                // 指定路径的本地仓库
                handler.mavenLocal()
                handler.maven {
                    url "${rootDir}/repo"
                }
            } else {
                // 远程仓库
                handler.maven { MavenArtifactRepository mavenArtifactRepository ->
                    name = "GitHubPackages"
                    // maven 仓库地址
                    url = uri(mavenUrl)
                    // 访问仓库的 账号和密码
                    credentials {
                        username = mavenUsername
                        password = mavenPassword
                    }
                }
            }
        }
        publications { PublicationContainer publicationContainer ->
            // Creates a Maven publication called "release".
            release(MavenPublication) {
                // Applies the component for the release build variant.
                from components.release // 注释1:使用 Android Gradle 插件生成的组件，作为发布的内容
                //                artifact sourceJar // 上传源码
                // Library Package Name (Example : "com.frogobox.androidfirstlib")
                // NOTE : Different GroupId For Each Library / Module, So That Each Library Is Not Overwritten
                groupId = 'ren.nearby.share_export'
                // Library Name / Module Name (Example : "androidfirstlib")
                // NOTE : Different ArtifactId For Each Library / Module, So That Each Library Is Not Overwritten
                artifactId = 'share-export'
                // Version Library Name (Example : "1.0.0")
                version = '1.0.0'

                //指定路径 share_export-release.aar
                //artifact "build/outputs/aar/${project.getName()}-release.aar"
            }
        }
    }
}

## 2.在项目根目录下gradle.properties文件配置以下内容
# maven授权上传配置
mavenUrl=https://maven.pkg.github.com/nearbyren/nearby-share 【https://maven.pkg.github.com/GitHub账户名/对应的库名称】
mavenUsername=nearbyren     【github用户名】
mavenPassword=      【此处去github申请token】
android.disableAutomaticComponentCreation=true

# true-正式发布环境 false-本地调试环境
isRemoteAar=true







