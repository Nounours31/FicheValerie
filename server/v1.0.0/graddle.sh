#/bin/bash

echo "To install: gradle installDist --warning-mode all --info"
echo "Use: --warning-mode all --info"

Server=$(hostname)
echo $Server

set JAVA_HOME=
set GRADDLE_HOME=

	export JAVA_HOME=/C/java/jdk1.8.0_241
	export GRADDLE_HOME=/E/ProgramFiles/gradle-7.1.1

echo JAVA_HOME: $JAVA_HOME

export PATH=$JAVA_HOME:$JAVA_HOME/bin:$GRADDLE_HOME:$GRADDLE_HOME/bin:$PATH
