# 11:46
[![GitHub license](https://img.shields.io/github/license/MrAnima/Camerash.svg)](https://github.com/MrAnima/Camerash/blob/master/LICENSE)
> A simple app that creates randomness from camera frames

[![Screenshot](https://github.com/MrAnima/Camerash/blob/master/screenshot.jpg)]

## How does it work ?
The camera of the phone captures a video. For each frame:
*   The raw image is acquired as a byte array
*   This byte array is hashed using `SHA1`
*   We take the first byte of the digest and show it in a `TextView`
*   We add all the other bytes to a graph that show the good distribution of numbers

## Release History
*   Be patient. I'm lazy.
