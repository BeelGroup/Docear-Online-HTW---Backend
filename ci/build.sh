#builds Docear with webservice module

set -x

ant_file_name=build_docear.xml
ant_file_src="docear_plugin_webservice/ant/${ant_file_name}"
ant_file_target_folder=docear_framework/ant

cp ${ant_file_src} ${ant_file_target_folder}

cd ${ant_file_target_folder}

ant -buildfile ${ant_file_name}