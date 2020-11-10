#!/usr/bin/env sh

touch deployment/Dockerfile

echo "FROM ghcr.io/felixoi/sponge-docs-theme:latest as homepage" >> deployment/Dockerfile
i=0
for version in $VERSIONS; do
    echo "FROM ghcr.io/felixoi/spongedocs:$version as builder-$i" >> deployment/Dockerfile
    i=$(( i + 1 ))
done

echo "FROM nginx:1.19.4-alpine" >> deployment/Dockerfile
echo "COPY --from=homepage /usr/share/nginx/html /usr/share/nginx/html" >> deployment/Dockerfile

i=0
for version in $VERSIONS; do
    echo "COPY --from=builder-$i /usr/share/nginx/html/$version /usr/share/nginx/html/$version" >> deployment/Dockerfile
    i=$(( i + 1 ))
done
