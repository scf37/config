config
======

Configuration management library for Java suited for enterprise use.

Features
========

* Allows getting configuration from multiple sources: classpath, local path, http
* Support java properties merging.
* Supports typical enterprise use-cases: common configuration, 
    configuration for specific environment, sensitive production configuration.
* Has support for Spring, Log4j/Log4j2, web containers.

Typical usecases
================
0. Just make it work
   ```java
    //look in classpath root
    ConfigReader<Properties> reader = ConfigFactory.readPropertiesFrom("classpath:")
        .build();
    //look for classpath:my.properties
    //if classpath:{hostname}/my.properties exists, load it as well, overriding keys
    //in previous file
    Properties p = reader.read("my.properties");
  ```
  
1. Read java properties for this environment, add some default properties to them
    and allow to override loaded properties by system properties.

  ```java  
    ConfigReader<Properties> reader = ConfigFactory.readPropertiesFrom("classpath:config")
        .overrideWithSystemProperties().build();
    Properties p = reader.read(null, "myenv", "my.properties");
  ```
  File structure for this example:
  ```
  classpath:my.properties                       <-- default (common for all envs) properties
  classpath:myenv/my.properties                 <-- env-specific properties   
  ```

2. Use host name as environment name(useful for developers to have their private configuration).  
    Allow to set environment name on startup via system properties.  
    Production support wants to keep passwords separated from the app in home directory.  

  ```java  
  EnvironmentNameResolver resolver = new EnvironmentNameResolver();
    ConfigReader<Properties> reader = ConfigFactory.readPropertiesFrom("classpath:config")
        .overrideWith("file:~/config/").build();
    Properties p = reader.read("myapp", "v1", resolver.getEnvironmentName(), "my.properties");
  ```
  Typical file structure for this example:
  ```  
  classpath:config/myapp/myhost/my.properties   <-- env-specific properties
  classpath:config/myapp/prod/my.properties     <-- prod-specific properties
  classpath:config/myapp/my.properties          <-- common properties
  ~/config/myapp/my.properties                  <-- production passwords
  ```
3. And more!

Documentation
=============

TBD. See javadoc.

Installing
==========

git clone https://github.com/scf37/config.git  
cd config  
gradle install  

No public repo yet

License
=======

   Copyright 2013 Scf37

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
