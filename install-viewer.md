#Howto install Iceweasel in Kiosk Mode on Raspbian Jessie:

##1. Install relevant Packages

```shell
&> sudo apt-get update && sudo apt-get upgrade -y
&> sudo apt-get install iceweasel unclutter
```

Install R-Kiosk Addon for Iceweasel/Firefox:
https://addons.mozilla.org/de/firefox/addon/r-kiosk/

##2. Configure autostart with iceweasel kiosk mode:

&> vi /etc/xdg/lxsession/LXDE-pi/autostart
AND
&> vi ~/.config/lxsession/LXDE-pi/autostart
```shell
# Disable xscreensaver by commenting following line out
#@xscreensaver -no-splash

# Disable Powermanagement
@xset s off
@xset -dpms
@xset s noblank

# start iceweasel in kiosk mode
@iceweasel http://<openflorianserver>/openflorian/
@unclutter -grab -visible
```

##4. Install CEC Library for Raspbian

Source: <http://constey.de/2014/10/fernseher-ueber-hdmi-per-raspberry-pi-steuern-cec/>

###Installation of Build Essentials:
```shell
&> apt-get install cmake liblockdev1-dev libudev-dev libxrandr-dev python-dev swig
```

###libCEC download and compile:
```shell
&> cd ~/; git clone https://github.com/Pulse-Eight/platform.git
&> cd platform
&> cmake .
&> make
&> sudo make install
&> cd ~/; git clone git://github.com/Pulse-Eight/libcec.git
&> cd libcec/
&> cmake -DRPI_INCLUDE_DIR=/opt/vc/include -DRPI_LIB_DIR=/opt/vc/lib .
&> make -j4
&> sudo make install
&> sudo ldconfig
```

###Testing the client:
```shell
$> cec-client -l
  Found devices: 1

  device: 1
  com port: RPI
  vendor id: 2708
  product id: 1001
  firmware version: 1
  type: Raspberry Pi
```

### power on TV
```shell
&> echo "on 0" | sudo cec-client -s -d 1
```

### power off TV (not working on every TV)
```shell
&> echo "standby 0" | sudo cec-client -s -d 1
```

### set active source
```shell
&> echo "as" | cec-client -s
```

### Create Cron-Job for automatic TV poweron and setting active source

```shell
&> sudo crontab -e

*/3 * * * * echo "as" | /usr/local/bin/cec-client -s
*/5 * * * * echo "on 0" | sudo /usr/local/bin/cec-client -s -d 1
```