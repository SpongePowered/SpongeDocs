#------- Build

FROM crowdin/cli:latest as builder

ARG GITHUB_USER
ARG GITHUB_TOKEN
ARG GITHUB_REPO
ARG GITHUB_VERSION
ARG CROWDIN_TOKEN
ARG CROWDIN_PROJECT_ID
ARG CROWDIN_BASE_URL
ARG VERSION

RUN apk add --no-cache python3 bash zip

ENV VIRTUAL_ENV=/opt/venv
RUN python3 -m venv $VIRTUAL_ENV
ENV PATH="$VIRTUAL_ENV/bin:$PATH"

WORKDIR /app

COPY requirements.txt /app/requirements.txt

RUN pip install -U -r requirements.txt

COPY . /app

RUN sphinx-build -W -d build/doctrees source build/html
RUN sphinx-build -W -q -b gettext source build/locale

RUN cd /app && crowdin download -b ${VERSION}

RUN sphinx-intl build > /dev/null

RUN export VERSIONS=`list-versions` \
      && translations=`list-translations ${VERSION}` \
      && for version in $VERSIONS ; do export LOCALES_$(echo $version | tr '.' '_')="en $(list-translations $version)" ; done \
      && export LOCALES="en $translations" \
      && build-language en \
      && echo "$translations" | xargs -n1 -P4 build-language

#------- Copy to nginx

FROM nginx:1.25.4-alpine

COPY --from=builder /app/dist /usr/share/nginx/html
