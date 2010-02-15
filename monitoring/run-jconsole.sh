#!/bin/bash

exec /usr/local/jdk/jdk/bin/jconsole \
            -J-Djava.util.logging.config.file=jconsole-logging.properties \
            -J-Djavax.net.debug=all \
            -J-Djavax.net.ssl.trustStore=/devel/tikal-fuse/amq-slow-consumers/monitoring/jconsole.ketstore \
            -J-Djavax.net.ssl.trustStorePassword=changeit \
            -J-Djavax.net.ssl.keyStore=/devel/tikal-fuse/amq-slow-consumers/monitoring/jconsole.ketstore  \
            -J-Djavax.net.ssl.keyStorePassword=changeit
#            shalom-laptop:1097


