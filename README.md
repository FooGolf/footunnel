# FooGolf tunnel

## Purpose

This program supports golfers who:
- are playing a golf simulator such as GSPro on a Windows gaming PC in the cloud, e.g. a cloud PC at FooGolf.com
- want to connect their launch monitor to their local computer via USB cable or bluetooth

## How it works

````   
   ╔══════════════════════════╗               ┌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┐
   ║ Streaming computer       ║ usb/bluetooth ╎ Golf launch  ╎
   ║ (e.g Raspberry pi   ┌┄┄┄┄╫┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┤ monitor      ╎
   ╚═════════════════════╪════╝               └╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘
                         ┆                          
             ssh tunnel  ┆            Player        
╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╴    
   ╔═════════════════════╪════╗       Cloud         
   ║  Cloud PC           ┆    ║                     
   ║  ┌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┼╌┐  ║                     
   ║  ╎ Golf simulator   ╵ ╎  ║                     
   ║  ╎ software (GSPro)   ╎  ║                     
   ║  └╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘  ║                     
   ╚══════════════════════════╝
````

This program acts as a permanently running local API server. When its `/doSSH` API is called (typically via Javascript in a foogolf.com web page), it sets up an SSH tunnel in to the gaming PC running in the cloud.

This allows the virtualhere software running on the cloud PC to reach back and use USB devices attached to the user's device.

The user can then play golf on the cloud PC, hitting shots on their locally connected launch monitor.

## Usage

Download the jar file from releases, or if you prefer to compile from source:

`mvn clean package`

Run the program:

`java -jar ./fooboxtunnel-1.0.jar`

Call API to set up tunnel (typically this would instead happen via Javascript on a foogolf.com web page):

`curl -X POST "http://127.0.0.1:8087/doSSH" -H "Content-Type: application/json" -d '{"cloudPC": "76ecbd14-7f41-41c7-ab1e-4694b31dee69.gsprosims.foogolf.com", "password": "WNs23!"}' -i`

## Virtualhere software

The virtualhere software allows for one free USB/bluetooth device on a linux machine.

If you want to add additional devices (e.g. USB-connected streamdeck as a GSPro control box) then you will need a full virtualhere licence.
 
 ## Disclaimer
 
 This repository is provided "as is" without any warranties - express or implied - and use is at your own risk.
  

