# Overlay-Wrapper
Wrapper Apk for pubishing overlays as embedded zipfile

The zipfile sould contain the categories folder structure as follows:
folders are to be included as needed)

i.e.

/SykoPath-Overlays
                  |_/3rd Party Apps Teaking Overlays
                  |_/Color Changing Overlays
                  |_/Other Overlays
                  |_/UI Tweaking Overlays                  



This wrapper template app can be modified via strings.xml, colors.xml, styles.xml, layout.xml...


This wrapper template app copies the bitSyko-overlays.zip from /assests to /sdcard at 1st.
Then the zipfile will be extracted to the well known /sdcard/SykoPath-Overlays - folder 
(all overlay.apk(s) into their right place/categories)...
