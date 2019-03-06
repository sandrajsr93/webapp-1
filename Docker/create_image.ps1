$var = $(get-item ${PWD}).parent.FullName
$pathProjectAngular = $var + "\Angular"
$pathProject = $var + "\TrackOrJargh"
$pathJar = $pathProject + "\target"

#Create Angular
docker run -it --rm --name trackorjargh-angular -v ${pathProjectAngular}:/otp/trackorjargh -w /otp/trackorjargh teracy/angular-cli ng build --base-href /new/

#Move angular files to TrackOrJargh
rm ${pathProject}\src\main\resources\static\new\*
cp ${pathProjectAngular}\dist\* ${pathProject}\src\main\resources\static\new

#Create jar TrackOrjargh
docker run -it --rm --name trackorjargh -v ${pathProject}:/usr/src/mymaven -w /usr/src/mymaven maven mvn package -DskipTests

#Move jar to actual directory
mv ${pathJar}/TrackOrJargh-0.0.1-SNAPSHOT.jar .

#Create image
docker build -t oscarsotosanchez/trackorjargh-spring .
