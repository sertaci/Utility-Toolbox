import tensorflow as tf
import numpy as np
import os 
import cv2
import matplotlib.pyplot as plt
import librosa
import librosa.display


# data = tf.keras.utils.image_dataset_from_directory(os.path.dirname(os.path.abspath(__file__))+'/urban/data', color_mode='grayscale', image_size=(256, 256))

model = tf.keras.models.load_model(os.path.dirname(os.path.abspath(__file__))+'/fullmodellast.h5')


def create_spectogram(y):
    spec = librosa.feature.melspectrogram(y=y)
    spec_conv = librosa.amplitude_to_db(spec, ref=np.max)

    return spec_conv

path = os.path.dirname(os.path.abspath(__file__)) + "/test.wav"
data, sampling_rate = librosa.load(path)

out = create_spectogram(data)

plt.figure(figsize=(5,4))
plt.gca().set_axis_off()
plt.subplots_adjust(top = 1, bottom = 0, right = 1, left = 0, hspace = 0, wspace = 0)
plt.margins(0,0)

librosa.display.specshow(out, sr=sampling_rate)
plt.savefig("out1.png", bbox_inches="tight", pad_inches = 0)
newimg = cv2.imread(r'out1.png')
resize = tf.image.resize(newimg, (256,256))
grayimg = tf.image.rgb_to_grayscale(resize)

yhat = model.predict(np.expand_dims(grayimg/255, 0))

f = open("out.txt", "w")
if yhat[0][0] > 0.8:
    f.write("Air Conditioner Sound")

elif yhat[0][1] > 0.8:
    f.write("Car Horn Sound")

elif yhat[0][2] > 0.8:
    f.write("Children Playing Sound")

elif yhat[0][3] > 0.8:
    f.write("Dog Bark Sound")

elif yhat[0][4] > 0.8:
    f.write("Drilling Sound")

elif yhat[0][5] > 0.8:
    f.write("Engine Idling Sound")

elif yhat[0][6] > 0.8:
    f.write("Gun Shot Sound")

elif yhat[0][7] > 0.8:
    f.write("Jackhammer Sound")

elif yhat[0][8] > 0.8:
    f.write("Siren Sound")

elif yhat[0][9] > 0.8:
    f.write("Street Music Sound")

else:
    f.write("Could Not Find the Sound")


f.close()
