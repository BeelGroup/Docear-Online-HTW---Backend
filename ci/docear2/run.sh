#this file will be copied to docear2

workspace_folder=~/docear-ci-workspace
docear_config_folder=~/.docear

echo "killing docear";
killall Xvfb 2> /dev/null || true; #kills docear, error messages will be discarded
echo "removing previous installation"
rm -r --force docear-*-build* && \
rm -r --force ${workspace_folder} && \
mkdir -p ${workspace_folder} && \
cp ~/docear/docear-settings-folder/auto.mmfilter ${docear_config_folder} && \
cp ~/docear/docear-settings-folder/auto.properties ${docear_config_folder} && \
tar xzf docear_linux.tar.gz && \
cd docear-* && \
rm -f nohup.out && \
echo "starting docear" && \
nohup xvfb-run --auto-servernum -s "-screen 6 1280x1024x24" bash docear.sh &