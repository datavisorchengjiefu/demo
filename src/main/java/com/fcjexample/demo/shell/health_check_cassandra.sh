#!/usr/bin/env bash

KUBECONFIG="$1"
NAMESPACE="$2"
SERVICE_NAME="$3"
TAG="$4"

MAX_RETRY_COUNT=30
SLEEP_INTERVAL=20
retry_count=0
keep_retry=true

if [[ 'dv-cassandra' != $SERVICE_NAME ]]
then
  echo "Service name [$SERVICE_NAME] is not supported. Terminate the process."
  exit 256
fi

if [[ -z $TAG ]]
then
  echo "No tag found. Terminate the process."
  exit 256
fi
echo -e "Input tag: $TAG\n"

LABEL_NAME=$(helm get manifest -n $NAMESPACE $SERVICE_NAME | awk '/metadata:/,/spec/' | awk '{if($1 == "app:") {print $0}}' | awk 'NR == 1 {print $2}')

while [[ "$keep_retry" == true ]]
do
  PODS=($(kubectl --kubeconfig $KUBECONFIG get po -l app=$LABEL_NAME -n $NAMESPACE | awk 'NR >= 2 { print $1 }'))
  echo -e "PODS: "${PODS[@]}"\n"

  if [[ -z "${PODS[@]}" ]]
  then
    echo "No pods found. Terminate the process."
    exit 256
  else
    echo -e "# of PODS: "${#PODS[@]}"\n"
    health_pod_count=0
    for POD in "${PODS[@]}"
    do
      echo -e "Conducting health check for POD: $POD\n"
      kubectl --kubeconfig $KUBECONFIG describe po $POD -n $NAMESPACE > pod_description.yaml

      CURRENT_TAG=$(cat pod_description.yaml | awk '/'$LABEL_NAME':/,/'$SERVICE_NAME'/' | grep Image: | awk '{print $2}' | awk '{ split($0, arr, ":"); print arr[2] }')
      echo -e "Current tag: $CURRENT_TAG\n"

      STATUS_READY=$(cat pod_description.yaml | awk '/Conditions/,/Volumes/' | awk '$1 == "Ready" { print $2 }')
      echo -e "Status ready: $STATUS_READY\n"

      if [[ "$CURRENT_TAG" != "$TAG" ]]
      then
        echo -e "Image tag does not match. Retry after $SLEEP_INTERVAL seconds...\n"
        retry_count=$((retry_count+1))
        sleep $SLEEP_INTERVAL
        keep_retry=true
        break
      elif [[ "$STATUS_READY" != 'True' ]]
      then
        echo -e "Pod is not ready. Retry after $SLEEP_INTERVAL seconds...\n"
        retry_count=$((retry_count+1))
        sleep $SLEEP_INTERVAL
        keep_retry=true
        break
      else
        health_pod_count=$((health_pod_count+1))
      fi
    done

    echo -e "retry count: $retry_count\n"
    if [[ "$health_pod_count" == "${#PODS[@]}" ]] || [[ "$retry_count" == "$MAX_RETRY_COUNT" ]]
    then
      keep_retry=false
    fi
  fi
done

if [[ "$retry_count" == "$MAX_RETRY_COUNT" ]]
then
  echo "Health check timeout."
  exit 256
else
  echo "Health Check Done."
  exit 0
fi
