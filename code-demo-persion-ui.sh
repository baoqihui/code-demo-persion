************************************************************合并********************************************************
#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
export BUILD_ID=dontKillMe

#Jenkins中编译好的dist位置
OLD_PATH=/var/lib/jenkins/workspace/code-demo-persion-ui/wms

#设置新的存放路径
NEW_PATH=/out/dist

cd ${OLD_PATH}
npm install --unsafe-perm

#删除原dist
rm -rf ${OLD_PATH}/dist

#编译
cd ${OLD_PATH}
npm run build

#删除新目录的dist
rm -rf ${NEW_PATH}

#源文件copy
cp -a ${OLD_PATH}/dist ${NEW_PATH}


************************************************************分开********************************************************
#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
export BUILD_ID=dontKillMe

#Jenkins中编译好的dist位置
OLD_PATH=/var/lib/jenkins/workspace/code-demo-persion-ui/wms

cd ${OLD_PATH}
npm install --unsafe-perm

************************************************************2********************************************************
#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
export BUILD_ID=dontKillMe

#Jenkins中编译好的dist位置
OLD_PATH=/var/lib/jenkins/workspace/code-demo-persion-ui/wms

#删除原dist
rm -rf ${OLD_PATH}/dist

#编译
cd ${OLD_PATH}
npm run build

************************************************************3********************************************************
#设置新的存放路径
NEW_PATH=/out/dist

#设置生成路径
OLD_PATH=/var/lib/jenkins/workspace/code-demo-persion-ui/wms

#删除新目录的dist
rm -rf ${NEW_PATH}

#源文件copy
cp -a ${OLD_PATH}/dist ${NEW_PATH} 
