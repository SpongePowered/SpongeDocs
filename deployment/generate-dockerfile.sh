#!/usr/bin/env sh

REGISTRY=ghcr.io
IMAGE_NAME=spongepowered/spongedocs
ACTIVE_VERSION="${ACTIVE_VERSION:-}"
TAG_PREFIX="${TAG_PREFIX:-}"
touch deployment/Dockerfile

echo "FROM $REGISTRY/spongepowered/sponge-docs-theme:latest as homepage" >> deployment/Dockerfile
i=0
for version in $VERSIONS; do
    if [ -n "$ACTIVE_VERSION" ] && [ "$version" = "$ACTIVE_VERSION" ]; then
        tag="${TAG_PREFIX}${version}"
    else
        tag="$version"
    fi
    echo "FROM $REGISTRY/$IMAGE_NAME:$tag as builder-$i" >> deployment/Dockerfile
    i=$(( i + 1 ))
done

echo "FROM nginx:1.29.0-alpine" >> deployment/Dockerfile
echo "COPY --from=homepage /usr/share/nginx/html /usr/share/nginx/html" >> deployment/Dockerfile

i=0
for version in $VERSIONS; do
    echo "COPY --from=builder-$i /usr/share/nginx/html/$version /usr/share/nginx/html/$version" >> deployment/Dockerfile
    i=$(( i + 1 ))
done
