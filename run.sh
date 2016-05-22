#!/bin/bash


mvn clean package failsafe:integration-test -Dcatalina.base="D:\Program Files\springsource\vfabric-tc-server-developer-2.8.2.RELEASE\base-instance" -Dcatalina.home="D:\Program Files\springsource\vfabric-tc-server-developer-2.8.2.RELEASE\tomcat-7.0.35.B.RELEASE" 
