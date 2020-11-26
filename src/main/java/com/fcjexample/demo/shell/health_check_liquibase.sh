#!/usr/bin/env bash

KUBECONFIG="$1"
NAMESPACE="$2"
SERVICE_NAME="$3"
TAG="$4"

MAX_RETRY_COUNT=60
SLEEP_INTERVAL=30
retry_count=0
keep_retry=true

if [[ 'dv-liquibase' != $SERVICE_NAME ]]
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

JOB_NAME=$(helm get manifest -n $NAMESPACE $SERVICE_NAME | awk '/kind: Job/,/spec/' | awk '{if($1 == "name:") {print $2}}')
echo -e "Job name: $JOB_NAME\n"

while [[ "$keep_retry" == true ]]
do
  health_job_count=0
  kubectl --kubeconfig $KUBECONFIG describe jobs.batch $JOB_NAME -n $NAMESPACE> job.yaml

  echo -e "Conducting health check for Job: $JOB_NAME\n"

  CURRENT_TAG=$(cat job.yaml | awk '/'$LABEL_NAME':/,/'$SERVICE_NAME'/' | grep Image: | awk '{print $2}' | awk '{ split($0, arr, ":"); print arr[2] }')
  echo -e "Current tag: $CURRENT_TAG\n"
  COMPLETION_NUM=$(cat job.yaml | grep Completions: | awk '{print $2}')

  if [[ "$CURRENT_TAG" != "$TAG" ]]
  then
    echo -e "Image tag does not match. Retry after $SLEEP_INTERVAL seconds...\n"
    retry_count=$((retry_count+1))
    sleep $SLEEP_INTERVAL
    keep_retry=true
  elif [[ "$COMPLETION_NUM" != "1" ]]
  then
    echo -e "Job is not completed. Retry after $SLEEP_INTERVAL seconds...\n"
    retry_count=$((retry_count+1))
    sleep $SLEEP_INTERVAL
    keep_retry=true
  else
    health_job_count=$((health_job_count+1))
  fi

  echo -e "retry count: $retry_count\n"
  if [[ "$health_job_count" == "1" ]] || [[ "$retry_count" == "$MAX_RETRY_COUNT" ]]
  then
    keep_retry=false
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
