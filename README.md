<!--
  ~ Licensed to the Apache Software Foundation (ASF) under one or more
  ~ contributor license agreements.  See the NOTICE file distributed with
  ~ this work for additional information regarding copyright ownership.
  ~ The ASF licenses this file to You under the Apache License, Version 2.0
  ~ (the "License"); you may not use this file except in compliance with
  ~ the License.  You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->

[![Build Status](https://builds.apache.org/job/unomi-master/badge/icon)](https://builds.apache.org/job/unomi-master/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.unomi/unomi-root/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.apache.unomi/unomi-root)

⚠️  Note
============ 
> Base branch for unomi changes is `rfk-unomi-v2.1`.  
> Please merge all the changes to `rfk-unomi-v2.1` branch only 

Deployment Steps
============
- Create a feature branch. Make changes raise PR.
- Merge PR with `rfk-unomi-v2.1` branch.
- Switch to `rfk-unomi-v2.1` branch and run `mvn package` or `mvn package -DskipTests`
- This build the binary under `package/target` directory
- Take backup of the binary present in s3 path `s3://rfk-repo/packages/rfk-data-unomi/` to `s3://rfk-repo/packages/rfk-data-unomi/unomi-2.0.0_bkp`
- Copy the binary `unomi-2.0.0.tar.gz` to s3 path `s3://rfk-repo/packages/rfk-data-unomi/`.

Note: This binary will be pulled by `rfk-data-unomi` repo [here](https://github.com/Reflektion/rfk-data-unomi/blob/main/Dockerfile#L36) while creating unomi image.

Apache Unomi
============
https://unomi.apache.org

Apache Unomi stores user profile information and is mostly used to provide a backend server for A/B testing and 
personalization. To do so it implements the currently under development OASIS Context Server specification. 

License
-------
The source code is available under the Apache License V2

Branches
--------

- main (master) branch: is the current development branch and should be considered unstable. At this it might even not 
  compile. It is only recommended for Unomi developers or people looking to contribute on the project.
- unomi-X.X.X branches : these are the maintenance branches of the stable releases of Apache Unomi and should always
compile and be stable. These are recommended for users that prefer to work from the source code. Otherwise you can find 
packaged binaries on the [Apache Unomi website](https://unomi.apache.org). 

Documentation
-------------
You can find all the updated documentation, including building and deployment instructions, on the [Apache Unomi 
web site](https://unomi.apache.org).
