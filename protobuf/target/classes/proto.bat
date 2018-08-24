E:
cd E:\face2face\protobuf\src\main\resources

set OUT=../java
set def_cli_java=(login chat)
set def_internal_java=(protobuf.internal)


for %%A in %def_cli_java% do (
    echo generate cli protocol java code: %%A.proto
    protoc.exe --java_out=%OUT% ./cli_def/%%A.proto
)

for %%A in %def_internal_java% do (
    echo generate protobuf.internal java code: %%A.proto
    protoc.exe --java_out=%OUT% ./internal_def/%%A.proto
)

pause