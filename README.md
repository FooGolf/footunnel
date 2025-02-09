# FooGolf tunnel

## Purpose

This program supports golfers who:
- are playing a golf simulator such as GSPro on a cloud PC e.g. at FooGolf.com
- have their launch monitor attached to their local device via USB or bluetooth

## How it works

This program acts as a permanently running local web server. When its `/doSSH` API is called (typically via Javascript in a foogolf.com web page), it sets up an SSH tunnel in to the gaming PC running in the cloud.

This allows the virtualhere software running on the cloud PC to reach back and use USB devices attached to the user's device.

The user can then play golf on the cloud PC, hitting shots on their locally connected launch monitor.

````   
   ╔══════════════════════════╗     ┌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┐
   ║  User device             ║ usb ╎ Golf launch  ╎
   ║ (e.g raspberry pi   ┌┄┄┄┄╫┄┄┄┄┄┤ monitor      ╎
   ╚═════════════════════╪════╝     └╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘
                         ┆                          
             ssh tunnel  ┆            Player        
╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╴    
   ╔═════════════════════╪════╗       Cloud         
   ║  Cloud PC           ┆    ║                     
   ║  ┌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┼╌┐  ║                     
   ║  ╎ Golf simulator   ╵ ╎  ║                     
   ║  ╎  software          ╎  ║                     
   ║  └╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┘  ║                     
   ╚══════════════════════════╝
````

