# timelapse-webcam

__status: WIP__

Java application for using the webcam to capture multiple images with a fixed repetition rate.


## TODO

### Features
- [X] set the directory where the images are saved
- [X] set the time between two images
- [X] set the time how long the capturing should run
- [ ] write the timestamp into the metadata
- [ ] provide a live view before capturing
- [ ] display the latest captured image
- [ ] customize the filename
- [ ] choose the file type (JPEG/PNG/...)
- [ ] take a test image and calculate the approximate file size and amount of images
- [ ] support timelapse with multiple cameras parallel

### Development
- [ ] build for mutiple OS
- [ ] move UI in a separate Thread
- [ ] improve the build script and stuff from ANT


## Credits

Application makes use of the following libraries:

- [webcam-capture](https://github.com/sarxos/webcam-capture) by [sarxos](https://github.com/sarxos)
