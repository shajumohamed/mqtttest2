var mqtt = require('mqtt');
var count=0;
var client = mqtt.createClient(1883, 'test.mosca.io');


	
client.subscribe("Allianz/#");
client.on('message', function (topic, message) {
  console.log(message);
});