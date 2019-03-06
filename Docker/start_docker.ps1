If(Test-Path .\TrackOrJargh* -PathType Leaf){
    rm TrackOrJargh-0.0.1-SNAPSHOT.jar
}
docker-compose down
.\create_image.ps1
docker-compose up