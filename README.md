# Camerash
[![GitHub license](https://img.shields.io/github/license/MrAnima/Camerash.svg)](https://github.com/MrAnima/Camerash/blob/master/LICENSE)
> A simple app that creates randomness from camera frames

![Screenshot](https://github.com/MrAnima/Camerash/blob/master/screenshot.jpg)

## Motivations & Background
In France , for our equivalent to _A Levels_ or _High-School Degree_, one of the exam (TPE - "Tutored Personal Work"), consists of a group work on a specific subject, leading to an oral presentation. Me and my group decided to work on randomness, and among other experiments, like a [double pendulum](https://en.wikipedia.org/wiki/Double_pendulum), I made this app, to show how entropy could be created from a video feed. Given that videos are often influenced by a lot of factors, such as lighting, weather, moving objects or even noise from the camera sensor itself, it's safe to assume that the stream is extremely chaotic. For instance, Cloudflare [uses lava lamps](https://blog.cloudflare.com/lavarand-in-production-the-nitty-gritty-technical-details/) to produce unpredictable cryptographically-secure encryption keys.

## How does it work ?
The camera of the phone captures a video. For each frame:
*   The raw image is acquired as a byte array
*   This byte array is hashed using `SHA1`
*   We take the first byte of the digest and show it in a `TextView`
*   We add all the other bytes to a graph that show the good distribution of numbers

## Release History
*   Be patient. I'm lazy.
