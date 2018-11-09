cd /Users/innerpeacer/Workspaces/MyEclipse-WTMap/MapServiceMaven/MapService/pobuf/proto
protoc --java_out=../../src/main/java/ t_y_geometry_pbf.proto
protoc --java_out=../../src/main/java/ t_y_map_data_pbf.proto
protoc --java_out=../../src/main/java/ t_y_property_pbf.proto
protoc --java_out=../../src/main/java/ t_y_symbol_pbf.proto
protoc --java_out=../../src/main/java/ t_y_poi_pbf.proto

protoc --java_out=../../src/main/java/ t_y_beacon_pbf.proto

