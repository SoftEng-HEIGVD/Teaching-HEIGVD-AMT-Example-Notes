FROM jboss/wildfly:15.0.0.Final

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

ADD conf/standalone-amt.xml /opt/jboss/wildfly/standalone/configuration/standalone-amt.xml
ADD conf/modules/ /opt/jboss/wildfly/modules/system/layers/base/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--server-config", "standalone-amt.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug", "*:8787"]
