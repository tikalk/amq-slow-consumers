#!/bin/sh

# must execute this from the application's home folder, e.g home$>./bin/server.sh
server_home=$(cd `dirname $0`/..; pwd)
server_conf=${server_home}/conf

#export server_home


if [ -z "$1" ] ; then
	export server_home
	return
fi

# ------------------------------------------------------------------
# set the properties in $server_conf/$1.properties as system properties
# the sed command will replace the placeholder in the properties file ${server_home} with the value of
# server_home, and the property server.home is then injected to the jvm as -D property.
# the second sed command will remove comment lines starting with #.
# the last sed command removes empty lines.
# ------------------------------------------------------------------
JAVAOPTS=`cat ${server_conf}/$1.properties | sed s:'${server_home}':${server_home}:g | sed '/^\#/d' | sed '/^$/d'`
for i in ${JAVAOPTS}
do
    # if the file content is empty, then it will return the input string
    echo $i | grep '=' > /dev/null
    has_eq=$?
    echo $i | grep -Ee '^-XX:' > /dev/null
    has_xx=$?
    #ignore comment lines,they are removed
    #echo $i | grep -Ee '^#' > /dev/null
    #has_comment=$?
    # ignore the comment lines
    #if [ $has_comment -eq 0 ]; then
    if [ $has_eq -eq 0 ] && [ $has_xx -ne 0  ]; then
       #echo "adding system property -D$i"
       JAVA_OPTS="$JAVA_OPTS -D$i"
    else
       #echo "adding JVM argument $i"
       JAVA_OPTS="$JAVA_OPTS $i"
    fi
   #fi
done



# ------------------------------------------------------------------
# set the debug options
# ------------------------------------------------------------------
if [ -n "${XPR_DEBUG_PORT}" ]; then
    JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=${XPR_DEBUG_PORT},suspend=${SUSPEND_UNTIL_DEBUG_ATTACHE} "
fi


#JAVA=${JAVA_HOME}/bin/java
#todo: see how to find java home
JAVA="java"

#add the conf folder to the classpath
CLASSPATH=${server_conf}
for f in $server_home/lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done


echo Starting application with "$1" properties 
echo '----------------------------------------'
echo server_home=$server_home
echo '----------------------------------------'
echo JAVA_OPTS=$JAVA_OPTS
echo '----------------------------------------'
echo JAVA=$JAVA
echo '----------------------------------------'
echo Java CLASSPATH=$CLASSPATH
echo '----------------------------------------'
echo '----------------------------------------'
echo '----------------------------------------'


export JAVA_OPTS
export CLASSPATH


