#!/usr/bin/env bash

set -e

exec java -Djava.security.egd=file:/dev/./urandom \
          -Duser.Timezone=America/Sao_Paulo \
          -jar /app/risk-insurance-*.jar

esac