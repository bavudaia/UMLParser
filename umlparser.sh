#!/bin/bash
java -jar parser.jar $1
python ./yuml-master/yuml -s plain -i ./yUMLOutput.yuml -o $2
