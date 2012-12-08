docear_desktop_path=/tmp/docear-desktop

java -jar ci/docear-desktop-checkout_2.9.2-0.1-SNAPSHOT-one-jar.jar ${docear_desktop_path} https://github.com/Docear/Desktop.git && \
cp -r ${docear_desktop_path}/{d*,f*,J*} .