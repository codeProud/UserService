#!/usr/bin/env bash
# Run java application

java \
 -Xmx${XMX} -XX:+IdleTuningGcOnIdle -Xtune:virtualized -Xscmx128m -Xscmaxaot100m -Xshareclasses:cacheDir=/opt/shareclasses \
  -jar user-service.jar \
  --spring.data.cassandra.keyspace-name=${CASSANDRA_KEYSPACE} \
  --spring.data.cassandra.contact-points=${CASSANDRA_HOST} \
  --spring.data.cassandra.port=${CASSANDRA_PORT} \
  --spring.redis.host=${REDIS_HOST} \
  --spring.redis.port=${REDIS_PORT} \
  --server.port=${SERVER_PORT} \
  --server.proxy.prefix=${USER_SERVICE_PREFIX} \
  --logging.level.root=${LOGGING_LEVEL} \
  --eureka.client.serviceUrl.defaultZone=http://${EUREKA_URL}/eureka/ \
  --facebook.client.clientId=${FACEBOOK_CLIENT_ID} \
  --facebook.client.clientSecret=${FACEBOOK_CLIENT_SECRET} \
  --google.client.clientId=${GOOGLE_CLIENT_ID} \
  --google.client.clientSecret=${GOOGLE_CLIENT_SECRET} \
  --spring.mail.username=${SRING_MAIL_USERNAME} \
  --spring.mail.password=${SPRING_MAIL_PASSWORD} \
  --frontend.url.parent=${FRONTEND_URL} \
  --frontend.url.main=/${FRONTEND_MAIN} \
  --frontend.url.login=/${FRONTEND_LOGIN}
  /