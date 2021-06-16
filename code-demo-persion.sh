#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
export BUILD_ID=dontKillMe

#指定最后编译好的jar存放的位置
NEW_PATH=/out

#Jenkins中编译好的jar位置
OLD_PATH=/var/lib/jenkins/workspace/code-demo-persion/target

#Jenkins中编译好的jar名称
JAR_NAME=code-demo-persion-0.0.1-SNAPSHOT.jar

#获取运行编译好的进程ID，便于我们在重新部署项目的时候先杀掉以前的进程
PID=$(cat /out/code-demo-persion.pid)

#进入指定的编译好的jar的位置
cd  ${OLD_PATH}

#将编译好的jar复制到最后指定的位置
echo --------------------------------------------------------------
rm -rf ${NEW_PATH}/${JAR_NAME}
cp  ${OLD_PATH}/${JAR_NAME} ${NEW_PATH}

#进入最后指定存放jar的位置
cd  ${NEW_PATH}

#杀掉以前可能启动的项目进程
kill -9 ${PID}

#启动jar，指定SpringBoot后台启动
nohup java -jar  ${JAR_NAME} &

#将进程ID存入到shaw-web.pid文件中
echo $! > /out/code-demo-persion.pid
