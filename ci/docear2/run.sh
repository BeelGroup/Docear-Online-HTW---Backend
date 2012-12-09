killall Xvfb; #kills docear
rm -r --force docear-* && \
tar xzf docear_linux.tar.gz && \
cd docear-* && \
nohup xvfb-run --auto-servernum -s "-screen 6 1280x1024x24" bash docear.sh &