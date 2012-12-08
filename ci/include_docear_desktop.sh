docear_desktop_path=~/docear-desktop-copy-template

java -jar ci/docear-desktop-checkout_*.jar ${docear_desktop_path} https://github.com/Docear/Desktop.git && \
cp -r ${docear_desktop_path}/{d*,f*,J*} .