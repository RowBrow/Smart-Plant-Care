#include <WiFi.h> // For WiFi
#include <Esp.h>
#include <PubSubClient.h> // For MQTT client
#include "ArduinoJson-v7.3.1.h"
#include "DHT.h" // For humidity/temperature sensor

//#define DHTTYPE DHT11   // DHT 11
//#define DHTTYPE DHT21   // DHT 21 (AM2301)
#define DHTTYPE DHT11   // DHT 22  (AM2302), AM2321
#define uS_TO_S_FACTOR 1000000LL

unsigned long now;

// Set up WiFi and MQTT clients
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

uint64_t chipid; // Can be used as deviceId

const int dhtpin = 22;
const int soilpin = 32;
const int POWER_PIN = 34;
const int LIGHT_PIN = 33;

// Initialize DHT sensor.
DHT dht(dhtpin, DHTTYPE);

// Temporary variables
static char celsiusTemp[7];
static char humidityTemp[7];

// Set up the credentials for the WiFi
const char* SSID = "RUC-IOT";
const char* PASSWORD = "GiHa2638La";
const int MAX_CONNECTION_TRIES = 20;

// Configure for selected MQTT broker
const char* MQTT_SERVER = "public.cloud.shiftr.io";
const int MQTT_PORT = 1883;

// Set up the credentials for the MQTT broker
const char* MQTT_USER = "public";
const char* MQTT_PASSWORD = "public";
const char* MQTT_CLIENT_ID = "HiGrowSensor";

// Set up the topics to subscribe and publish to
const char* MQTT_SUBSCRIBE_TOPIC = "HiGrowSensor/send_action";
const char* MQTT_PUBLISH_TOPIC   = "HiGrowSensor/send_data";

char deviceid[21];

/**
 * Attempt to connect to the wifi
 * for a number of tries
 */
void setup_wifi() {
  Serial.print("Connecting to WiFi...");
  WiFi.begin(SSID, PASSWORD);

  int attempts = 0;
  while (WiFi.status() != WL_CONNECTED && attempts < MAX_CONNECTION_TRIES) {
    delay(500);
    Serial.print(".");
    attempts++;
  }

  // Give information whether the connection was successful
  if (WiFi.status() == WL_CONNECTED) {
    Serial.print("\nConnected to WiFi with IP ");
    Serial.println(WiFi.localIP());
  } else {
    Serial.println("\nWiFi connection failed!");
  }
}

/**
 * Attempt to reconnect to the MQTT server
 */
void reconnect() {
  while (!mqttClient.connected()) {
    Serial.print("Attempting MQTT connection...");
    if (mqttClient.connect(MQTT_CLIENT_ID, MQTT_USER, MQTT_PASSWORD)) {
      Serial.println("connected");
      mqttClient.subscribe(MQTT_SUBSCRIBE_TOPIC);
    } else {
      Serial.print("failed, rc=");
      Serial.print(mqttClient.state());
      Serial.println(" retrying in 5 seconds...");
      delay(5000);
    }
  }
}


void setup() {
  Serial.begin(115200);

  dht.begin();

  setup_wifi();
  mqttClient.setServer(MQTT_SERVER, MQTT_PORT); // Set up mqtt client
  
  // TODO: Define the callback function
  //mqttClient.setCallback(callback); // Set the callback function for the client
  
  pinMode(16, OUTPUT); 
  pinMode(POWER_PIN, INPUT);
  digitalWrite(16, LOW);  

  chipid = ESP.getEfuseMac();
  sprintf(deviceid, "%" PRIu64, chipid);
  Serial.print("DeviceId: ");
  Serial.println(deviceid);
}

void loop() {
  // Check if connection to WiFi still exists
  if (WiFi.status() != WL_CONNECTED) {
    setup_wifi(); // Attempt to reconnect if necessary
  }

  // Check if connection to MQTT broker still exists
  if (!mqttClient.connected()) {
    reconnect(); // Attempt to reconnect if necessary
  }

  mqttClient.loop(); // Receive messages


  char body[1024];
  digitalWrite(16, LOW); //switched on
  sensorsData(body);

  mqttClient.publish(MQTT_PUBLISH_TOPIC, body);
  Serial.println(body);
  delay(5000);
}

void sensorsData(char* body){

  int waterlevel = analogRead(soilpin);
  int lightlevel = analogRead(LIGHT_PIN);
  
  waterlevel = map(waterlevel, 0, 4095, 0, 1023);
  waterlevel = constrain(waterlevel, 0, 1023);
  lightlevel = map(lightlevel, 0, 4095, 0, 1023);
  lightlevel = constrain(lightlevel, 0, 1023);
  
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float humidity = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float temperature = dht.readTemperature();
  
  float hic = dht.computeHeatIndex(temperature, humidity, false);       
  dtostrf(hic, 6, 2, celsiusTemp);               
  dtostrf(humidity, 6, 2, humidityTemp);
  
  String did = String(deviceid);
  String water = String((int)waterlevel);
  String light = String((int)lightlevel);

  strcpy(body, "{\"deviceId\":\"");
  strcat(body, did.c_str());
  strcat(body, "\",\"water\":\"");
  strcat(body, water.c_str());
  strcat(body, "\",\"light\":\"");
  strcat(body, light.c_str());
  strcat(body, "\",\"humidity\":\"");
  strcat(body, humidityTemp);
  strcat(body, "\",\"temperature\":\"");
  strcat(body, celsiusTemp);
  strcat(body, "\"}");
}
