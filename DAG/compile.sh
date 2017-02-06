#!/bin/bash
cd ~/Documents/korat
javac -cp korat.jar DAGtest/DAG.java
rm output.txt
for((i = 0; i < 6; i++))
do java -noverify -cp korat.jar:. korat.Korat --class DAGtest.DAG --args $i >> output.txt
done
