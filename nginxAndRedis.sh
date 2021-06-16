#设置启动后不杀死
export BUILD_ID=dontKillMe

#nginx启动目录
NGINX_PATH=/usr/local/nginx/sbin

#redis启动目录
REDIS_PATH=/opt/redis/redis-5.0.3/bin

#获取redis的pid
#REDIS_PID=$(cat /var/run/redis_6379.pid)||true
#kill -9 ${REDIS_PID}||true

#NGINX_PID=$(cat /usr/local/nginx/logs/nginx.pid)||true
#kill -9 ${NGINX_PID}||true

#||true指的是jenkins忽略此句的错误信息继续执行
#=========================nginx===============================
echo now run the nginx...
cd ${NGINX_PATH}
./nginx -s stop||true
./nginx||true

#=========================redis===============================
echo now run the redis...
cd ${REDIS_PATH}
./redis-server redis.conf||true
