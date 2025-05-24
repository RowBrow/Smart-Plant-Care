// Taken and adapted from https://github.com/lucafabbri/HiGrow-Arduino-Esp/blob/master/HiGrowEsp32/HiGrowEsp32.ino

#include <WiFi.h> // For WiFi
#include <Esp.h>
#include <PubSubClient.h> // For MQTT client
#include "ArduinoJson-v7.3.1.h"
#include "DHT.h" // For humidity/temperature sensor

//#define DHTTYPE DHT21   // DHT 21 (AM2301)
#define DHTTYPE DHT11   // DHT 22  (AM2302), AM2321
#define uS_TO_S_FACTOR 1000000LL

const int MEASUREMENT_INTERVAL = 1000;
unsigned long lastTransmission;

// Set up WiFi and MQTT clients
WiFiClient wifiClient;
PubSubClient mqttClient(wifiClient);

uint64_t chipid; // Can be used as deviceId

// Configure pins for readings
const int DHT_PIN = 22;
const int SOIL_PIN = 32;
const int POWER_PIN = 34;
const int LIGHT_PIN = 33;

// Configure pins for output LEDs
const int LIGHT_LED_PIN = 5; 
const int WATER_LED_PIN = 17; 

// Initialize DHT sensor.
DHT dht(DHT_PIN, DHTTYPE);

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

// Set up variables to hold
// information about whether
// to turn water and light on
// or not
bool waterOn; 
bool lightOn; 

// The last time waterOn
// or lightOn turned true
unsigned long waterLast;
unsigned long lightLast;

// The intervals water or light should
// stay turned on if an message 
// commands them to turn on
const int waterOnInterval= 3000;
const int lightOnInterval= 3000;

/**
 * Attempt to connect to the wifi
 * for a number of tries
 */
void setup_wifi() {
  Serial.print("Connecting to WiFi...");
  WiFi.begin(SSID, PASSWORD);

  // Try to connect multiple times
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
void setup_mqtt() {
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
  // Set up serial connection for
  // sending messages when debugging
  Serial.begin(115200);

  // Start sensors to be used in
  // measurements
  dht.begin();
  pinMode(LIGHT_PIN, INPUT);

  // Set up WiFi and MQTT connection
  setup_wifi();
  mqttClient.setServer(MQTT_SERVER, MQTT_PORT); // Set up mqtt client
  mqttClient.setCallback(callback); // Set the callback function for the client
  
  // Set up outputs (LEDs) and set
  // their starting value to LOW
  pinMode(WATER_LED_PIN, OUTPUT);
  pinMode(LIGHT_LED_PIN, OUTPUT);
  digitalWrite(WATER_LED_PIN, LOW);
  digitalWrite(LIGHT_LED_PIN, LOW);

  // Create deviceId and print it
  // to serial connection
  chipid = ESP.getEfuseMac();
  sprintf(deviceid, "%" PRIu64, chipid);
  Serial.print("DeviceId: ");
  Serial.println(deviceid);
}

/**
 * Callback function called whenever
 * a topic subscribed gets a new message
 */
void callback(char* topic, byte* payload, unsigned int length) {
  // Check if the topic is the correct topic
  if (strcmp(topic, MQTT_SUBSCRIBE_TOPIC) == 0) {
    JsonDocument doc; // For parsing the JSON response

    deserializeJson(doc, (char*) payload); // Parse JSON response
    
    // Print the message
    for (int i = 0; i < length; i++) {
      Serial.print((char)payload[i]); 
    }
    Serial.print("\n"); 

    // If the response is not directed to
    // this device, do not process further
    //if (doc["device"] != MQTT_CLIENT_ID) {
    //  return;
    //}

    waterOn = doc["waterOn"];
    lightOn = doc["lightOn"];

    const unsigned long NOW = millis();

    // Update lastLast and waterLast
    // based on whether they should be
    // turned on by this message

    if(waterOn) {
      waterLast = NOW;
    }

    if(lightOn) {
      lightLast = NOW;
    }
  }
}


void loop() {
  // Check if connection to WiFi still exists
  if (WiFi.status() != WL_CONNECTED) {
    setup_wifi(); // Attempt to reconnect if necessary
  }

  // Check if connection to MQTT broker still exists
  if (!mqttClient.connected()) {
    setup_mqtt(); // Attempt to reconnect if necessary
  }

  // every 5 seconds, we want to publish a measurement
  
  mqttClient.loop(); // Receive messages

  const unsigned long NOW = millis();

  if(NOW > lastTransmission + MEASUREMENT_INTERVAL) {
    char body[1024];

    // Toggle the blue LED on the board to signify a measurement is sent
    digitalWrite(16, HIGH); 
    
    // Read sensor data
    sensorsData(body);
    // Send new measurement
    mqttClient.publish(MQTT_PUBLISH_TOPIC, body);
    // Report the data sent on
    // serial connection for
    // debugging purposes
    Serial.println(body);

    // Turn the blue LED on the board off again
    digitalWrite(16, LOW);

    // Update the last transmission time
    lastTransmission = NOW;

    // Report lightOn and waterOn for
    // debugging purposes
    Serial.println("Water: " + String(waterOn));
    Serial.println("Light: " + String(lightOn));
  }

  // Depending on waterOn and lightOn
  // toggle the LEDs

  if(waterOn) {
    digitalWrite(WATER_LED_PIN, HIGH);
  } else {
    digitalWrite(WATER_LED_PIN, LOW);
  }

  if(lightOn) {
    digitalWrite(LIGHT_LED_PIN, HIGH);
  } else {
    digitalWrite(LIGHT_LED_PIN, LOW);
  }
}

/**
 * Make a measurement of sensors
 * and save a result of them in
 * the form of a JSON object
 */
void sensorsData(char* body) {
  // Read water and light levels
  // in the environment
  int waterLevel = analogRead(SOIL_PIN);
  int lightLevel = analogRead(LIGHT_PIN);
  
  // Normalize moisture reading such that
  // 0 means completely dry and 100 means
  // completely wet
  waterLevel = constrain(waterLevel,1400,4000);
  waterLevel = map(waterLevel,1400,4000,0,100);
  waterLevel *= -1;
  waterLevel += 100; 

  // Normalize light reading such that
  // 0 means totally dark, and 100 means
  // completely bright (shining a flashlight
  // directly into the sensor)
  lightLevel = constrain(lightLevel, 0, 250);
  lightLevel = map(lightLevel, 0, 250, 0, 100);
  lightLevel *= -1;
  lightLevel += 100;
  
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float humidity = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float temperature = dht.readTemperature();
  
  float hic = dht.computeHeatIndex(temperature, humidity, false);       
  dtostrf(hic, 6, 2, celsiusTemp);               
  dtostrf(humidity, 6, 2, humidityTemp);
  
  String did = String(deviceid);
  String water = String((int)waterLevel);
  String light = String((int)lightLevel);

  // Prepare the message to publish
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
