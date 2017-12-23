#!/usr/bin/env python3


# Server per testare l'interazione client server

from colorama import Fore, Back, Style, init
from flask import Flask, render_template, send_from_directory
from flask import request
import json
from pprint import pprint
app = Flask(__name__)



tempToken  = "qwertyasdfgh12345"
tempUsername = "admin"
tempPassword = "pwd"

init()
print(Fore.WHITE + Back.BLUE + '  _____                _                _                 ')
print(Fore.WHITE + Back.BLUE + ' |_   _| __ __ ___   _| | ___ _ __   __| | __ _ _ __  _   ')
print(Fore.WHITE + Back.BLUE + "   | || '__/ _` \ \ / / |/ _ \ '_ \ / _` |/ _` | '__|| |_ ")
print(Fore.WHITE + Back.BLUE + '   | || | | (_| |\ V /| |  __/ | | | (_| | (_| | | |_   _|')
print(Fore.WHITE + Back.BLUE + '   |_||_|  \__,_| \_/ |_|\___|_| |_|\__,_|\__,_|_|   |_|  ')
print(Fore.WHITE + Back.BLUE + "                                                          ")
#print(Style.RESET_ALL)
print(Fore.BLACK + Back.GREEN + "Starting server now..")
print(Style.RESET_ALL)


# Test del metodo del login
@app.route('/Login', methods=['POST'])
def login():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded['username'] == tempUsername and data_loaded['password'] == tempPassword:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /Login")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "ok",
				"token" : tempToken
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "ko",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)


# Test del metodo del signUp
@app.route('/SignUp', methods=['POST'])
def sign_up():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if 1:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /SignUp")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "ok",
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "ko",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

#Added route to see images files from flask
@app.route("/img/<path:path>")
def send_image(path):
        return send_from_directory("templates/img", path)

@app.route("/js/<path:path>")
def send_distjs(path):
        return send_from_directory("templates/js", path)

#Added route to see css files from flask
@app.route("/css/<path:path>")
def send_css(path):
        return send_from_directory("templates/css", path)

#Add route for other stuff inside the server
@app.route("/lib/<path:path>")
def send_lib(path):
	return send_from_directory("templates/lib", path)

@app.route("/contactform/<path:path>")
def send_contact(path):
	return send_from_directory("templates/contactform", path)

# Added fonts
@app.route("/font/<path:path>")
def send_font(path):
	return send_from_directory("templates/font", path)

@app.route("/<path:path>")
def all_other(path):
	return send_from_directory("templates", path)

@app.route("/")
def hello():
        return render_template("index.html")



if __name__ == "__main__":
    app.run()

