FROM jboss/wildfly

ENV JBOSS_HOME /opt/jboss/wildfly

COPY ./jboss-cli/configure-postgresql.cli $JBOSS_HOME/standalone/tmp/.
COPY ./jboss-cli/configure-security.cli $JBOSS_HOME/standalone/tmp/.
COPY ./jboss-cli/configure-mail.cli $JBOSS_HOME/standalone/tmp/.
COPY ./jboss-cli/reeler.properties /opt/jboss/wildfly/standalone/configuration/reeler.properties


ADD https://jdbc.postgresql.org/download/postgresql-42.1.4.jar $JBOSS_HOME/standalone/tmp/postgresql-42.1.4.jar
#ADD always sets the file owner as root, so change to jboss is necessary
USER root
RUN chown -R jboss $JBOSS_HOME/standalone/tmp/postgresql-42.1.4.jar

USER jboss

RUN $JBOSS_HOME/bin/add-user.sh admin adminadmin1 --silent

# Install postgres drivers and datasource
RUN /bin/sh -c '$JBOSS_HOME/bin/standalone.sh &' && \
  sleep 10 && \
  cd $JBOSS_HOME/standalone/tmp/ && \  
  $JBOSS_HOME/bin/jboss-cli.sh --user=admin --password=adminadmin1  --connect --file=configure-postgresql.cli && \
  $JBOSS_HOME/bin/jboss-cli.sh --user=admin --password=adminadmin1  --connect --file=configure-security.cli && \
  $JBOSS_HOME/bin/jboss-cli.sh --user=admin --password=adminadmin1  --connect --file=configure-mail.cli && \
  $JBOSS_HOME/bin/jboss-cli.sh --connect --command=:shutdown && \
  rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
  rm configure*.* &&\
  rm postgres*.*


#ADD ./wildfly/reeler.war /opt/jboss/wildfly/standalone/deployments
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

