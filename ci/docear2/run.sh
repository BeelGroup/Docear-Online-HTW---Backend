#this file will be copied to docear2

echo "killing docear";
killall Xvfb 2> /dev/null || true; #kills docear, error messages will be discarded
echo "removing previous installation"
rm -r --force docear-* && \
tar xzf docear_linux.tar.gz && \
cd docear-* && \
rm -f nohup.out && \
echo "starting docear" && \
nohup xvfb-run --auto-servernum -s "-screen 6 1280x1024x24" bash docear.sh &