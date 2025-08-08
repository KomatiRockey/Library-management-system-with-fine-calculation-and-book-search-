#!/bin/sh
set -e
MVN_VERSION=3.9.6
MVN_DIR=".mvn/wrapper"
PROPERTIES_FILE="$MVN_DIR/maven-wrapper.properties"
JAR_FILE="$MVN_DIR/maven-wrapper.jar"

# Download the wrapper jar if not exists
if [ ! -f "$JAR_FILE" ]; then
  echo "Downloading Maven Wrapper..."
  mkdir -p "$MVN_DIR"
  curl -fsSL $(grep wrapperUrl $PROPERTIES_FILE | cut -d'=' -f2) -o "$JAR_FILE"
fi

JAVA_CMD="java"
exec "$JAVA_CMD" -jar "$JAR_FILE" "$@"
