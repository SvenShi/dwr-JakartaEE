# 本次改造主要目的为适配 Java 17 和 Spring Boot 3.0

- **个人简单修改版，用于实际生产项目改造升级，目前仍未实际投产请谨慎选择使用！**
- **目前个人简单测试后可以正常工作，实际情况请自行测试！**
- **安装方法见下面的 `Install to Maven` 章节**
  <br/>

# The main purpose of this transformation is to adapt to Java 17 and Spring Boot 3.0
- **A simple modified version for personal use, used for transformation and upgrading of actual production projects. It has not yet been put into actual production, please choose to use it with caution!**
- **At present, it can work normally after a simple personal test. Please test it yourself for actual conditions!**
- **For installation methods, see the `Install to Maven` chapter below.**

# 改造列表
加粗新增，划线删除
## JavaEE -> JakartaEE
- 使用idea自带迁移功能将javax包名迁移到jakarta包名
- ~~annotations-api.jar~~ -> **jakarta.annotation-api-3.0.0-M1.jar**
- ~~javax.servlet-api-3.0.jar~~ -> **jakarta.servlet-api-6.1.0-M1.jar**
- ~~jsp-api-2.0.jar~~ -> **jakarta.servlet.jsp-api-4.0.0-M1.jar**
- ~~javax.jms-1.1.jar~~ -> **jakarta.jms-api-3.1.0.jar**
- ~~commons-fileupload-1.2.1.jar~~ -> **commons-fileupload2-core-2.0.0-M1.jar** + **commons-fileupload2-jakarta-2.0.0-M1.jar**
- ~~jms-1.1.jar~~
## Jetty 7.0.1 -> Jetty 12.0.4
- ~~jetty-http-7.0.1.v20091125.jar~~ -> **jetty-http-12.0.4.jar**
- ~~jetty-io-7.0.1.v20091125.ja~~r -> **jetty-io-12.0.4.jar**
- ~~jetty-server-7.0.1.v20091125.jar~~ -> **jetty-server-12.0.4.jar**
- ~~jetty-servlet-7.0.1.v20091125.jar~~ -> **jetty-ee10-servlet-12.0.4.jar**
- ~~jetty-util-7.0.1.v20091125.jar~~ -> **jetty-util-12.0.4.jar**
- ~~jetty-webapp-7.0.1.v20091125.jar~~ -> j**etty-ee10-webapp-12.0.4.jar**
- **jetty-xml-12.0.4.jar**
- ~~jetty-continuation-7.0.1.v20091125.jar~~
- ~~JettyContinuationContainerAbstraction.java~~
- ~~JettyContinuationSleeper.java~~
## Spring 3.2.14 -> Spring 6.1.1
- ~~spring-aop-3.2.14.RELEASE.jar~~ -> **spring-aop-6.1.1.jar**
- ~~spring-beans-3.2.14.RELEASE.jar~~ -> **spring-beans-6.1.1.jar**
- ~~spring-context-3.2.14.RELEASE.jar~~ -> **spring-context-6.1.1.jar**
- ~~spring-core-3.2.14.RELEASE.jar~~ -> **spring-core-6.1.1.jar**
- ~~spring-mock.jar~~ -> **spring-test-6.1.1.jar**
- ~~spring-web-3.2.14.RELEASE.jar~~ -> **spring-web-6.1.1.jar**
- ~~spring-webmvc-3.2.14.RELEASE.jar~~ -> **spring-webmvc-6.1.1.jar**
## 其他依赖升级
- ~~commons-io-1.4.jar~~ -> **commons-io-2.15.1.jar**
## 删除模块
- ~~serverside/struts~~
- ~~serverside/webwork~~
## Api变动导致的代码修改
- org.directwebremoting.dwrp.CommonsFileUpload
- org.directwebremoting.spring.namespace.ConfigurationParser
- org.directwebremoting.jms.DwrConnection
- org.directwebremoting.jms.DwrConnectionFactory
- org.directwebremoting.jms.DwrMessage
- org.directwebremoting.jms.DwrMessageProducer
- org.directwebremoting.jms.DwrSession

# DWR - Direct Web Remoting

DWR is a Java library that enables Java on the server and JavaScript in a browser to interact and call each other as simply as possible.
DWR is Easy Ajax for Java.

Website http://directwebremoting.org \
Forum https://discourse.dojo.io/c/dwr-users

Old mailing lists Mailman archive:\
http://mail.dojotoolkit.org/mailman/listinfo/dwr-users \
http://mail.dojotoolkit.org/mailman/listinfo/dwr-dev

Old mailing lists Nabble archive:\
http://dwr.2114559.n2.nabble.com/DWR-Users-f2114559.html \
http://dwr.2114559.n2.nabble.com/DWR-Dev-f5409447.html

## Building DWR

DWR is primarily built using ant. The important ant targets are:
* clean: Removes target files and .DS_Store files left around by OSX
* jar: Create a new dwr.jar file
* war: Create a clean unpacked demo war file for tomcat/jetty/etc
* package: Build a set of output .zip files for distribution

The DWR build system can be adapted to create outputs containing different
modules (see Terminology) so it is simple to create a dwr.jar file that
contains exactly the modules that you need. See the 'modules' property and the
execution of the 'build' macrodef.

The ant outputs are stored in dwr/target/ant.

## Install to Maven

After using ant to package the project, run the command in the project root directory.
Or download the jar package in release and install it, but the path needs to be changed.

```maven
  mvn install:install-file "-Dfile=dist\lib\dwr-JakartaEE-4.0.0-SNAPSHOT.jar" "-Dsources=dist\lib\dwr-JakartaEE-4.0.0-SNAPSHOT-sources.jar" "-DgroupId=org.directwebremoting" "-DartifactId=dwr-JakartaEE" "-Dversion=4.0.0-SNAPSHOT" "-Dpackaging=jar"
```
After installation, you can use the dependency in the project.
```xml
<dependency>
  <groupId>org.directwebremoting</groupId>
  <artifactId>dwr-JakartaEE</artifactId>
  <version>4.0.0-SNAPSHOT</version>
</dependency>
```

## Terminology

Module: DWR is made up of a number of modules. The idea is that these modules
are as independent as possible. Spring integration is an example of a module.
The modules are grouped into four areas: core, protocol, serverside, ui
* core: The heart of DWR
  * api: All the important interfaces and classes for users and integrators
  * impl: Implementations of the interface points, of use most to DWR developers
  * legacy: For backwards compatibility
* protocol: A set of ways of talking to the network
* serverside: Integrations with various server-side technologies
* ui: Integrations with various user interface technologies

Each module may contain a number of source trees. One for test, one for demos,
one for the main sources and maybe one for generated code. The module may also
contain a web directory to be merged into an output war file.

## Licensing information

DWR is a subproject of Dojo which is a member of the JS Foundation.

� 2005 [JS Foundation](https://js.foundation/) under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) license.

DWR includes derivative works under other copyright notices and distributed according to the terms of their respective licenses,
please see [COPYRIGHT.txt](COPYRIGHT.txt) and [LICENSE.txt](LICENSE.txt) for details.
