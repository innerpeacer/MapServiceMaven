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

cd /Users/innerpeacer/IdeaProjects/MapServiceMaven/Pbf/proto/route
protoc --java_out=../../src/main/java/ t_y_route_network_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_route_geometry_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_route_property_pbf.proto;

cd /Users/innerpeacer/IdeaProjects/MapServiceMaven/Pbf/proto/threemap;
protoc --java_out=../../src/main/java/ t_y_three_geometry_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_three_data_pbf.proto;
protoc --java_out=../../src/main/java/ t_y_three_property_pbf.proto;
