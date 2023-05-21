from flask import Flask, render_template, request, redirect, url_for
from werkzeug.utils import secure_filename
import os
import base64
import requests
from waitress import serve
import json
from flask_cors import CORS
import datetime


STATIC_DIR = os.path.abspath('./static')


app = Flask(__name__, template_folder=r"./templates/", static_folder=STATIC_DIR, static_url_path='/static')

@app.route('/main', methods=['GET'])
def index():

    with open("./static/output_data.json", "r", encoding="UTF-8") as f:
        plants_data = json.load(f)["obj_list"]
        
    plants_images = os.listdir("./static/atlasPhoto/")
    
    print(plants_images)
    
    return render_template('Hackathon.html', plants_data=plants_data, plants_images=plants_images)


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=80, debug=True)
