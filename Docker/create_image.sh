#!/bin/bash

path_project_angular=$(dirname $(pwd))/Angular
path_project=$(dirname $(pwd))/TrackOrJargh
path_jar=$path_project/target

#Create Angular
docker run -it --rm --name trackorjargh-angular -v "$path_project_angular":/otp/trackorjargh -w /otp/trackorjargh teracy/angular-cli ng build --base-href /new/

#Move angular files to TrackOrJargh
rm $path_project/src/main/resources/static/new/*
cp $path_project_angular/dist/* $path_project/src/main/resources/static/new

#Create jar TrackOrjargh
docker run -it --rm --name trackorjargh -v "$path_project":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -DskipTests

#Move jar to actual directory
mv $path_jar/TrackOrJargh-0.0.1-SNAPSHOT.jar .

#Create image 
docker build -t oscarsotosanchez/trackorjargh-spring .
