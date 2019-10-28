cd /Users/innerpeacer/Workspaces/MyEclipse-WTMap/MapServiceMaven/Pbf/proto/mapdata;
protoc --java_out=../../src/main/java/ t_y_geometry_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_map_data_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_property_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_symbol_pbf.proto;


cd /Users/innerpeacer/Workspaces/MyEclipse-WTMap/MapServiceMaven/Pbf/proto/poi;
protoc --java_out=../../src/main/java/ t_y_poi_pbf.proto;


cd /Users/innerpeacer/Workspaces/MyEclipse-WTMap/MapServiceMaven/Pbf/proto/beacon;
protoc --java_out=../../src/main/java/ t_y_beacon_pbf.proto;

cd /Users/innerpeacer/Workspaces/MyEclipse-WTMap/MapServiceMaven/Pbf/proto/cbm
protoc --java_out=../../src/main/java/ t_y_symbol_pbf.proto;protoc --java_out=../../src/main/java/ t_y_cbm_pbf.proto;

