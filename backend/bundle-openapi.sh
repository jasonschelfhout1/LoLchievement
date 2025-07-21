#!/bin/bash
swagger-cli bundle src/main/resources/static/openapi/openapi.yaml -o src/main/resources/static/openapi-bundled.yaml --type yaml
