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
				"status" : "OK",
				"token" : tempToken
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			pprint(data_loaded)
			my_response = {
				"status" : "KO",
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
				"status" : "OK",
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)


# Test del metodo del UserInformation
@app.route('/UserInformation', methods=['POST'])
def get_user_information():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /UserInformation")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status": "OK",
				"user" : {
					"username": "admin",
					"name": "luke",
					"surname" : "mos",
					"email" : "lukaszmoskwa94@gmail.com",
					"phone" : "92381983",
					"drivingLicense" : "ASD123QWE567",
					"creditCard" : "ZXC123ASD123",
					"maxWalk" : "1km",
					"maxHourMeans" : "2",
					"breakPref" : [
					],
					"meansPref" : [
					]
				}
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)


# Test del metodo del GetWeather
# Has a fixed position and date only for testing purposes
@app.route('/GetWeather', methods=['POST'])
def get_weather():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["day"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /GetWeather")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {"location":{"name":"Desio","region":"Lombardia","country":"Italy","lat":45.62,"lon":9.22,"tz_id":"Europe/Rome","localtime_epoch":1514127113,"localtime":"2017-12-24 15:51"},"current":{"last_updated_epoch":1514126710,"last_updated":"2017-12-24 15:45","temp_c":13.0,"temp_f":55.4,"is_day":1,"condition":{"text":"Soleggiato","icon":"//cdn.apixu.com/weather/64x64/day/113.png","code":1000},"wind_mph":4.3,"wind_kph":6.8,"wind_degree":280,"wind_dir":"W","pressure_mb":1026.0,"pressure_in":30.8,"precip_mm":0.0,"precip_in":0.0,"humidity":62,"cloud":0,"feelslike_c":12.8,"feelslike_f":55.0,"vis_km":10.0,"vis_miles":6.0},"forecast":{"forecastday":[]}}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)


# Test del metodo del SetUserField
# Has a fixed position and date only for testing purposes
@app.route('/SetUserField', methods=['POST'])
def set_user_field():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["field"] is not None and data_loaded["newValue"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /SetUserField")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

# Test del metodo del SetBreakPref
@app.route('/SetBreakPref', methods=['POST'])
def set_break_pref():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["flag"] is not None and data_loaded["name"] is not None and data_loaded["start"] is not None and data_loaded["end"] is not None and data_loaded["duration"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /SetBreakPref")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)


# Test del metodo del SetMeansPref
@app.route('/SetMeansPref', methods=['POST'])
def set_means_pref():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["flag"] is not None and data_loaded["means"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /SetMeansPref")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

# Test del metodo del logout
@app.route('/Logout', methods=['POST'])
def logout():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded['username'] is not None and data_loaded['token'] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /Logout")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

# Test del metodo del DeleteEvent
@app.route('/DeleteEvent', methods=['POST'])
def delete_event():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["ID"] is not None and data_loaded["username"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /DeleteEvent")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

# Test del metodo del GetAllSchedule
@app.route('/GetAllSchedule', methods=['POST'])
def get_all_schedule():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["username"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /GetAllSchedule")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

# Test del metodo del AddEvent
@app.route('/AddEvent', methods=['POST'])
def add_event():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["username"] is not None and data_loaded["day"] is not None and data_loaded["eventDuration"] is not None and data_loaded["eventName"] is not None and data_loaded["eventStart"] is not None and data_loaded["origin"] is not None and data_loaded["eventPosition"] is not None and data_loaded["eventType"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /AddEvent")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)


# Test del metodo del CreateSchedule
@app.route('/CreateSchedule', methods=['POST'])
def create_schedule():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["token"] is not None and data_loaded["username"] is not None and data_loaded["day"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /CreateSchedule")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
				"token" : "Qualcosa è andato male..."
			}
			pprint(my_response)
			return json.dumps(my_response)

# Test del metodo del GetPath
@app.route('/GetPath', methods=['POST'])
def get_path():
	if request.method == 'POST':
		data_loaded = json.loads((request.data).decode("utf-8"))
		if data_loaded["origin"] is not None and data_loaded["destination"] is not None and data_loaded["mode"] is not None:
			print(Fore.WHITE + Back.YELLOW + "### REQUEST made on /GetPath")
			pprint(data_loaded)
			print(Back.YELLOW + "==========")
			print(Fore.WHITE + Back.YELLOW + "### RESPONSE is:")
			my_response = {
				"status" : "OK"
			}
			pprint(my_response)
			print(Style.RESET_ALL)
			return json.dumps(my_response)
		else:
			print(Back.RED+ Fore.WHITE + "Error!!")
			my_response = {
				"status" : "KO",
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