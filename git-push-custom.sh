#!/usr/bin/env bash
cd "$HOME/DAM" || exit 1
git add .
read -r -p "Introduce el mensaje del commit: " msg
git commit -m "$msg"
git push
read -r -p "Pulsa Enter para salir..."
